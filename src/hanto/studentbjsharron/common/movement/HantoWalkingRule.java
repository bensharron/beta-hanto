/**
 * 
 */
package hanto.studentbjsharron.common.movement;

import java.util.List;

import hanto.common.HantoException;
import hanto.studentbjsharron.common.HantoBoard;
import hanto.studentbjsharron.common.HantoCoordinateImpl;

/**
 * 
 * @author Ben Sharron
 *
 */
public class HantoWalkingRule implements HantoMovementRule {

	private int maxSteps;
	
	/**
	 * The default is a max of one step
	 */
	public HantoWalkingRule() {
		this(1);
	}
	
	/**
	 * Save the maximum number of walk steps
	 * @param maxSteps The maximum number of steps that the piece can walk
	 */
	public HantoWalkingRule(int maxSteps) {
		this.maxSteps = maxSteps;
	}
	
	/*
	 * @see hanto.studentbjsharron.common.HantoMovementRule#checkValidMove()
	 */
	@Override
	public void checkValidMove(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board) throws HantoException {
		if (!checkValidPath(from, to, board, maxSteps)) {
			throw new HantoException("No valid walk to that location.");
		}
	}
	
	/**
	 * Recursive helper for finding valid paths
	 * @param from Start location
	 * @param to End location
	 * @param board Board that the move is happening on
	 * @param stepsLeft number of unused steps in the path
	 * @return whether there is a valid path
	 */
	private boolean checkValidPath(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board, int stepsLeft) {
		if (from.equals(to) && stepsLeft != maxSteps) {
			return true;
		}
		
		if (stepsLeft == 0) { 
			return false;
		}
		
		HantoBoard newBoard;
		boolean foundPath = false;
		
		for (HantoCoordinateImpl adj : from.getAdjacentCoordinates()) {
			if ((newBoard = checkValidStep(from, adj, board)) != null) {				
				if (checkValidPath(adj, to, newBoard, stepsLeft - 1)) {
					foundPath = true;
					break;
				}
			}
		}
		
		return foundPath;
	}
	
	/**
	 * Check a single step in a walk for validity
	 * @param from Start location for step
	 * @param to End location for step
	 * @param board Board that the move is happening on
	 * @return the board after the step is made, null if the step is invalid
	 */
	private HantoBoard checkValidStep(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board) {
		HantoBoard boardCopy = board.copyBoard();
		
		boardCopy.addPiece(boardCopy.removePiece(from), to);
		
		if (boardCopy.checkContiguousBoard() && checkValidSlideMove(from, to, board)) {
			return boardCopy;
		} else {
			return null;
		}
	}

	/**
	 * Check if a single walk step is allowed to be made due to sliding constraints
	 * @param from The start of the sliding step
	 * @param to The end of the sliding step
	 * @param board The board the pieces are on
	 * @return whether the piece can slide this step
	 */
	private boolean checkValidSlideMove(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board) {
		List<HantoCoordinateImpl> toAdjacencies = to.getAdjacentCoordinates();
		List<HantoCoordinateImpl> fromAdjacencies = from.getAdjacentCoordinates();
		boolean spaceAvailable = false;
		
		for (HantoCoordinateImpl adj : toAdjacencies) {
			// Find spaces adjacent to from and to; one of them must be empty
			if (fromAdjacencies.contains(adj) && !board.hasPieceAt(adj)) {		
				spaceAvailable = true;
				break;
			}
		}
		
		if (!spaceAvailable) {
			return false;
		}
		
		return true;
	}
}

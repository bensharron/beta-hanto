/**
 * 
 */
package hanto.studentbjsharron.common.movement;

import java.util.List;

import hanto.common.*;
import hanto.studentbjsharron.common.HantoBoard;
import hanto.studentbjsharron.common.HantoCoordinateImpl;
import hanto.studentbjsharron.common.HantoMove;

/**
 * A Strategy Pattern for a movement rule
 * @author Ben Sharron
 *
 */
public interface HantoMovementRule {
	/**
	 * Check if a piece of the current movement type can make the given move on the given board
	 * @param from the start location for the piece
	 * @param to the end location for the piece
	 * @param board the board that the piece is moving on
	 * @throws HantoException if the move is invalid
	 */
	void checkValidMove(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board) throws HantoException;
	
	/**
	 * Get all valid moves a piece of the current movement type can make on the given board by the given player
	 * @param from the start location for the piece
	 * @param color the color of the player moving the piece
	 * @param board the board that the piece is moving on
	 * @throws HantoException if the move is invalid
	 */
	List<HantoMove> getValidMoves(HantoCoordinateImpl from, HantoPlayerColor color, HantoBoard board);
}

/**
 * 
 */
package hanto.studentbjsharron.common.movement;

import java.util.LinkedList;
import java.util.List;

import hanto.common.HantoException;
import hanto.studentbjsharron.common.HantoBoard;
import hanto.studentbjsharron.common.HantoCoordinateImpl;

/**
 * @author Ben Sharron
 *
 */
public class HantoJumpingRule extends HantoMovementStrategy {

	/*
	 * @see hanto.studentbjsharron.common.movement.HantoMovementRule#checkValidMove(hanto.studentbjsharron.common.HantoCoordinateImpl, hanto.studentbjsharron.common.HantoCoordinateImpl, hanto.studentbjsharron.common.HantoBoard)
	 */
	@Override
	public void checkValidMove(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board) throws HantoException {
		if (!isValidMove(from, to, board)) {
			throw new HantoException("Piece cannot jump to that location.");
		}
	}
	
	public boolean isValidMove(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board) {
		List<HantoCoordinateImpl> pathLocs = new LinkedList<HantoCoordinateImpl>();
		
		if (to.isAdjacentTo(from)) {
			return false;
		}
		
		if ((pathLocs = getStraightLinePath(from, to)) == null) {
			return false;
		}
		
		for (HantoCoordinateImpl hex : pathLocs) {
			if (!board.hasPieceAt(hex)) {
				return false;
			}
		}
		
		return true;
	}

	private List<HantoCoordinateImpl> getStraightLinePath(HantoCoordinateImpl from, HantoCoordinateImpl to) {
		int x1 = from.getX();
		int y1 = from.getY();
		int x2 = to.getX();
		int y2 = to.getY();
		
		int startX = Math.min(x1, x2);
		int endX = Math.max(x1, x2);
		int startY = Math.min(y1, y2);
		int endY = Math.max(y1, y2);
		
		List<HantoCoordinateImpl> inBetweenLocs = new LinkedList<HantoCoordinateImpl>();
		
		if (x1 == x2 && y1 != y2) {
			for (int y = startY + 1; y < endY; y++) {
				inBetweenLocs.add(new HantoCoordinateImpl(x1, y));
			}
		} else if (y1 == y2 && x1 != x2) {
			for (int x = startX + 1; x < endX; x++) {
				inBetweenLocs.add(new HantoCoordinateImpl(x, y1));
			}
		} else if (x1 + y1 == x2 + y2) {
			for (int i = 1; i < endX - startX; i++) {
				inBetweenLocs.add(new HantoCoordinateImpl(startX + i, endY - i));
			}
		} else {
			return null;
		}
		
		return inBetweenLocs;
	}
}

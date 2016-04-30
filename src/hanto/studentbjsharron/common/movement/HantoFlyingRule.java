/**
 * 
 */
package hanto.studentbjsharron.common.movement;

import hanto.common.HantoException;
import hanto.studentbjsharron.common.HantoBoard;
import hanto.studentbjsharron.common.HantoCoordinateImpl;

/**
 * @author Ben Sharron
 *
 */
public class HantoFlyingRule extends HantoMovementStrategy {

	private final int maxDistance;

	public HantoFlyingRule() {
		maxDistance = Integer.MAX_VALUE;
	}
	
	public HantoFlyingRule(int maxDist) {
		maxDistance = maxDist;
	}

	/*
	 * @see hanto.studentbjsharron.common.movement.HantoMovementRule#checkValidMove(hanto.studentbjsharron.common.HantoCoordinateImpl, hanto.studentbjsharron.common.HantoCoordinateImpl, hanto.studentbjsharron.common.HantoBoard)
	 */
	@Override
	public void checkValidMove(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board) throws HantoException {
		if (!isValidMove(from, to, board)) {
			throw new HantoException("Piece can only fly " + maxDistance + " spaces.");
		}
	}
	
	public boolean isValidMove(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board) {
		return from.distanceTo(to) <= maxDistance;
	}
}

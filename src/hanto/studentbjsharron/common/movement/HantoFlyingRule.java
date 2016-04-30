/**
 * 
 */
package hanto.studentbjsharron.common.movement;

import java.util.*;

import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.studentbjsharron.common.HantoBoard;
import hanto.studentbjsharron.common.HantoCoordinateImpl;
import hanto.studentbjsharron.common.HantoMove;

/**
 * @author Ben Sharron
 *
 */
public class HantoFlyingRule implements HantoMovementRule {

	/*
	 * @see hanto.studentbjsharron.common.movement.HantoMovementRule#checkValidMove(hanto.studentbjsharron.common.HantoCoordinateImpl, hanto.studentbjsharron.common.HantoCoordinateImpl, hanto.studentbjsharron.common.HantoBoard)
	 */
	@Override
	public void checkValidMove(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board) throws HantoException {}

	@Override
	public List<HantoMove> getValidMoves(HantoCoordinateImpl from, HantoPlayerColor color, HantoBoard board) {
		List<HantoMove> validMoves = new LinkedList<HantoMove>();
		
		return validMoves;
	}
}

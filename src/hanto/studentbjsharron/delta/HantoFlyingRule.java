/**
 * 
 */
package hanto.studentbjsharron.delta;

import hanto.common.HantoException;
import hanto.studentbjsharron.common.HantoBoard;
import hanto.studentbjsharron.common.HantoCoordinateImpl;
import hanto.studentbjsharron.common.movement.HantoMovementRule;

/**
 * @author ben
 *
 */
public class HantoFlyingRule implements HantoMovementRule {

	/* (non-Javadoc)
	 * @see hanto.studentbjsharron.common.movement.HantoMovementRule#checkValidMove(hanto.studentbjsharron.common.HantoCoordinateImpl, hanto.studentbjsharron.common.HantoCoordinateImpl, hanto.studentbjsharron.common.HantoBoard)
	 */
	@Override
	public void checkValidMove(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board)
			throws HantoException {}

}

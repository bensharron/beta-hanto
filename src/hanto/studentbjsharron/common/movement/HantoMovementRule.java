/**
 * 
 */
package hanto.studentbjsharron.common.movement;

import hanto.common.*;
import hanto.studentbjsharron.common.HantoBoard;
import hanto.studentbjsharron.common.HantoCoordinateImpl;

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
}

/**
 * 
 */
package hanto.studentbjsharron.delta;

import static hanto.common.HantoPieceType.*;

import hanto.common.HantoPlayerColor;
import hanto.studentbjsharron.common.BaseHantoGame;
import hanto.studentbjsharron.common.movement.HantoFlyingRule;
import hanto.studentbjsharron.common.movement.HantoWalkingRule;

/**
 * The implementation of Delta Hanto.
 * @author Ben Sharron
 *
 */
public class DeltaHantoGame extends BaseHantoGame {

	/**
	 * Standard constructor for DeltaHantoGame with given starting player
	 * @param movesFirst player that will make the first move
	 */
	public DeltaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);

		addPiece(SPARROW, 4);
		addPiece(CRAB, 4);
		
		rules.addRule(BUTTERFLY, new HantoWalkingRule());
		rules.addRule(SPARROW, new HantoFlyingRule());
		rules.addRule(CRAB, new HantoWalkingRule(3));
		
		canMove = true;
		canResign = true;
		numTurns = Integer.MAX_VALUE;
	}
}

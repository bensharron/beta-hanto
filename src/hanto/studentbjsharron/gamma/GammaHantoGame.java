/**
 * 
 */
package hanto.studentbjsharron.gamma;

import static hanto.common.HantoPieceType.*;

import hanto.common.*;
import hanto.studentbjsharron.common.*;
import hanto.studentbjsharron.common.movement.HantoWalkingRule;

/**
 * The implementation of Gamma Hanto.
 * @author Ben Sharron
 *
 */
public class GammaHantoGame extends	BaseHantoGame {
	
	/**
	 * Standard constructor for GammaHantoGame with given starting player
	 * @param movesFirst player that will make the first move
	 */
	public GammaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);

		addPiece(SPARROW, 5);
		
		rules.addRule(BUTTERFLY, new HantoWalkingRule());
		rules.addRule(SPARROW, new HantoWalkingRule());

		numTurns = 20;
	}
}

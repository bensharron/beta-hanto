/**
 * 
 */
package hanto.studentbjsharron.epsilon;

import static hanto.common.HantoPieceType.*;

import hanto.common.*;
import hanto.studentbjsharron.common.BaseHantoGame;
import hanto.studentbjsharron.common.movement.*;

/**
 * @author Ben Sharron
 *
 */
public class EpsilonHantoGame extends BaseHantoGame {
	/**
	 * Standard constructor for DeltaHantoGame with given starting player
	 * @param movesFirst player that will make the first move
	 */
	public EpsilonHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		
		addPiece(SPARROW, 2);
		addPiece(CRAB, 6);
		addPiece(HORSE, 4);
		
		rules.addRule(BUTTERFLY, new HantoWalkingRule());
		rules.addRule(SPARROW, new HantoFlyingRule(4));
		rules.addRule(CRAB, new HantoWalkingRule());
		rules.addRule(HORSE, new HantoJumpingRule());
		
		canMove = true;
		canResign = true;
		numTurns = Integer.MAX_VALUE;
	}
	
	@Override
	public boolean checkResign() throws HantoPrematureResignationException {
		boolean isResign = super.checkResign();
		
		if (isResign && !(getPlayerPlays(currentPlayer).isEmpty())) {
			throw new HantoPrematureResignationException();
		}
		
		return isResign;
	}
}

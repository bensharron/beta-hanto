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

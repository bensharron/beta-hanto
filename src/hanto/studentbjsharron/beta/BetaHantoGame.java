/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package hanto.studentbjsharron.beta;

import static hanto.common.HantoPieceType.*;

import hanto.common.*;
import hanto.studentbjsharron.common.*;

/**
 * The implementation of Beta Hanto.
 * @version Mar 16, 2016
 */
public class BetaHantoGame extends BaseHantoGame
{

	/**
	 * Standard constructor for BetaHantoGame with given starting player
	 * @param movesFirst player that will make the first move
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);

		addPiece(SPARROW, 5);
		
		canMove = false;
		numTurns = 6;
	}
	
	/**
	 * Check if the piece at given location is the opponent's
	 * @param loc Location to check
	 * @return whether the piece belongs to the opponent
	 */
	@Override
	public boolean IsOpponent(HantoCoordinateImpl loc) {
		return false;
	}
}

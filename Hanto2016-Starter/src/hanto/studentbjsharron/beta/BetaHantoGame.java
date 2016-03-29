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

import hanto.common.*;
import hanto.studentbjsharron.common.*;

import static hanto.common.MoveResult.*;
import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;

/**
 * The implementation of Beta Hanto.
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame
{
	private HantoPlayerColor startingPlayer, currentPlayer;
	private HantoPiece bluePiece, redPiece;
	private HantoCoordinateImpl blueLocation, redLocation;
	
	/**
	 * Standard constructor for BetaHantoGame with given starting player
	 * @param movesFirst player that will make the first move
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst) {
		startingPlayer = movesFirst;
		currentPlayer = movesFirst;
	}
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		// copy constructor
		HantoCoordinateImpl place = new HantoCoordinateImpl(to);
		
		if (currentPlayer == startingPlayer) {
			if (to.getX() != 0 || to.getY() != 0)
				throw new HantoException("First player did not make first move to origin.");
		} else {
			if (currentPlayer == BLUE && !place.isAdjacentTo(redLocation))
				throw new HantoException("New piece not adjacent to existing piece.");
			
			if (currentPlayer == RED && !place.isAdjacentTo(blueLocation)) {
				throw new HantoException("New piece not adjacent to existing piece.");
			}
		}
		
		// Swap player
		if (currentPlayer == BLUE) {
			bluePiece = new HantoPieceImpl(BLUE, pieceType);
			blueLocation = place;
			currentPlayer = RED;
		} else {
			redPiece = new HantoPieceImpl(RED, pieceType);
			redLocation = place;
			currentPlayer = BLUE;
		}
		
		return OK;
	}

	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		HantoCoordinateImpl loc = new HantoCoordinateImpl(where);
		
		if (loc.equals(blueLocation)) {
			return bluePiece;
		} else if (loc.equals(redLocation)) {
			return redPiece;
		} else {
			return null;
		}
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		// TODO Auto-generated method stub
		return null;
	}

}

/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentbjsharron.tournament;

import static hanto.common.HantoPlayerColor.*;

import java.util.List;
import java.util.Random;

import hanto.common.*;
import hanto.studentbjsharron.HantoGameFactory;
import hanto.studentbjsharron.common.BaseHantoGame;
import hanto.studentbjsharron.common.HantoCoordinateImpl;
import hanto.studentbjsharron.common.HantoMove;
import hanto.tournament.*;

/**
 * Description
 * @version Oct 13, 2014
 */
public class HantoPlayer implements HantoGamePlayer
{
	HantoPlayerColor myColor, oppColor, movesFirst;
	BaseHantoGame game;
	
	/*
	 * @see hanto.tournament.HantoGamePlayer#startGame(hanto.common.HantoGameID, hanto.common.HantoPlayerColor, boolean)
	 */
	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst)
	{
		HantoGameFactory factory = HantoGameFactory.getInstance();
		
		this.myColor = myColor;
		oppColor = myColor == BLUE ? RED : BLUE;
		movesFirst = doIMoveFirst ? myColor : oppColor;
		
		game = (BaseHantoGame) factory.makeHantoGame(version, movesFirst);
	}

	/*
	 * @see hanto.tournament.HantoGamePlayer#makeMove(hanto.tournament.HantoMoveRecord)
	 */
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove)
	{
		if (opponentsMove != null) {
			try {
				game.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());
			} catch (HantoException e) {
				e.printStackTrace();
			}
		}
		
		List<HantoMove> possMoves = game.getPlayerPlays(myColor);
		
		HantoMove move = getMove(possMoves);
		
		HantoPieceType piece = move.getPiece();
		HantoCoordinateImpl from = move.getFrom();
		HantoCoordinateImpl to = move.getTo();
		
		try {
			game.makeMove(piece, from, to);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		
		return new HantoMoveRecord(piece, from, to);
	}

	private HantoMove getMove(List<HantoMove> possMoves) {
		Random rn = new Random();
		int index = rn.nextInt(possMoves.size());
		
		return possMoves.get(index);
	}
}

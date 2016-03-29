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

import java.util.*;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;

/**
 * The implementation of Beta Hanto.
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame
{
	private HantoPlayerColor startingPlayer, currentPlayer;
	private int turnNumber = 1;
	private boolean blueButterflyPlaced = false, redButterflyPlaced = false, gameOver = false;
	private Map<HantoCoordinateImpl, HantoPiece> board = new HashMap<HantoCoordinateImpl, HantoPiece>();
	
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
		if (gameOver) {
			throw new HantoException("You cannot move after the game is finished.");
		}
		
		if (from != null) {
			throw new HantoException("Piece must be placed and not moved.");
		}
		
		checkValidPiece(pieceType);
		
		// copy constructor
		HantoCoordinateImpl place = new HantoCoordinateImpl(to);
		
		checkValidLocation(place);
		
		HantoPieceImpl newPiece;
		
		if (currentPlayer == BLUE) {
			if (pieceType == BUTTERFLY) {
				blueButterflyPlaced = true;
			}
			
			newPiece = new HantoPieceImpl(BLUE, pieceType);
			currentPlayer = RED;
		} else {
			if (pieceType == BUTTERFLY) {
				redButterflyPlaced = true;
			}
			
			newPiece = new HantoPieceImpl(RED, pieceType);
			currentPlayer = BLUE;
		}
		
		if (currentPlayer == startingPlayer) {
			// Next turn
			turnNumber++;
		}
		
		board.put(place, newPiece);
		
		if (turnNumber > 6) {
			gameOver = true;
			return DRAW;
		} else {
			return OK;
		}
	}

	/**
	 * @param pieceType piece to check if it's valid on the current board for BetaHanto
	 * @throws HantoException if piece is invalid
	 */
	private void checkValidPiece(HantoPieceType pieceType) throws HantoException {
		if (pieceType != BUTTERFLY && pieceType != SPARROW) {
			throw new HantoException("Piece must be a butterfly or a sparrow.");
		}
		
		if (currentPlayer == BLUE) {
			if (pieceType == BUTTERFLY && blueButterflyPlaced) {
				// Must be first one
				throw new HantoException("Blue player has already played his butterfly.");
			}
			
			if (turnNumber >= 4 && pieceType != BUTTERFLY && !blueButterflyPlaced) {
				// Butterfly must be placed by turn 4
				throw new HantoException("Blue player must play his butterfly.");
			}
		}
			
		if (currentPlayer == RED) {
			if (pieceType == BUTTERFLY && redButterflyPlaced) {
				// Must be first one
				throw new HantoException("Red player has already played his butterfly.");
			}
			
			if (turnNumber >= 4 && pieceType != BUTTERFLY && !redButterflyPlaced) {
				// Butterfly must be placed by turn 4
				throw new HantoException("Red player must play his butterfly.");
			}
		}
	}

	/**
	 * Helper function to check if new coordinate is valid under rules of BetaHanto
	 * @param place HantoCoordinate to check to guarantee the coordinate is valid
	 */
	private void checkValidLocation(HantoCoordinateImpl place) throws HantoException {
		if (currentPlayer == startingPlayer && turnNumber == 1) {
			if (place.getX() != 0 || place.getY() != 0) {
				throw new HantoException("First player did not make first move to origin.");
			}
		} else {
			if (board.containsKey(place)) {
				// Can't place on existing piece
				throw new HantoException("New piece cannot be placed on existing piece.");
			}
			
			for (HantoCoordinateImpl pos : board.keySet()) {
				if (place.isAdjacentTo(pos)) {
					return;
				}
			}
			
			throw new HantoException("New piece not adjacent to existing piece.");
		}
	}

	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		HantoCoordinateImpl loc = new HantoCoordinateImpl(where);
		
		return board.get(loc);
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		String boardStr = "";
		
		// "Row" can be calculated using formula x+2y
		int lowestRow = Integer.MAX_VALUE;
		int highestRow = Integer.MIN_VALUE;
		
		int lowestCol = Integer.MAX_VALUE;
		int highestCol = Integer.MIN_VALUE;
		
		// Find boundary of board
		for (HantoCoordinateImpl pos : board.keySet()) {
			int rowVal = pos.getX() + 2 * pos.getY();
			
			if (rowVal < lowestRow) {
				lowestRow = rowVal;
			}
			
			if (rowVal > highestRow) {
				highestRow = rowVal;
			}
			
			if (pos.getX() < lowestCol) {
				lowestCol = pos.getX();
			}
			
			if (pos.getX() > highestCol) {
				highestCol = pos.getX();
			}
		}
		
		// Iterate over board
		for (int row = highestRow; row >= lowestRow; row--) {
			for (int col = lowestCol; col <= highestCol; col++) {
				if ((row - col) % 2 != 0) {
					// Coordinate doesn't exist
					boardStr += " ";
				} else {				
					int x = col;
					int y = (row - col) / 2;
					
					HantoCoordinateImpl hex = new HantoCoordinateImpl(x, y);
					
					if (board.containsKey(hex)) {
						boardStr += board.get(hex).getType().getSymbol();
					} else {
						boardStr += " ";
					}
				}
			}
			
			boardStr += "\n";
		}
		
		return boardStr;
	}

}

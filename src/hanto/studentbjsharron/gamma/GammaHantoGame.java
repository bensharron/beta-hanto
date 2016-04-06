/**
 * 
 */
package hanto.studentbjsharron.gamma;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentbjsharron.common.HantoCoordinateImpl;
import hanto.studentbjsharron.common.HantoPieceImpl;

/**
 * @author Ben Sharron
 *
 */
public class GammaHantoGame implements HantoGame {
	private final HantoPlayerColor startingPlayer;
	private HantoPlayerColor currentPlayer;
	private int turnNumber;
	private HantoCoordinateImpl blueButterflyLoc, redButterflyLoc;
	private boolean gameOver;
	private Map<HantoCoordinateImpl, HantoPiece> board;
	private final int numTurns;

	/**
	 * Standard constructor for BetaHantoGame with given starting player
	 * @param movesFirst player that will make the first move
	 */
	public GammaHantoGame(HantoPlayerColor movesFirst) {
		startingPlayer = movesFirst;
		currentPlayer = movesFirst;
		turnNumber = 1;
		blueButterflyLoc = redButterflyLoc = null;
		gameOver = false;
		board = new HashMap<HantoCoordinateImpl, HantoPiece>();
		numTurns = 6;
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
		
		// copy constructors
		HantoCoordinateImpl toImpl = new HantoCoordinateImpl(to);
		HantoCoordinateImpl fromImpl = null;
		
		if (from != null) {
			fromImpl = new HantoCoordinateImpl(from);
		}
		
		checkValidPiece(pieceType);
		checkValidLocation(fromImpl, toImpl);
		
		if (from == null) {
			checkValidNewPiece(pieceType);
			checkValidNewLocation(toImpl);
		} else {
			removePiece(pieceType, fromImpl);
		}
		
		placePiece(pieceType, toImpl);
		
		if (currentPlayer == startingPlayer) {
			// Next turn
			turnNumber++;
		}
		
		return checkEndgameConditions();
	}

	/**
	 * Checks if the piece being referenced is a part of this game
	 * @param pieceType piece to check if it's valid in this game iteration
	 * @throws HantoException if piece is invalid
	 */
	private void checkValidPiece(HantoPieceType pieceType) throws HantoException {
		if (pieceType != BUTTERFLY && pieceType != SPARROW) {
			throw new HantoException("Piece must be a butterfly or a sparrow.");
		}
	}

	/**
	 * Checks that new piece doesn't break any rules about its type
	 * @param pieceType piece to check if it's a valid new piece
	 * @throws HantoException if piece is invalid
	 */
	private void checkValidNewPiece(HantoPieceType pieceType) throws HantoException {
		if (currentPlayer == BLUE) {
			if (pieceType == BUTTERFLY && blueButterflyLoc != null) {
				// Must be first one
				throw new HantoException("Blue player has already played his butterfly.");
			}
			
			if (turnNumber >= 4 && pieceType != BUTTERFLY && blueButterflyLoc == null) {
				// Butterfly must be placed by turn 4
				throw new HantoException("Blue player must play his butterfly.");
			}
		} else {
			if (pieceType == BUTTERFLY && redButterflyLoc != null) {
				// Must be first one
				throw new HantoException("Red player has already played his butterfly.");
			}
			
			if (turnNumber >= 4 && pieceType != BUTTERFLY && redButterflyLoc == null) {
				// Butterfly must be placed by turn 4
				throw new HantoException("Red player must play his butterfly.");
			}
		}
	}
	
	/**
	 * Helper function to check if new coordinate is valid under rules of BetaHanto
	 * @param place HantoCoordinate to check to guarantee the coordinate is valid
	 */
	private void checkValidLocation(HantoCoordinateImpl from, HantoCoordinateImpl to) throws HantoException {
		if (currentPlayer == startingPlayer && turnNumber == 1 &&
				(to.getX() != 0 || to.getY() != 0)) {
			throw new HantoException("First player did not make first move to origin.");
		}
		
		if (board.containsKey(to)) {
			throw new HantoException("Piece cannot be placed on existing piece.");
		}
	}

	/**
	 * Method to check the location for a new placed piece for validity
	 * @param to New Coordinate to check for validity
	 * @throws HantoException if new coordinate is invalid
	 */
	private void checkValidNewLocation(HantoCoordinateImpl to) throws HantoException {
		if (turnNumber > 1)	{
			boolean adjacentToSameColor = false;
			
			for (HantoCoordinateImpl adj : to.getAdjacentCoordinates()) {
				if (board.get(adj) == null) {
					continue;
				}
				
				if (currentPlayer != board.get(adj).getColor()) {
					throw new HantoException("New piece adjacent to existing piece of other color.");
				}
					
				adjacentToSameColor = true;
				break;
			}
			
			if (!adjacentToSameColor) {
				throw new HantoException("New piece not adjacent to existing piece of the same color.");
			}
		}
	}
	
	/**
	 * Method to place piece on the board and update relevant state variables
	 * @param pieceType piece that is being placed
	 * @param place location that the piece is being placed at
	 */
	private void placePiece(HantoPieceType pieceType, HantoCoordinateImpl place) {
		board.put(place, new HantoPieceImpl(currentPlayer, pieceType));
		
		if (currentPlayer == BLUE) {
			if (pieceType == BUTTERFLY) {
				blueButterflyLoc = place;
			}
			
			currentPlayer = RED;
		} else {
			if (pieceType == BUTTERFLY) {
				redButterflyLoc = place;
			}

			currentPlayer = BLUE;
		}
	}

	/**
	 * Remove piece from board, usually in preparation for replacing it after a move
	 * @param pieceType Piece type being removed
	 * @param loc Location the piece is being removed from
	 * @throws HantoException if piece trying to be removed doesn't match piece at that location
	 */
	private void removePiece(HantoPieceType pieceType, HantoCoordinateImpl loc) throws HantoException {
		HantoPiece currPiece = board.get(loc);
		
		if (currPiece.getColor() == currentPlayer && currPiece.getType() == pieceType) {
			// Piece matches qualifications
			board.remove(loc);
		} else {
			throw new HantoException("Piece being moved doesn't match piece at location given.");
		}
	}
	
	/**
	 * Method to evaluate the end game conditions and determine the correct result
	 * @return the correct result of the current move
	 */
	private MoveResult checkEndgameConditions() {
		boolean blueSurr = isSurrounded(blueButterflyLoc);
		
		// Check end-game conditions
		if (isSurrounded(redButterflyLoc)) {
			gameOver = true;
			
			if (blueSurr) {
				return DRAW;
			} else {
				return BLUE_WINS;
			}
		} else if (blueSurr) {
			gameOver = true;
			return RED_WINS;
		}
		
		if (turnNumber > numTurns) {
			gameOver = true;
			return DRAW;
		}

		return OK;
	}

	/**
	 * Method to determine if a piece is surrounded
	 * @return true if the piece is surrounded
	 */
	private boolean isSurrounded(HantoCoordinateImpl loc) {
		if (loc == null) {
			return false;
		}
		
		for (HantoCoordinateImpl hex : loc.getAdjacentCoordinates()) {
			if (board.get(hex) == null) {
				return false;
			}
		}
		
		return true;
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

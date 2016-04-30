/**
 * 
 */
package hanto.studentbjsharron.common;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.*;

import java.util.LinkedList;
import java.util.List;

import hanto.common.*;

/**
 * Abstract base class for Hanto Game versions
 * @author Ben Sharron
 *
 */
public abstract class BaseHantoGame implements HantoGame {
	protected final HantoPlayerColor startingPlayer;
	protected HantoPlayerColor currentPlayer;
	protected int turnNumber;
	protected boolean gameOver;
	protected final HantoBoard board;
	protected int numTurns;
	protected boolean canMove;
	protected boolean canResign;
	protected HantoRuleSet rules;
	protected HantoPlayers players;
	
	protected HantoPieceType movingPieceType;
	protected HantoCoordinateImpl fromLoc, toLoc;

	/**
	 * Standard constructor for HantoGame with given starting player
	 * @param movesFirst player that will make the first move
	 */
	protected BaseHantoGame(HantoPlayerColor movesFirst) {
		rules = new HantoRuleSet();
		players = new HantoPlayers();
		players.addPlayer(BLUE, new HantoPlayerState());
		players.addPlayer(RED, new HantoPlayerState());
		
		startingPlayer = movesFirst;
		currentPlayer = movesFirst;
		turnNumber = 1;
		gameOver = false;
		board = new HantoBoard();
		
		// Default
		canMove = true;
		canResign = false;
	}
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		movingPieceType = pieceType;
		fromLoc = from == null ? null : new HantoCoordinateImpl(from);
		toLoc = to == null ? null : new HantoCoordinateImpl(to);
		
		if (checkResign()) {
			gameOver = true;
			return currentPlayer == BLUE ? RED_WINS : BLUE_WINS;
		}
		
		if (gameOver) {
			throw new HantoException("You cannot move after the game has finished.");
		}
		
		checkValidPiece();
		checkValidLocation();
		
		if (from == null) {
			checkValidNewPiece();
			checkValidNewLocation();
			addNewPiece();
		} else {
			checkCanMove();
			checkValidMove();
			movePiece();
		}
		
		checkContiguousBoard();
		
		if (currentPlayer == startingPlayer) {
			// Next turn
			turnNumber++;
		}
		
		return checkEndgameConditions();
	}

	protected boolean checkResign() throws HantoPrematureResignationException {
		return canResign && movingPieceType == null && fromLoc == null && toLoc == null;
	}

	private void addNewPiece() {
		players.getPlayerState(currentPlayer).placePiece(movingPieceType, toLoc);
		
		placePiece();
	}

	/**
	 * Add new piece type with given number of pieces for each player
	 * @param piece Piece type to add
	 * @param numPieces Number of pieces of this type each player starts with
	 */
	protected void addPiece(HantoPieceType piece, int numPieces) {
		players.getPlayerState(BLUE).addPiece(piece, numPieces);
		players.getPlayerState(RED).addPiece(piece, numPieces);
	}
	
	private void checkCanMove() throws HantoException {
		if (!canMove) {
			throw new HantoException("This version of Hanto doesn't support movement");
		}
	}

	/**
	 * Checks if the current move is valid under the current rules
	 * @throws HantoException if move is invalid
	 */
	private void checkValidMove() throws HantoException {
		if (players.getPlayerState(currentPlayer).getButterflyLoc() == null) {
			throw new HantoException("Player can't move until the blue butterfly is placed.");
		}
		
		if (!rules.hasRuleFor(movingPieceType)) {
			throw new HantoException("This game has no rule for moving a " + movingPieceType + ".");
		}
		
		rules.getRule(movingPieceType).checkValidMove(fromLoc, toLoc, board);
	}
	
	/**
	 * Method to place piece on the board and update relevant state variables
	 */
	private void placePiece() {
		board.addPiece(new HantoPieceImpl(currentPlayer, movingPieceType), toLoc);
		
		currentPlayer = currentPlayer == BLUE ? RED : BLUE;
	}
	
	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		HantoCoordinateImpl loc = new HantoCoordinateImpl(where);
		
		return board.getPiece(loc);
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard() {
		return board.getPrintableBoard();
	}
	
	/**
	 * Checks that new piece doesn't break any rules about its type
	 * @throws HantoException if piece is invalid
	 */
	private void checkValidNewPiece() throws HantoException {
		if (!players.getPlayerState(currentPlayer).hasPieceLeft(movingPieceType)) {
			throw new HantoException("Player has no " + movingPieceType + ".");
		}
	}
	
	/**
	 * Method to evaluate the end game conditions and determine the correct result
	 * @return the correct result of the current move
	 */
	private MoveResult checkEndgameConditions() {		
		boolean blueSurr = isSurrounded(players.getPlayerState(BLUE).getButterflyLoc());
		
		// Check end-game conditions
		if (isSurrounded(players.getPlayerState(RED).getButterflyLoc())) {
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
	 * @return true if		placePiece(pieceType, toLoc); the piece is surrounded
	 */
	private boolean isSurrounded(HantoCoordinateImpl loc) {
		if (loc == null) {
			return false;
		}
		
		for (HantoCoordinateImpl hex : loc.getAdjacentCoordinates()) {
			if (board.getPiece(hex) == null) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * @throws HantoException if piece is invalid
	 */
	private void checkValidPiece() throws HantoException {
		if (!players.getPlayerState(currentPlayer).usesPiece(movingPieceType)) {
			throw new HantoException("Piece must be valid in this game version.");
		}
		
		if (isNotValidPiece(movingPieceType)) {
			// Butterfly must be placed by turn 4
			throw new HantoException("Player must play his butterfly.");
		}
	}
	
	private boolean isNotValidPiece(HantoPieceType piece) {
		return turnNumber >= 4 && piece != BUTTERFLY && players.getPlayerState(currentPlayer).getButterflyLoc() == null;
	}

	/**
	 * Helper function to check if new coordinate is valid under rules of BetaHanto
	 */
	private void checkValidLocation() throws HantoException {
		if (board.hasPieceAt(toLoc)) {
			throw new HantoException("New piece cannot be placed on existing piece.");
		}
	}
	
	/**
	 * Method to check the location for a new placed piece for validity
	 * @throws HantoException if new coordinate is invalid
	 */
	private void checkValidNewLocation() throws HantoException {
		if (turnNumber == 1) {
			if (currentPlayer == startingPlayer) {
				if (!toLoc.equals(new HantoCoordinateImpl(0, 0))) {
					throw new HantoException("First player did not make first move to origin.");
				}
				
				return;
			}
			
			if (!toLoc.isAdjacentTo(new HantoCoordinateImpl(0, 0))) {
				throw new HantoException("Second player did not make first move adjacent to origin.");
			}
			
			return;
		}
		
		boolean adjacentToSameColor = false;
			
		for (HantoCoordinateImpl adj : toLoc.getAdjacentCoordinates()) {
			if (board.getPiece(adj) == null) {
				continue;
			}
			
			if (IsOpponent(adj)) {
				throw new HantoException("New piece adjacent to existing piece of other color.");
			}
				
			adjacentToSameColor = true;
			break;
		}
		
		if (!adjacentToSameColor) {
			throw new HantoException("New piece not adjacent to existing piece of the same color.");
		}
	}

	/**
	 * Check if the piece at given location is the opponent's
	 * @param loc Location to check
	 * @return whether the piece belongs to the opponent
	 */
	protected boolean IsOpponent(HantoCoordinateImpl loc) {
		return currentPlayer != board.getPiece(loc).getColor();
	}
	
	/**
	 * Check if the board is contiguous
	 * @throws HantoException if the board is not contiguous
	 */
	public void checkContiguousBoard() throws HantoException {
		if (!board.checkContiguousBoard()) {
			throw new HantoException("Move causes board to become discontiguous.");
		}
	}
	
	/**
	 * Remove piece from board and put it at its new location
	 * @throws HantoException if piece trying to be moved doesn't match piece at that location
	 */
	private void movePiece() throws HantoException {
		HantoPiece currPiece = board.getPiece(fromLoc);
		
		if (currPiece != null && currPiece.getColor() == currentPlayer && currPiece.getType() == movingPieceType) {
			// Piece matches qualifications
			board.removePiece(fromLoc);
		} else {
			throw new HantoException("Piece being moved doesn't match piece at location given.");
		}
		
		placePiece();
	}
	
	private List<HantoMove> getPlayerMoves(HantoPlayerColor color) {
		List<HantoMove> moves = new LinkedList<HantoMove>();
		
		if (players.getPlayerState(currentPlayer).getButterflyLoc() == null) {
			return moves;
		}
		
		for (HantoCoordinateImpl loc : board.getPlayerPieceLocs(color)) {
			HantoPieceType pieceType = board.getPiece(loc).getType();
			
			List<HantoMove> newMoves = rules.getRule(pieceType).getValidMoves(loc, color, board);
			
			for (HantoMove newMove : newMoves) {
				if (boardStaysContiguous(newMove)) {
					moves.add(newMove);
				}
			}
		}
		
		return moves;
	}
	
	private boolean boardStaysContiguous(HantoMove newMove) {
		HantoBoard tempBoard = board.copyBoard();
		
		HantoPieceType piece = newMove.getPiece();
		
		tempBoard.addPiece(new HantoPieceImpl(currentPlayer, piece), newMove.getTo());
		tempBoard.removePiece(newMove.getFrom());
		
		return tempBoard.checkContiguousBoard();
	}

	/**
	 * Get a list of all plays available to the player of the given color
	 * @param color Player whose available plays are being retrieved
	 * @return a list of all plays available to the player of the given color
	 */
	public List<HantoMove> getPlayerPlays(HantoPlayerColor color) {
		List<HantoMove> moves = getPlayerMoves(color);
		
		for (HantoCoordinateImpl place : board.getPlayerValidPlaceLocations(currentPlayer)) {
			List<HantoPieceType> piecesLeft = players.getPlayerState(color).getPiecesLeft();
			
			for (HantoPieceType piece : piecesLeft) {
				if (isNotValidPiece(piece)) {
					continue;
				}
				
				moves.add(new HantoMove(piece, null, place));
			}
		}
		
		return moves;
	}
}

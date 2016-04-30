/**
 * 
 */
package hanto.studentbjsharron.common;

import java.util.*;

import hanto.common.*;

/**
 * Class to deal with a generic Hanto Board
 * @author Ben Sharron
 *
 */
public class HantoBoard implements Iterable<HantoCoordinateImpl> {

	private Map<HantoCoordinateImpl, HantoPiece> board;
	
	/**
	 * HantoBoard constructor to initialize 
	 */
	public HantoBoard() {
		board = new HashMap<HantoCoordinateImpl, HantoPiece>();
	}
	
	/**
	 * Adds the given piece to the board at the given location
	 * @param newPiece Piece to add
	 * @param location Location to place the piece at
	 */
	public void addPiece(HantoPiece newPiece, HantoCoordinateImpl location) {
		board.put(location, newPiece);
	}
	
	/**
	 * Tells whether the board has a piece at the given location
	 * @param location Location to check for a piece
	 * @return whether the location has a piece at it
	 */
	public boolean hasPieceAt(HantoCoordinateImpl location) {
		return board.containsKey(location);
	}
	
	/**
	 * Retrieves the piece at the given location
	 * @param location Location to retrieve a piece from
	 * @return The piece at the location
	 */
	public HantoPiece getPiece(HantoCoordinateImpl location) {
		return board.get(location);
	}
	
	/**
	 * Removes the piece at the given location
	 * @param location Location to remove a piece from
	 * @return The piece at the location
	 */
	public HantoPiece removePiece (HantoCoordinateImpl location) {
		return board.remove(location);
	}
	
	/**
	 * Make a copy of the board
	 * @return the board copy
	 */
	public HantoBoard copyBoard() {
		HantoBoard newBoard = new HantoBoard();
		
		for(HantoCoordinateImpl loc : board.keySet()) {
			newBoard.addPiece(board.get(loc), loc);
		}
		
		return newBoard;
	}
	
	/**
	 * Check if the board is contiguous
	 * @return whether the board is contiguous
	 */
	public boolean checkContiguousBoard() {
		if (board.isEmpty())  {
			return true;
		}
		
		HantoCoordinateImpl firstLoc = (HantoCoordinateImpl) board.keySet().toArray()[0];
		
		// Traverse board starting at toImpl to guarantee contiguousness
		Map<HantoCoordinateImpl, Boolean> piecesReached = new HashMap<HantoCoordinateImpl, Boolean>();
		
		for (HantoCoordinateImpl pieceLoc : this) {
			piecesReached.put(pieceLoc, false);
		}
		
		piecesReached.put(firstLoc, true);
		traverseAllAdjacentHexes(firstLoc, piecesReached);
		
		if (piecesReached.containsValue(false)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Recursively perform a breadth-first search on the board
	 * @param startLoc Location to start the traversal at
	 * @param piecesReached Map of pieces telling which ones have previously been seen
	 */
	private void traverseAllAdjacentHexes(HantoCoordinateImpl startLoc, Map<HantoCoordinateImpl, Boolean> piecesReached) {
		for (HantoCoordinateImpl adj : startLoc.getAdjacentCoordinates()) {
			if (!this.hasPieceAt(adj) || piecesReached.get(adj)) {
				// Piece isn't on board or piece has already been traversed
				continue;
			}
			
			piecesReached.put(adj, true);
			traverseAllAdjacentHexes(adj, piecesReached);
		}
	}
	
	/**
	 * Returns a string version of the board that can be printed to the screen
	 * @return A string version of the board
	 */
	public String getPrintableBoard()
	{
		String boardStr = "";
		
		// "Row" can be calculated using formula x+2y
		int lowestRow = Integer.MAX_VALUE;
		int highestRow = Integer.MIN_VALUE;
		
		int lowestCol = Integer.MAX_VALUE;
		int highestCol = Integer.MIN_VALUE;
		
		// Find boundary of board
		for (HantoCoordinateImpl pos : this) {
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
					
					if (this.hasPieceAt(hex)) {
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

	@Override
	public Iterator<HantoCoordinateImpl> iterator() {
		return board.keySet().iterator();
	}
	
	public String toString() {
		return this.getPrintableBoard();
	}

	/**
	 * Returns a list of locations with pieces of the given player color
	 * @param player
	 * @return a list of coordinates of pieces on the board of the the given color
	 */
	public List<HantoCoordinateImpl> getPlayerPieceLocs(HantoPlayerColor color) {
		List<HantoCoordinateImpl> playerPieceLocs = new LinkedList<HantoCoordinateImpl>();
		
		for (HantoCoordinateImpl loc : board.keySet()) {
			if (board.get(loc).getColor().equals(color)) {
				playerPieceLocs.add(loc);
			}
		}
		
		return playerPieceLocs;
	}
	
	/**
	 * Return a list of locations on the board where the given player could place a new piece
	 * @param color Color of player being checked
	 * @return a list of locations on the board where the given player could place a new piece
	 */
	public List<HantoCoordinateImpl> getPlayerValidPlaceLocations(HantoPlayerColor color) {
		List<HantoCoordinateImpl> validPlaceLocs = new LinkedList<HantoCoordinateImpl>();
		
		if (board.isEmpty()) {
			// Empty board so can place at origin
			validPlaceLocs.add(new HantoCoordinateImpl(0, 0));
			
			return validPlaceLocs;
		}
		
		for (HantoCoordinateImpl piece : getPlayerPieceLocs(color)) {
			for (HantoCoordinateImpl loc : piece.getAdjacentCoordinates()) {
				if (board.containsKey(loc) || validPlaceLocs.contains(loc)) {
					continue;
				}
				
				boolean noAdjOpps = true;
				
				for (HantoCoordinateImpl adj : loc.getAdjacentCoordinates()) {
					if (board.containsKey(adj) && board.get(adj).getColor() != color) {
						noAdjOpps = false;
						break;
					}
				}
				
				if (noAdjOpps) {
					validPlaceLocs.add(loc);
				}
			}
		}
		
		return validPlaceLocs;
	}
}

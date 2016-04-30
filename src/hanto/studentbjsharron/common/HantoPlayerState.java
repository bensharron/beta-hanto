/**
 * 
 */
package hanto.studentbjsharron.common;

import static hanto.common.HantoPieceType.*;

import java.util.*;

import hanto.common.*;

/**
 * Object to hold the current state of a player and his pieces
 * @author Ben Sharron
 *
 */
public class HantoPlayerState {
	private HantoCoordinateImpl butterflyLoc;
	private Map<HantoPieceType, Integer> piecesLeft;
	
	public HantoPlayerState() {
		butterflyLoc = null;
		piecesLeft = new HashMap<HantoPieceType, Integer>();
		
		piecesLeft.put(BUTTERFLY, 1);
	}
	
	/**
	 * Add a new piece type for the given player
	 * @param piece Piece type to keep track of
	 * @param numPieces Number of pieces of this type to start out with
	 */
	public void addPiece(HantoPieceType piece, int numPieces) {
		piecesLeft.put(piece, numPieces);
	}
	
	/**
	 * Use a piece, storing the butterfly's location
	 * @param piece Piece being used
	 * @param loc Location being placed at for storing the butterfly's location
	 */
	public void placePiece(HantoPieceType piece, HantoCoordinateImpl loc) {
		piecesLeft.put(piece, piecesLeft.get(piece) - 1);
		
		if (piece == BUTTERFLY) {
			butterflyLoc = loc;
		}
	}
	
	/**
	 * Check whether the player has pieces of the given type available
	 * @param piece Piece type to check
	 * @return whether the piece is available
	 */
	public boolean hasPieceLeft(HantoPieceType piece) {
		return piecesLeft.containsKey(piece) && piecesLeft.get(piece) > 0;
	}
	
	/**
	 * Check whether the player uses pieces of the given type
	 * @param piece Piece type to check
	 * @return whether the piece is available
	 */
	public boolean usesPiece(HantoPieceType piece) {
		return piecesLeft.containsKey(piece);
	}
	
	/**
	 * Return list of pieces that the player hasn't exhausted yet
	 * @return list of pieces that the player hasn't exhausted yet
	 */
	public List<HantoPieceType> getPiecesLeft() {
		List<HantoPieceType> pieces = new LinkedList<HantoPieceType>();
		
		for (HantoPieceType piece : piecesLeft.keySet()) {
			if (piecesLeft.get(piece) > 0) {
				pieces.add(piece);
			}
		}
		
		return pieces;
	}
	
	/**
	 * Get the location of the butterfly for this player
	 * @return the location of the butterfly
	 */
	public HantoCoordinateImpl getButterflyLoc() {
		return butterflyLoc;
	}
}

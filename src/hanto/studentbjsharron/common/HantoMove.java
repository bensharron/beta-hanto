/**
 * 
 */
package hanto.studentbjsharron.common;

import hanto.common.HantoPieceType;

/**
 * @author Ben Sharron
 *
 */
public class HantoMove {
	private final HantoPieceType piece;
	private final HantoCoordinateImpl from;
	private final HantoCoordinateImpl to;
	
	public HantoMove(HantoPieceType piece, HantoCoordinateImpl from, HantoCoordinateImpl to) {
		this.piece = piece;
		this.from = from;
		this.to = to;
	}
	
	public HantoPieceType getPiece() {
		return piece;
	}
	
	public HantoCoordinateImpl getFrom() {
		return from;
	}
	
	public HantoCoordinateImpl getTo() {
		return to;
	}
}

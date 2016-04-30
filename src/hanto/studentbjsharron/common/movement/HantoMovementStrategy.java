package hanto.studentbjsharron.common.movement;

import java.util.LinkedList;
import java.util.List;

import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentbjsharron.common.HantoBoard;
import hanto.studentbjsharron.common.HantoCoordinateImpl;
import hanto.studentbjsharron.common.HantoMove;

public abstract class HantoMovementStrategy implements HantoMovementRule {

	@Override
	public abstract void checkValidMove(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board) throws HantoException;
	
	protected abstract boolean isValidMove(HantoCoordinateImpl from, HantoCoordinateImpl to, HantoBoard board);

	@Override
	public List<HantoMove> getValidMoves(HantoCoordinateImpl from, HantoPlayerColor color, HantoBoard board) {
		List<HantoMove> validMoves = new LinkedList<HantoMove>();
		HantoPieceType piece = board.getPiece(from).getType();
		
		List<HantoCoordinateImpl> emptyAdjLocs = board.getEmptyAdjLocs();
		
		for (HantoCoordinateImpl hex : emptyAdjLocs) {
			if (isValidMove(from, hex, board)) {
				validMoves.add(new HantoMove(piece, from, hex));
			}
		}
		
		return validMoves;
	}

}

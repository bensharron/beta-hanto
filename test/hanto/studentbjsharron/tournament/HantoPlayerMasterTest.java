/**
 * 
 */
package hanto.studentbjsharron.tournament;

import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.HantoGameID.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentbjsharron.HantoGameFactory;
import hanto.studentbjsharron.common.HantoCoordinateImpl;
import hanto.tournament.*;

import org.junit.*;

/**
 * @author Ben Sharron
 *
 */
public class HantoPlayerMasterTest {

	private static HantoGamePlayer player1, player2;
	private static HantoGameFactory factory;
	private HantoGame game;
	
	@BeforeClass
	public static void initializeClass()
	{
		player1 = new HantoPlayer();
		player2 = new HantoPlayer();
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		game = factory.makeHantoGame(EPSILON_HANTO);
		player1.startGame(EPSILON_HANTO, BLUE, true);
		player2.startGame(EPSILON_HANTO, RED, false);
	}
	
	@Test   // 1
	public void blueMakesValidMoveOnFirstMove() throws HantoException
	{
		HantoMoveRecord move = player1.makeMove(null);
		MoveResult mr = game.makeMove(move.getPiece(), move.getFrom(), move.getTo());
		
		assertEquals(OK, mr);
	}
	
	@Test   // 2
	public void redMakesValidMoveOnFirstMove() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		
		HantoMoveRecord move = player2.makeMove(new HantoMoveRecord(BUTTERFLY, null, makeCoordinate(0, 0)));
		MoveResult mr = game.makeMove(move.getPiece(), move.getFrom(), move.getTo());
		
		assertEquals(OK, mr);
	}
	
	@Test   // 3
	public void blueMakesValidMoveOnFirstMove() throws HantoException
	{
		HantoMoveRecord move = player1.makeMove(null);
		MoveResult mr = game.makeMove(move.getPiece(), move.getFrom(), move.getTo());
		
		assertEquals(OK, mr);
	}

	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new HantoCoordinateImpl(x, y);
	}
}

/**
 * 
 */
package hanto.studentbjsharron.epsilon;

import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentbjsharron.HantoGameFactory;
import hanto.studentbjsharron.common.HantoCoordinateImpl;

import org.junit.*;

/**
 * @author Ben Sharron
 *
 */
public class EpsilonHantoMasterTest {

	private static HantoGameFactory factory;
	private HantoGame game;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO);
	}
	
	@Test(expected=HantoPrematureResignationException.class)   // 1
	public void blueCannotResignOnFirstMove() throws HantoException
	{
		game.makeMove(null, null, null);
	}
	
	@Test(expected=HantoPrematureResignationException.class)   // 2
	public void redCannotResignOnSecondMove() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(null, null, null);
	}
	
	@Test(expected=HantoPrematureResignationException.class)   // 3
	public void redCantResignWhenTheyHaveAMove() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(-1, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, makeCoordinate(-1, 1), makeCoordinate(0, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(-2, 1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(-1, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(1, 1));
		game.makeMove(BUTTERFLY, makeCoordinate(-1, 1), makeCoordinate(0, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(-2, 2));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(-1, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		game.makeMove(null, null, null);
	}
	
	@Test   // 4
	public void redCanResignWhenTheyHaveNoMoves() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(-1, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, makeCoordinate(-1, 1), makeCoordinate(0, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(-2, 1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(-1, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(1, 1));
		game.makeMove(BUTTERFLY, makeCoordinate(-1, 1), makeCoordinate(0, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(-2, 2));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(-1, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		game.makeMove(BUTTERFLY, makeCoordinate(-1, 1), makeCoordinate(0, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(-1, 2));
		MoveResult mr = game.makeMove(null, null, null);
		
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test   // 5
	public void blueCanPlaceHorse() throws HantoException
	{
		MoveResult mr = game.makeMove(HORSE, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
	}
	
	@Test   // 6
	public void horseCanJumpX() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		
		game.makeMove(HORSE, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		
		MoveResult mr = game.makeMove(HORSE, makeCoordinate(-1, 0), makeCoordinate(2, 0));
		assertEquals(OK, mr);
	}
	
	@Test   // 7
	public void horseCanJumpY() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(HORSE, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		
		MoveResult mr = game.makeMove(HORSE, makeCoordinate(0, -1), makeCoordinate(0, 2));
		assertEquals(OK, mr);
	}
	
	@Test   // 8
	public void horseCanJumpXY() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		
		game.makeMove(HORSE, null, makeCoordinate(-1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -2));
		
		MoveResult mr = game.makeMove(HORSE, makeCoordinate(-1, 1), makeCoordinate(3, -3));
		assertEquals(OK, mr);
	}
	
	@Test(expected=HantoException.class)   // 9
	public void horseMustJumpInStraightLine() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(HORSE, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		
		game.makeMove(HORSE, makeCoordinate(0, -1), makeCoordinate(-1, 1));
	}
	
	@Test(expected=HantoException.class)   // 10
	public void horseCantJumpToAdjacentHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(HORSE, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		
		game.makeMove(HORSE, makeCoordinate(0, -1), makeCoordinate(-1, 0));
	}
	
	@Test(expected=HantoException.class)   // 11
	public void horseCantJumpOverEmptyHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(HORSE, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		
		game.makeMove(HORSE, makeCoordinate(-1, 0), makeCoordinate(-1, 3));
	}
	
	@Test(expected=HantoException.class)   // 12
	public void sparrowCantFlyMoreThanFour() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -1));
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(CRAB, null, makeCoordinate(1, 1));
		
		game.makeMove(SPARROW, makeCoordinate(0, -2), makeCoordinate(0, 3));
	}
	
	@Test   // 13
	public void sparrowCanFlyFour() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		
		game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(0, 3));
	}
	
	@Test(expected=HantoException.class)   // 14
	public void blueCantResignWhenTheyCanFly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -1));
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -2));
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -3));
		game.makeMove(CRAB, null, makeCoordinate(0, 4));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -4));
		game.makeMove(CRAB, null, makeCoordinate(0, 5));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -5));
		game.makeMove(CRAB, null, makeCoordinate(0, 6));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -6));
		game.makeMove(CRAB, null, makeCoordinate(0, 7));
		
		game.makeMove(HORSE, null, makeCoordinate(0, -7));
		game.makeMove(HORSE, null, makeCoordinate(0, 8));
		
		game.makeMove(HORSE, null, makeCoordinate(0, -8));
		game.makeMove(HORSE, null, makeCoordinate(0, 9));
		
		game.makeMove(HORSE, null, makeCoordinate(0, -9));
		game.makeMove(HORSE, null, makeCoordinate(0, 10));
		
		game.makeMove(HORSE, null, makeCoordinate(0, -10));
		game.makeMove(HORSE, null, makeCoordinate(0, 11));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -11));
		game.makeMove(SPARROW, null, makeCoordinate(0, 12));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -12));
		game.makeMove(SPARROW, null, makeCoordinate(0, 13));
		
		game.makeMove(null, null, null);
	}
	
	@Test(expected=HantoException.class)   // 15
	public void blueCantResignWhenTheyCanJump() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -1));
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -2));
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -3));
		game.makeMove(CRAB, null, makeCoordinate(0, 4));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -4));
		game.makeMove(CRAB, null, makeCoordinate(0, 5));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -5));
		game.makeMove(CRAB, null, makeCoordinate(0, 6));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -6));
		game.makeMove(CRAB, null, makeCoordinate(0, 7));
		
		game.makeMove(HORSE, null, makeCoordinate(0, -7));
		game.makeMove(HORSE, null, makeCoordinate(0, 8));
		
		game.makeMove(HORSE, null, makeCoordinate(0, -8));
		game.makeMove(HORSE, null, makeCoordinate(0, 9));
		
		game.makeMove(HORSE, null, makeCoordinate(0, -9));
		game.makeMove(HORSE, null, makeCoordinate(0, 10));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -10));
		game.makeMove(SPARROW, null, makeCoordinate(0, 11));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -11));
		game.makeMove(SPARROW, null, makeCoordinate(0, 12));
		
		game.makeMove(HORSE, null, makeCoordinate(0, -12));
		game.makeMove(HORSE, null, makeCoordinate(0, 13));
		
		game.makeMove(null, null, null);
	}

	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new HantoCoordinateImpl(x, y);
	}
}

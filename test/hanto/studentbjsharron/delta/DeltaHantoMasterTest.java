/**
 * 
 */
package hanto.studentbjsharron.delta;

import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentbjsharron.HantoGameFactory;
import hanto.studentbjsharron.common.HantoCoordinateImpl;

import org.junit.*;

/**
 * @author Ben Sharron
 *
 */
public class DeltaHantoMasterTest {

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
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, BLUE);
	}
	
	@Test   // 1
	public void blueCanResign() throws HantoException
	{
		final MoveResult mr = game.makeMove(null, null, null);
		assertEquals(RED_WINS, mr);
	}
	
	@Test   // 2
	public void redCanResign() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult mr = game.makeMove(null, null, null);
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test(expected=HantoException.class)   // 3
	public void resigningEndsTheGame() throws HantoException
	{
		game.makeMove(null, null, null);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	}
	
	@Test(expected=HantoException.class)  // 4
	public void cantPlaceACrane() throws HantoException
	{
		game.makeMove(CRANE, null, makeCoordinate(0, 0));
	}
	
	@Test  // 5
	public void bluePlacesSparrow() throws HantoException  // 5
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
	}
	
	@Test  // 6
	public void redPlacesCrab() throws HantoException  // 6
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(CRAB, null, makeCoordinate(1, 0));
	}
	
	@Test  // 8
	public void blueButterflyCanWalk() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, 0));
	}
	
	@Test(expected=HantoException.class)   // 8
	public void blueOnlyHasFourSparrows() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(SPARROW, null, makeCoordinate(0, 4));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -4));
		game.makeMove(SPARROW, null, makeCoordinate(0, 5));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -5));
	}
	
	@Test(expected=HantoException.class)   // 9
	public void redOnlyHasFourCrabs() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -3));
		game.makeMove(CRAB, null, makeCoordinate(0, 4));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(CRAB, null, makeCoordinate(0, 5));
		
		game.makeMove(BUTTERFLY, makeCoordinate(1, -3), makeCoordinate(1, -4));
		game.makeMove(CRAB, null, makeCoordinate(0, 5));
	}
	
	@Test(expected=HantoException.class)  // 10
	public void butterflyCanOnlyWalkOneSpace() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(-1, 0));
	}
	
	@Test  // 11
	public void sparrowCanFly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));
		
		MoveResult mr = game.makeMove(SPARROW, makeCoordinate(0, -2), makeCoordinate(0, 4));
		assertEquals(OK, mr);
	}
	
	@Test(expected=HantoException.class)  // 12
	public void sparrowCannotBreakContiguousness() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));
		
		game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(0, 4));
	}
	
	@Test  // 13
	public void crabCanWalkOne() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -1));
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -2));
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		
		MoveResult mr = game.makeMove(CRAB, makeCoordinate(0, -2), makeCoordinate(1, -2));
		assertEquals(OK, mr);
	}
	
	@Test  // 14
	public void crabCanWalkTwo() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -1));
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -2));
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		
		MoveResult mr = game.makeMove(CRAB, makeCoordinate(0, -2), makeCoordinate(1, -1));
		assertEquals(OK, mr);
	}
	
	@Test  // 15
	public void crabCanWalkThree() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -1));
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		
		game.makeMove(CRAB, null, makeCoordinate(0, -2));
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		
		MoveResult mr = game.makeMove(CRAB, makeCoordinate(0, -2), makeCoordinate(1, 0));
		assertEquals(OK, mr);
	}
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new HantoCoordinateImpl(x, y);
	}
}

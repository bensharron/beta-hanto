/**
 * 
 */
package hanto.studentbjsharron.gamma;

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
public class GammaHantoMasterTest {

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
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, BLUE);
	}

	@Test(expected = HantoException.class)   // 1
	public void bluePlacesInitialButterflyOffOrigin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	}
	
	@Test(expected = HantoException.class)   // 2
	public void redPlacesButterflyOnBlueButterfly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	}
	
	@Test(expected = HantoException.class)   // 3
	public void redPlacesInitialButterflyNotAdjacentToOrigin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(2, 0));
	}
	
	@Test(expected = HantoException.class)	// 4
	public void blueTriesToPlaceCrab() throws HantoException
	{
		game.makeMove(CRAB, null, makeCoordinate(0, 0));
	}
	
	@Test(expected = HantoException.class)	// 5
	public void redTriesToPlaceCrane() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(CRANE, null, makeCoordinate(-1, 0));
	}
	
	@Test(expected = HantoException.class)  // 6
	public void blueTriesToPlaceSecondButterflyOnTurnTwo() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
	}
	
	@Test(expected = HantoException.class)  // 7
	public void redTriesToPlaceSecondButterflyOnTurnTwo() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(2, 0));
	}
	
	@Test  // 8
	public void bigPrintableBoardIsCorrect() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-2, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		
		String correctStr = " S \n" +
							"  S\n" +
							"   \n" +
							"S B\n" +
							" B \n";
		
		assertEquals(correctStr, game.getPrintableBoard());
	}
	
	@Test(expected = HantoException.class)  // 9
	public void blueButterflyNeedsToBePlayedByTurnFour() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0));
		game.makeMove(SPARROW, null, makeCoordinate(3, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-3, 0));
	}
	
	@Test(expected = HantoException.class)  // 10
	public void redButterflyNeedsToBePlayedByTurnFour() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0));
		game.makeMove(SPARROW, null, makeCoordinate(3, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-3, 0));
		game.makeMove(SPARROW, null, makeCoordinate(4, 0));
	}
	
	@Test(expected=HantoException.class)  // 11
	public void secondTurnBlueCantPlayNextToRed() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
	}
	
	@Test(expected=HantoException.class)  // 12
	public void secondTurnRedCantPlayNextToBlue() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
	}
	
	@Test(expected=HantoException.class)  // 13
	public void secondTurnBlueCantPlayNonAdjacent() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
	}
	
	@Test(expected=HantoException.class)  // 14
	public void secondTurnRedCantPlayNonAdjacent() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
	}
	
	@Test  // 15
	public void blueButterflyMovesSecondTurn() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		final MoveResult mr = game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(1, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}

	@Test(expected=HantoException.class)  // 16
	public void blueCantMoveOntoTakenSpot() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(0, 1));
	}
	
	@Test  // 17
	public void movedPieceLeavesSpotOpen() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, 0));		
		final MoveResult mr = game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(0, 0));
		assertEquals(OK, mr);
	}
	
	@Test(expected=HantoException.class)  // 18
	public void redCantMoveOntoSameSpot() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(0, 1));
	}
	
	@Test(expected=HantoException.class)  // 19
	public void blueCantMoveNonAdjacent() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, 1));
	}
	
	@Test  // 20
	public void gameDrawsAfterTwentyTurns() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));

		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0));
		game.makeMove(SPARROW, null, makeCoordinate(3, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-3, 0));
		game.makeMove(SPARROW, null, makeCoordinate(4, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-4, 0));
		game.makeMove(SPARROW, null, makeCoordinate(5, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-5, 0));
		game.makeMove(SPARROW, null, makeCoordinate(6, 0));

		game.makeMove(SPARROW, makeCoordinate(-5, 0), makeCoordinate(-5, 1));
		game.makeMove(SPARROW, makeCoordinate(6, 0), makeCoordinate(6, -1));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 1), makeCoordinate(-5, 0));
		game.makeMove(SPARROW, makeCoordinate(6, -1), makeCoordinate(6, 0));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 0), makeCoordinate(-5, 1));
		game.makeMove(SPARROW, makeCoordinate(6, 0), makeCoordinate(6, -1));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 1), makeCoordinate(-5, 0));
		game.makeMove(SPARROW, makeCoordinate(6, -1), makeCoordinate(6, 0));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 0), makeCoordinate(-5, 1));
		game.makeMove(SPARROW, makeCoordinate(6, 0), makeCoordinate(6, -1));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 1), makeCoordinate(-5, 0));
		game.makeMove(SPARROW, makeCoordinate(6, -1), makeCoordinate(6, 0));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 0), makeCoordinate(-5, 1));
		game.makeMove(SPARROW, makeCoordinate(6, 0), makeCoordinate(6, -1));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 1), makeCoordinate(-5, 0));
		game.makeMove(SPARROW, makeCoordinate(6, -1), makeCoordinate(6, 0));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 0), makeCoordinate(-5, 1));
		game.makeMove(SPARROW, makeCoordinate(6, 0), makeCoordinate(6, -1));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 1), makeCoordinate(-5, 0));
		game.makeMove(SPARROW, makeCoordinate(6, -1), makeCoordinate(6, 0));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 0), makeCoordinate(-5, 1));
		game.makeMove(SPARROW, makeCoordinate(6, 0), makeCoordinate(6, -1));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 1), makeCoordinate(-5, 0));
		game.makeMove(SPARROW, makeCoordinate(6, -1), makeCoordinate(6, 0));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 0), makeCoordinate(-5, 1));
		game.makeMove(SPARROW, makeCoordinate(6, 0), makeCoordinate(6, -1));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 1), makeCoordinate(-5, 0));
		final MoveResult mr = game.makeMove(SPARROW, makeCoordinate(6, -1), makeCoordinate(6, 0));
		assertEquals(DRAW, mr);
		
	}
	
	@Test(expected=HantoException.class)  // 21
	public void onlyFiveBlueSparrowsAllowed() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0));
		game.makeMove(SPARROW, null, makeCoordinate(3, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-3, 0));
		game.makeMove(SPARROW, null, makeCoordinate(4, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-4, 0));
		game.makeMove(SPARROW, null, makeCoordinate(5, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-5, 0));
		game.makeMove(SPARROW, null, makeCoordinate(6, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-6, 0));
	}
	
	@Test(expected=HantoException.class)  // 22
	public void onlyFiveRedSparrowsAllowed() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0));
		game.makeMove(SPARROW, null, makeCoordinate(3, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-3, 0));
		game.makeMove(SPARROW, null, makeCoordinate(4, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-4, 0));
		game.makeMove(SPARROW, null, makeCoordinate(5, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(-5, 0));
		game.makeMove(SPARROW, null, makeCoordinate(6, 0));
		
		game.makeMove(SPARROW, makeCoordinate(-5, 0), makeCoordinate(-5, 1));
		game.makeMove(SPARROW, null, makeCoordinate(7, 0));
	}
	
	@Test(expected=HantoException.class)  // 23
	public void movedPieceCantSlideThrough() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		
		game.makeMove(SPARROW, null, makeCoordinate(2, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));
		
		game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(1, 0));
	}
	
	@Test(expected=HantoException.class)  // 24
	public void bluePieceCantMoveUntilButterflyPlaced() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(1, 0));
	}
	
	@Test(expected=HantoException.class)  // 25
	public void redPieceCantMoveUntilButterflyPlaced() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, makeCoordinate(1, 0), makeCoordinate(0, 1));
	}
	
	@Test(expected=HantoException.class)  // 26
	public void movedPieceCantBreakContiguousness() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(-1, 1));
	}
	
	@Test(expected=HantoException.class)  // 27
	public void tryToMovePieceThatDoesntExist() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, makeCoordinate(1, 0), makeCoordinate(1, -1));
	}
	
	@Test(expected=HantoException.class)  // 28
	public void tryToMovePieceThatDoesntMatchType() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(1, 0));
	}
	
	@Test(expected=HantoException.class)  // 29
	public void tryToMovePieceThatDoesntMatchColor() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(1, 0));
	}
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new HantoCoordinateImpl(x, y);
	}
}

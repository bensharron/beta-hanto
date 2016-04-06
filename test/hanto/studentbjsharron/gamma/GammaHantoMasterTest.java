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
	//private HantoPlayerColor firstColor, secondColor;
	//private MoveResult firstPlayerWins, secondPlayerWins;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		// setupColors(BLUE);
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, BLUE);
	}
	
	/*	
	private void setupColors(HantoPlayerColor startColor)
	{
		if (startColor == BLUE) {
			firstColor = BLUE;
			secondColor = RED;
			firstPlayerWins = BLUE_WINS;
			secondPlayerWins = RED_WINS;
		} else if (startColor == RED) {
			firstColor = RED;
			secondColor = BLUE;
			firstPlayerWins = RED_WINS;
			secondPlayerWins = BLUE_WINS;
		}		
	}
	*/

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
	
	@Test(expected = HantoException.class)	// 3
	public void blueTriesToPlaceCrab() throws HantoException
	{
		game.makeMove(CRAB, null, makeCoordinate(0, 0));
	}
	
	@Test(expected = HantoException.class)	// 4
	public void redTriesToPlaceCrane() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(CRANE, null, makeCoordinate(-1, 0));
	}
	
	@Test(expected = HantoException.class)  // 5
	public void blueTriesToPlaceSecondButterflyOnTurnTwo() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
	}
	
	@Test(expected = HantoException.class)  // 6
	public void redTriesToPlaceSecondButterflyOnTurnTwo() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(2, 0));
	}
	
	@Test  // 7
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
	
	@Test(expected = HantoException.class)  // 8
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
	
	@Test(expected = HantoException.class)  // 9
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
	
	@Test(expected=HantoException.class)  // 10
	public void secondTurnBlueCantPlayNextToRed() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
	}
	
	@Test(expected=HantoException.class)  // 11
	public void secondTurnRedCantPlayNextToBlue() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
	}
	
	@Test(expected=HantoException.class)  // 12
	public void secondTurnBlueCantPlayNonAdjacent() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
	}
	
	@Test(expected=HantoException.class)  // 13
	public void secondTurnRedCantPlayNonAdjacent() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
	}
	
	@Test  // 14
	public void blueButterflyMovesSecondTurn() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		final MoveResult mr = game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, 0));
		assertEquals(OK, mr);
	}

	@Test(expected=HantoException.class)  // 15
	public void blueCantMoveOntoTakenSpot() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(0, 1));
	}
	
	@Test  // 16
	public void movedPieceLeavesSpotOpen() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, 0));		
		final MoveResult mr = game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(0, 0));
		assertEquals(OK, mr);
	}
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new HantoCoordinateImpl(x, y);
	}
}

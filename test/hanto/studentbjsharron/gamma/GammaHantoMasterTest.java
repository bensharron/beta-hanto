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
	private HantoPlayerColor firstColor, secondColor;
	private MoveResult firstPlayerWins, secondPlayerWins;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		setupColors(BLUE);
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, firstColor);
	}
	
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

	@Test(expected=HantoException.class)  // 1
	public void secondTurnBlueCantPlayNextToRed () throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
	}
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new HantoCoordinateImpl(x, y);
	}
}

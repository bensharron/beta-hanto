/**
 * 
 */
package hanto.studentbjsharron.epsilon;

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
		game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, BLUE);
	}
	
	@Test(expected=HantoPrematureResignationException.class)   // 1
	public void blueCannotResignOnFirstMove() throws HantoException
	{
		final MoveResult mr = game.makeMove(null, null, null);
		assertEquals(RED_WINS, mr);
	}

	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new HantoCoordinateImpl(x, y);
	}
}

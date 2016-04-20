/**
 * 
 */
package hanto.studentbjsharron.common;

import java.util.*;

import hanto.common.*;

/**
 * Object to associate player colors to object containing the state for those players
 * @author Ben Sharron
 *
 */
public class HantoPlayers {
	private Map<HantoPlayerColor, HantoPlayerState> players;
	
	public HantoPlayers() {
		players = new HashMap<HantoPlayerColor, HantoPlayerState>();
	}
	
	/**
	 * Adds the given player/state association
	 * @param player Player whose state we're initializing
	 * @param state State for the given player
	 */
	public void addPlayer(HantoPlayerColor player, HantoPlayerState state) {
		players.put(player, state);
	}
	
	/**
	 * Returns the state object associated with the given player
	 * @param player Player whose state object is being retrieved
	 * @return The given player's state variable
	 */
	public HantoPlayerState getPlayerState(HantoPlayerColor player) {
		return players.get(player);
	}
}

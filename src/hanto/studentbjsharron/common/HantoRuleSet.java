/**
 * 
 */
package hanto.studentbjsharron.common;

import java.util.*;

import hanto.common.HantoPieceType;
import hanto.studentbjsharron.common.movement.HantoMovementRule;

/**
 * A set of rules for a given Hanto Game
 * @author Ben Sharron
 *
 */
public class HantoRuleSet {
	private Map<HantoPieceType, HantoMovementRule> rules;
	
	public HantoRuleSet() {
		rules = new HashMap<HantoPieceType, HantoMovementRule>();
	}
	
	/**
	 * Store a new rule in the current rule set
	 * @param piece The piece that the rule is for
	 * @param rule The rule that's being stored
	 */
	public void addRule(HantoPieceType piece, HantoMovementRule rule) {
		rules.put(piece, rule);
	}
	
	/**
	 * Checks if the piece is defined in the rule set
	 * @param piece Piece to check
	 * @return whether the given piece is defined in the rule set
	 */
	public boolean hasRuleFor(HantoPieceType piece) {
		return rules.containsKey(piece);
	}
	
	/**
	 * Retrieve a rule for a certain piece
	 * @param piece The piece that is being checked
	 * @return The rule for the given piece
	 */
	public HantoMovementRule getRule(HantoPieceType piece) {
		return rules.get(piece);
	}
}

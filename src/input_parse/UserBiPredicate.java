package input_parse;

import java.util.function.BiPredicate;
import backend.player.Player;
import backend.util.MutableGameState;

public class UserBiPredicate {

	private BiPredicate<Player, MutableGameState> userBiPredicate = 
	(hello, hi) -> {hi.endTurn(); return hi.getTurnNumber() == 0;};

	public UserBiPredicate() {
	}

	boolean doNothing() {
		System.out.println("BiPredicate");
		return false;
	}

	public BiPredicate<Player, MutableGameState> getBiPredicate() {
		return userBiPredicate;
	}
}
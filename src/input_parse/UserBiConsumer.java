package input_parse;

import java.util.function.BiConsumer;
import backend.player.Player;
import backend.util.MutableGameState;

public class UserBiConsumer {

	private BiConsumer<Player, MutableGameState> userBiConsumer = 
	(hello, hi) -> {};

	public UserBiConsumer() {
	}

	boolean doNothing() {
		System.out.println("BiConsumer");
		return false;
	}

	public BiConsumer<Player, MutableGameState> getBiConsumer() {
		return userBiConsumer;
	}
}
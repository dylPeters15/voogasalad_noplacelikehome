package backend.game_engine;

import backend.player.Player;
import backend.util.GameState;

@FunctionalInterface
public interface ResultQuadPredicate {

	Result determine(Player player, GameState gamestate);
	
	enum Result{
		WIN,
		LOSE,
		TIE,
		NONE
	}
}

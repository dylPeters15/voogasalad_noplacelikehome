package backend.game_engine;

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

package backend.game_engine;

import backend.player.Player;
import backend.util.GameplayState;
import backend.util.GameplayState;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ResultQuadPredicate {

	Result determine(Player player, GameplayState state);

	enum Result {
		//        WIN((player, gameState) -> state.handleWin(player)),
//        LOSE((player, gameState) -> state.handleLoss(player)),
//        TIE((player, gameState) -> state.handleTie()),
		NONE((player, gameState) -> doNothing());

		private BiConsumer<Player, GameplayState> toExecute;

		Result(BiConsumer<Player, GameplayState> executeThis) {
			toExecute = executeThis;
		}

		public void accept(Player player, GameplayState state) {
			toExecute.accept(player, state);
		}

		private static void doNothing() {
		}
	}
}

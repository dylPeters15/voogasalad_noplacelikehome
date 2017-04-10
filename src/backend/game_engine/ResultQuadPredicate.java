package backend.game_engine;

import backend.player.Player;
import backend.util.MutableGameState;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ResultQuadPredicate {

	Result determine(Player player, MutableGameState state);

	enum Result {
		//        WIN((player, gameState) -> state.handleWin(player)),
//        LOSE((player, gameState) -> state.handleLoss(player)),
//        TIE((player, gameState) -> state.handleTie()),
		NONE((player, gameState) -> doNothing());

		private BiConsumer<Player, MutableGameState> toExecute;

		Result(BiConsumer<Player, MutableGameState> executeThis) {
			toExecute = executeThis;
		}

		public void accept(Player player, MutableGameState state) {
			toExecute.accept(player, state);
		}

		private static void doNothing() {
		}
	}
}

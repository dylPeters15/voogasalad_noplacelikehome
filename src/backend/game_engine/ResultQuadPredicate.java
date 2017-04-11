package backend.game_engine;

import backend.player.Player;
import backend.util.AuthoringGameState;

import java.io.Serializable;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface ResultQuadPredicate extends Serializable{

	Result determine(Player player, AuthoringGameState state);

	enum Result {
		//        WIN((player, gameState) -> state.handleWin(player)),
//        LOSE((player, gameState) -> state.handleLoss(player)),
//        TIE((player, gameState) -> state.handleTie()),
		NONE((player, gameState) -> doNothing());

		private BiConsumer<Player, AuthoringGameState> toExecute;

		Result(BiConsumer<Player, AuthoringGameState> executeThis) {
			toExecute = executeThis;
		}

		public void accept(Player player, AuthoringGameState state) {
			toExecute.accept(player, state);
		}

		private static void doNothing() {
		}
	}
}

package backend.game_engine;

import java.util.function.BiConsumer;

import backend.player.Player;
import backend.util.MutableGameState;

@FunctionalInterface
public interface ResultQuadPredicate {

	Result determine(Player player, MutableGameState state);
	
    enum Result {
//        WIN((player, gameState) -> state.handleWin(player)),
//        LOSE((player, gameState) -> state.handleLoss(player)),
//        TIE((player, gameState) -> state.handleTie()),
          NONE((player, gameState) -> doNothing());
        
        private BiConsumer<Player, MutableGameState> toExecute;
    	
    	Result(BiConsumer<Player, MutableGameState> executeThis){
    		toExecute = executeThis;
    	}
    	
    	public void accept(Player player, MutableGameState state){
    		toExecute.accept(player, state);;
    	}
    	
    	private static final void doNothing(){}
    }
}

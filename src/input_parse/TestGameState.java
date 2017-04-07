package input_parse;

import java.util.Collection;
import java.util.function.BiPredicate;

import backend.game_engine.ResultQuadPredicate;
import backend.player.Player;
import backend.util.GameState;
import backend.util.ImmutableGameState;

public class TestGameState extends GameState {

	@Override
	public void messagePlayer(Player from, Player to, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTurn() {
		System.out.println("It works!");
	}

	@Override
	public Collection<ResultQuadPredicate> getObjectives() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<BiPredicate<Player, ImmutableGameState>> getTurnRequirements() {
		// TODO Auto-generated method stub
		return null;
	}

}

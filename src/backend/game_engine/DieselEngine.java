package backend.game_engine;

import backend.game_engine.ResultQuadPredicate.Result;
import backend.player.Player;
import backend.util.MutableGameState;
import util.net.ObservableServer;

import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author Zapata
 */

//TODO: Implement some way to checkTurnState() to determine if it is the beginning or end of a turn. Implement restart(), save()
// and load() (Tavo's job). Also implement a messagePlayer(Player from, Player to, String message).

public class DieselEngine implements GameEngine {

	private ObservableServer<MutableGameState> server;
	private Consumer<MutableGameState> stateUpdateListener = this::checkGame;

	public DieselEngine(ObservableServer<MutableGameState> s) {
		server = s;
		server.addListener(stateUpdateListener);
		Executors.newSingleThreadExecutor().submit(server);
	}

	private void checkGame(MutableGameState state) {
		checkTurnRules(state);
		checkTurnEvents(state);
		checkObjectives(state);
	}

	@Override
	public void messagePlayer(Player from, Player to, String message) {
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void load() {
		// TODO Auto-generated method stub

	}

	private void checkTurnRules(MutableGameState state) {
		if (!state.getTurnRequirements().parallelStream()
				.allMatch(e -> e.test(state.getCurrentPlayer(), state)) && state.canEndTurn()) state.endTurn();
	}

	private void checkTurnEvents(MutableGameState state) {
//		state.getTurnEvents().entrySet().stream()
//										.filter(e -> state.getTurnState())
//										.forEach(e -> e.getValue().stream().forEach(t -> t.accept(state.getCurrentPlayer(), state)));
	}

	private void checkObjectives(MutableGameState state) {
		state.getObjectives().parallelStream().forEach(e -> {
			Result result = e.determine(state.getCurrentPlayer(), state);
			result.accept(state.getCurrentPlayer(), state);
		});
	}

}
package backend.game_engine;

import backend.game_engine.ResultQuadPredicate.Result;
import backend.player.Player;
import backend.util.AuthorGameState;
import backend.util.AuthoringGameState;
import backend.util.io.XMLSerializer;
import util.io.Serializer;
import util.io.Unserializer;
import util.net.ObservableServer;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author Zapata
 */

//TODO: Implement some way to checkTurnState() to determine if it is the beginning or end of a turn. Implement restart(), save() and load() (Tavo's job). Also implement a messagePlayer(Player from, Player to, String message).
public class DieselEngine implements GameEngine {

	private ObservableServer<AuthoringGameState> server;
	private Consumer<AuthoringGameState> stateUpdateListener = this::checkGame;

	public DieselEngine(ObservableServer<AuthoringGameState> s) {
		server = s;
		server.addListener(stateUpdateListener);
		Executors.newSingleThreadExecutor().submit(server);
	}

	private void checkGame(AuthoringGameState state) {
		checkTurnRules(state);
		checkTurnEvents(state);
		checkObjectives(state);
	}

	@Override
	public void save(AuthorGameState gameState) {
		try{
			new XMLSerializer<AuthorGameState>().doSerialize(gameState);
		}catch(Exception e){
			//Something here.
		}
	}

	@Override
	public AuthorGameState load(File gameStateFile) {
		AuthorGameState newGameState;
		try{
			newGameState = (AuthorGameState) new XMLSerializer<AuthorGameState>().unserialize(new String(Files.readAllBytes(Paths.get(gameStateFile.getPath()))));
		}catch(Exception e){
			//Something here.
			newGameState = null;
		}
		return newGameState;
	}

	private void checkTurnRules(AuthoringGameState state) {
		if (!state.getTurnRequirements().parallelStream()
				.allMatch(e -> e.test(state.getCurrentPlayer(), state)) && state.canEndTurn()) state.endTurn();
	}

	private void checkTurnEvents(AuthoringGameState state) {
		state.getTurnEvents().entrySet().stream()
										.forEach(e -> e.getValue().stream().forEach(t -> t.accept(state.getCurrentPlayer(), state)));
	}

	private void checkObjectives(AuthoringGameState state) {
		state.getObjectives().parallelStream().forEach(e -> {
			Result result = e.determine(state.getCurrentPlayer(), state);
			result.accept(state.getCurrentPlayer(), state);
		});
	}

}
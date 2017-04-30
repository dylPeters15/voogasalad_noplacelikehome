package backend.game_engine;

import backend.game_engine.ResultQuadPredicate.Result;
import backend.player.ImmutablePlayer;
import backend.util.AuthoringGameState;
import backend.util.GameplayState;
import backend.util.io.XMLSerializer;
import util.net.ObservableServer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Alexander Zapata
 */

// TODO: Implement some way to checkTurnState() to determine if it is the
// beginning or end of a turn. Implement restart(), save() and load() (Tavo's
// job). Also implement a messagePlayer(Player from, Player to, String message).
public class DieselEngine implements GameEngine {

	private ObservableServer<GameplayState> myServer;
	private Consumer<GameplayState> stateUpdateListener = this::checkGame;

	/**
	 * This constructor passes in the ObservableServer<GameplayState> that the
	 * GameEngine will communicate with and add listeners to.
	 *
	 * @param server
	 */
	public DieselEngine(ObservableServer<GameplayState> server) {
		myServer = server;
		server.addListener(stateUpdateListener);
		Executors.newSingleThreadExecutor().submit(server);
	}

	/**
	 * This method compiles all of the necessary checking methods to run at the
	 * change of a GameState. This method is what gets passed to the Server to
	 * execute with lambdas.
	 *
	 * @param state
	 */
	private void checkGame(GameplayState state) {
		checkTurnRules(state);
		checkTurnEvents(state);
		checkObjectives(state);
	}

	@Override
	public void save(GameplayState gameState) {
		try {
			new XMLSerializer<>().doSerialize(gameState);
		} catch (Exception e) {
			// Something here.
		}
	}

	@Override
	public AuthoringGameState load(File gameStateFile) {
		AuthoringGameState newGameState;
		try {
			newGameState = (AuthoringGameState) new XMLSerializer<>()
					.unserialize(new String(Files.readAllBytes(Paths.get(gameStateFile.getPath()))));
		} catch (Exception e) {
			// Something here.
			newGameState = null;
		}
		return newGameState;
	}

	/**
	 * This method will take the TurnRequirements within GameState (which are
	 * wrapped by Requirement) and in the case they are all satisfied call
	 * state.endTurn().
	 *
	 * @param state
	 */
	private void checkTurnRules(GameplayState state) {
		if (!state.getTurnRequirements().parallelStream().allMatch(e -> e.test(state.getActiveTeam(), state))
				&& state.turnRequirementsSatisfied())
			state.endTurn();
	}

	/**
	 * This method will go through all of the TurnEvents (wrapped by
	 * Actionables) and execute their BIPredicates when added as a listener in
	 * the Server.
	 *
	 * @param state
	 */
	private void checkTurnEvents(GameplayState state) {
//		state.getTurnActions().forEach((key, value) -> value.forEach(t -> t.accept(state.getActiveTeam(), state)));
	}

	/**
	 * This method goes through all of the objectives of the GameState so that
	 * every-time a state changes the Server can check for a winner.
	 *
	 * @param state
	 */
	private void checkObjectives(GameplayState state) {
		state.getObjectives().parallelStream().forEach(e -> {
//			Result result = e.getResultQuad().determine(state.getActiveTeam(), state);
//			result.accept(state.getActiveTeam(), this);
		});
	}

	@Override
	public Object handleWin(ImmutablePlayer player) {
		// TODO Auto-generated method stub

		myServer.sendAndApply((GameplayState state) -> {
			state.getOrderedPlayerNames().stream().map(playerName -> state.getPlayerByName(playerName))
					.forEach(aPlayer -> aPlayer
							.setResult(aPlayer.getTeam().equals(aPlayer.getTeam()) ? Result.WIN : Result.LOSE));
			return state;
		});

		return null;
	}

	@Override
	public Object handleLoss(ImmutablePlayer player) {
		// TODO Auto-generated method stub

		myServer.sendAndApply((GameplayState state) -> {
			state.getPlayerByName(player.getName()).setResult(Result.LOSE);
			List<String> remainingPlayers = state.getOrderedPlayerNames().stream()
					.filter(playerName -> state.getPlayerByName(playerName).getResult().equals(Result.NONE))
					.collect(Collectors.toList());
			if (remainingPlayers.size() == 1) {
				state.getPlayerByName(remainingPlayers.get(0)).setResult(Result.WIN);
			}
			return state;
		});

		return null;
	}

	@Override
	public Object handleTie() {
		// TODO Auto-generated method stub

		myServer.sendAndApply((GameplayState state) -> {
			state.getOrderedPlayerNames().stream().forEach(name -> state.getPlayerByName(name).setResult(Result.TIE));
			return state;
		});

		return null;
	}

}
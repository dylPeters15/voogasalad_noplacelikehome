package backend.game_engine;

import backend.game_engine.ResultQuadPredicate.Result;
import backend.player.ImmutablePlayer;
import backend.util.AuthoringGameState;
import backend.util.GameplayState;
import backend.util.ReadonlyGameplayState;
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

	private ObservableServer<ReadonlyGameplayState> myServer;
	private GameplayState currentState;

	public DieselEngine(ObservableServer<ReadonlyGameplayState> gameServer) {
		myServer = gameServer;
		currentState = null;
	}

	/**
	 * This method compiles all of the necessary checking methods to run at the
	 * change of a GameState. This method is what gets passed to the Server to
	 * execute with lambdas.
	 *
	 * @param state
	 */
	public void checkGame(GameplayState state) {
		System.out.println("check");
		currentState = state;
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
		if (!state.getTurnRequirements().parallelStream().allMatch(e -> e.test(state.getActivePlayer(), state))
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
		state.getTurnActions().forEach((value) -> value.accept(state.getActivePlayer(), state));
	}

	/**
	 * This method goes through all of the objectives of the GameState so that
	 * every-time a state changes the Server can check for a winner.
	 *
	 * @param state
	 */
	private void checkObjectives(GameplayState state) {
		System.out.println(state.getObjectives().toString());
		state.getObjectives().parallelStream().forEach(e -> {
			Result result = e.getResultQuad().determine(state.getActivePlayer(), state);
			System.out.println(result.toString());
			result.accept(state.getActivePlayer(), this);
		});
	}

	@Override
	public Object handleWin(ImmutablePlayer player) {
		System.out.println("winner");
		currentState.getOrderedPlayerNames().stream().map(playerName -> currentState.getPlayerByName(playerName)).forEach(
				aPlayer -> aPlayer.setResult(aPlayer.getTeam().equals(aPlayer.getTeam()) ? Result.WIN : Result.LOSE));
		return null;
	}

	@Override
	public Object handleLoss(ImmutablePlayer player) {
		currentState.getPlayerByName(player.getName()).setResult(Result.LOSE);
		List<String> remainingPlayers = currentState.getOrderedPlayerNames().stream()
				.filter(playerName -> currentState.getPlayerByName(playerName).getResult().equals(Result.NONE))
				.collect(Collectors.toList());
		if (remainingPlayers.size() == 1) {
			currentState.getPlayerByName(remainingPlayers.get(0)).setResult(Result.WIN);
		}
		return null;
	}

	@Override
	public Object handleTie() {
			currentState.getOrderedPlayerNames().stream().forEach(name -> currentState.getPlayerByName(name).setResult(Result.TIE));
		return null;
	}

}
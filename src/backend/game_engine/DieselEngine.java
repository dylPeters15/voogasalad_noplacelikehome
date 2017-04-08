package backend.game_engine;

import backend.game_engine.ResultQuadPredicate.Result;
import backend.player.Player;
import backend.util.MutableGameState;
import util.io.Serializer;
import util.io.Unserializer;
import util.net.ObservableServer;

import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author Zapata
 */

//TODO: Implement some way to checkTurnState() to determine if it is the beginning or end of a turn. Implement restart(), save()
// and load() (Tavo's job). Also implement a messagePlayer(Player from, Player to, String message).

public class DieselEngine implements GameEngine{

	private Serializer serializer = this::save;
	private Unserializer unserializer = this::load;
	private ObservableServer<MutableGameState> server;
	private Consumer<MutableGameState> stateUpdateListener = this::checkGame;
	
	public DieselEngine(ObservableServer<MutableGameState> s){
		server = s;
		server.addListener(stateUpdateListener);
		Executors.newSingleThreadExecutor().submit(server);
	}
	
	private void checkGame(MutableGameState state){
		checkTurnRules(state);
		checkTurnEvents(state);
		checkObjectives(state);
		return;
	}
	
	private final void checkTurnRules(MutableGameState state){
		if(!state.getTurnRequirements().parallelStream()
									   .allMatch(e -> e.test(state.getCurrentPlayer(), state)) && state.canEndTurn()) state.endTurn(state.getCurrentPlayer());
	}
	
	private final void checkTurnEvents(MutableGameState state){
//		state.getTurnEvents().entrySet().stream()
//										.filter(e -> state.getTurnState())
//										.forEach(e -> e.getValue().stream().forEach(t -> t.accept(state.getCurrentPlayer(), state)));
	}
	
	private final void checkObjectives(MutableGameState state){
		state.getObjectives().parallelStream().forEach(e -> {Result result = e.determine(state.getCurrentPlayer(), state); result.accept(state.getCurrentPlayer(), state);});
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Serializable save(Object gameEngine) {
		return null;
		// TODO Auto-generated method stub
	}

	@Override
	public Object load(Object gameEngineXML) {
		return null;
		// TODO Auto-generated method stub
	}
	
	public void messagePlayer(Player from, Player to, String message) {}
	
}
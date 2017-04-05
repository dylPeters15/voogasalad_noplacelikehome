package backend.game_engine;

import backend.game_engine.ResultQuadPredicate.Result;
import backend.player.Player;
import backend.util.MutableGameState;
import util.net.ObservableServer;

import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DieselEngine implements GameEngine{

	private ObservableServer<MutableGameState> server;
	private Consumer<MutableGameState> stateUpdateListener = this::checkGame;
	
	public DieselEngine(ObservableServer<MutableGameState> s){
		server = s;
		server.addListener(stateUpdateListener);
		server.start();
	}
	
	private void checkGame(MutableGameState state){
		checkTurnRules(state);
		checkTurnEvents(state);
		checkObjectives(state);
		return;
	}
	
	private final void checkTurnRules(MutableGameState state){
		if(!state.getTurnRequirements().stream()
									   .map(e -> e.test(state.getCurrentPlayer(), state))
									   .collect(Collectors.toList())
								       .contains(false) && state.canEndTurn()) state.endTurn();
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
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}
	
	public void messagePlayer(Player from, Player to, String message) {}

    @Override
    public String toXML() {
        // TODO Auto-generated method stub
        return null;
    }
   
}
package backend.game_engine;

import java.util.function.Consumer;

import backend.player.Player;
import backend.util.GameState;
import util.net.ObservableServer;

public class DieselEngine implements GameEngine{

	private ObservableServer<GameState> server;
	private Consumer<GameState> stateUpdateListener = this::checkRules;
	
	public DieselEngine(ObservableServer<GameState> s){
		server = s;
		server.addListener(stateUpdateListener);
		server.start();
	}
	
	private void checkRules(GameState state){
		
		return;
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
	
	@Override
	public void messagePlayer(Player from, Player to, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

}
package backend.util;

import java.util.List;
import java.util.function.BiConsumer;

import backend.grid.MutableGrid;
import backend.io.XMLSerializable;
import backend.player.Player;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class GameState implements XMLSerializable, MutableGameState {

	@Override
	public List<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MutableGrid getGrid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTurnNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addEventHandler(BiConsumer<Player, ImmutableGameState> eventListener, Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

}

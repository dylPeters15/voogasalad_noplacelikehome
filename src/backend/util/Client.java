package backend.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import backend.cell.ModifiableCell;
import backend.cell.ModifiableTerrain;
import backend.game_engine.ResultQuadPredicate;
import backend.game_rules.GameRule;
import backend.grid.BoundsHandler;
import backend.grid.ModifiableGameBoard;
import backend.player.Player;
import backend.player.Team;
import backend.unit.ModifiableUnit;
import backend.unit.properties.ActiveAbility;

/**
 * @author Created by ncp14
 */
//TODO: Implement getTurnNumber(), messagePlayer(Player from, Player to, String message);
public class Client {
	

	public Client() {
	}
	
	public void setGameState(AuthoringGameState mGameState)
	{
		//Sends gameState to client
	}
	

}

package controller;

import backend.grid.GameBoard;
import backend.player.ImmutablePlayer;
import backend.util.AuthoringGameState;
import backend.util.GameplayState;
import frontend.View;
import util.net.Modifier;

/**
 * @author Created by ncp14 on 4/3/2017.
 */
public interface Controller {

	GameBoard getGrid();

	AuthoringGameState getAuthoringGameState();

	GameplayState getGameplayState();

	ImmutablePlayer getPlayer(String name);

	void setView(View view);

	Object getUnitTemplates();

	void setGameState(AuthoringGameState newGameState);

	void sendModifier(Modifier<GameplayState> modifier);
}

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import backend.grid.GameBoard;
import backend.util.GameState;
import frontend.View;

/**
 * @author Created by ncp14
 * This class is the communication controller which communicates between the frontend and backend.
 * The primary purpose of my controller is to hide implementation of backend structure, specifically how
 * our networking works and how the GameState is structured.
 * 
 */
public class CommunicationController extends Observable implements Controller  {
	//front to back buffer for gamestate
	private Buffer<GameState> gameStateHistory;
	private GameState mGameState;
	private View mView;
	//private Client client;
	
	@Override
	public GameBoard getGrid() {
		return mGameState.getGrid();
	}

	@Override
	public Object getUnitTemplates() {
		return mGameState.getUnitTemplates();
	}
	
	public void setView(View view)
	{
		this.mView = view;
	}
	
	
	public void setGameState(GameState gameState)
	{
		this.mGameState = gameState;
	}


	
	
	
	
}
package controller;


import backend.util.GameState;
import frontend.View;

/**
 * @author Created by ncp14
 * This class is the communication controller which communicates the information
 * in both directions.
 * 
 * myFrontBuffer passes from
 */
public class CommunicationController {
	//front to back buffer for gamestate
	private Buffer<GameState> frontToBackBuffer; 
	
	//GameRules buffer - should be groovy code
	private Buffer<String> gameRulesCode; 
	
	//end front to back buffer
	private Buffer<GameState> backToFrontBuffer;
	
	private ModelGenerator mModelGenerator; //Touchdown class in backend
	private View mView; //Touchdown class in frontend
	
	public CommunicationController() {
		this.frontToBackBuffer = new MyBuffer<GameState>();
		this.backToFrontBuffer = new MyBuffer<GameState>();
		this.gameRulesCode = new MyBuffer<String>();
	}
	public CommunicationController(Buffer<GameState> frontToBackBuffer, Buffer<GameState> backToFrontBuffer, Buffer<String> gameRulesCode) {
		this.frontToBackBuffer = frontToBackBuffer;
		this.backToFrontBuffer = backToFrontBuffer;
		this.gameRulesCode = gameRulesCode;
	}

	/*
	 * This method updates the front end by the information read from the buffer
	 *
	 */
	public void updateFrontend() {
		while (!this.backToFrontBuffer.isBufferEmpty()) {
			mView.setGameState(this.backToFrontBuffer.getBufferHead());
		}
	}

	/**
	 * This method reads from buffer the incoming update in the buffer and
	 * informs the backend command by command
	 */
	public void updateBackend() {
		while (!this.frontToBackBuffer.isBufferEmpty()) {
			ModelGenerator mModelGenerator = new ModelGenerator(this, this.frontToBackBuffer.getBufferHead());
			mModelGenerator.generateGameState();
		}
	}
	/**
	 * This method adds the command to buffer queue without truly informing the
	 * backend.
	 * 
	 * @param newGameState
	 */
	public void passToBackend(GameState newGameState) {
		this.frontToBackBuffer.addToBuffer(newGameState);
		this.updateBackend();
	}
	/**
	 * This method reads from buffer the incoming update of GameState inside
	 * the buffer and updates the GameState according.
	 */
	public void passToFrontend(GameState newGameState) {
		this.frontToBackBuffer.addToBuffer(newGameState);
		this.updateFrontend();
	}
	/**
	 * This method gives the backend the freedom to send alerts.
	 * Not sure if this is necessary.
	 * 
	 * @param message
	 */
	public void alert(String message) {
		this.mView.sendAlert(message);
	}
}
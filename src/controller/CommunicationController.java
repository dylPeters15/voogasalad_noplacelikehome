package controller;

import backend.util.AuthorGameState;

/**
 * @author Created by ncp14
 * This class is the communication controller which communicates the information
 * in both directions.
 * 
 * myFrontBuffer passes from
 */
public class CommunicationController {
	private Buffer<AuthorGameState> frontToBackBuffer; 
	private Buffer<AuthorGameState> backToFrontBuffer;
	private Buffer<String> gameCode;
	//private InternalController myGUIController;
	private Translate myBackend;
	public CommunicationController() {
		this.myFrontBuffer = new MyBuffer<Turtle>();
		this.myBackBuffer = new MyBuffer<String>();
	}
	public CommunicationController(Buffer<Turtle> myFrontBuffer, Buffer<String> myBackBuffer) {
		this.myFrontBuffer = myFrontBuffer;
		this.myBackBuffer = myBackBuffer;
	}
	public CommunicationController(InternalController myGUI) {
		this();
		this.myGUIController = myGUI;
		this.myBackend = new Translate(this, new Turtle(new Position(10, 10, 0)));
	}
	/*
	 * This method updates the front end by the information read from the buffer
	 * and sets the new turtle location through GraphicController given to it.
	 */
	public void updateFrontend() {
		while (!this.myFrontBuffer.isBufferEmpty()) {
			Turtle curCommand = this.myFrontBuffer.getBufferHead();
			double x = myGUIController.getCurPosX();
			double y = myGUIController.getCurPosY();
			// double rot = myGUIController.getRotation();
			Position newPosition = curCommand.getPosition();
			// .getNewPosition(new Position(x, y, 0));
			// double dis = newPosition.getDisplacement();
			double angle = newPosition.getOrientation();
			// angle=angle+rot;
			double xx = 200 + newPosition.getX() - 10;
			double yy = 200 + newPosition.getY() - 10;
			// System.out.println(x+" "+y+" "+dx+" "+dy);
			// Line newLine = new Line(x, y, xx, yy);
			// newLine.setFill(null);
			// newLine.setStroke(Color.BLACK);
			// newLine.setStrokeWidth(10);
			myGUIController.setCurPosX(xx);
			myGUIController.setCurPosY(yy);
			myGUIController.setRotate(angle);
			myGUIController.updateTurtleLocation(xx, yy, curCommand.getPen());
		}
	}
	/**
	 * This method updates the frontend variable display by the information from
	 * the backend database through GraphicController
	 * 
	 * @param myVariable
	 */
	public void updateVariable(List<Variable> myVariable) {
		for (Variable cur : myVariable) {
			this.myGUIController.updateVariable(cur.getName(), Integer.toString(cur.getValue()));
		}
	}
	/**
	 * This method updates a single variable by the database information
	 * 
	 * @param cur
	 */
	public void updateVariable(Variable cur) {
		// System.out.println("VariableUpdates__________________________");
		this.myGUIController.updateVariable(cur.getName(), Integer.toString(cur.getValue()));
	}
	/**
	 * This method reads from buffer the incoming update in the buffer and
	 * informs the backend command by command
	 */
	public void updateBackend() {
		while (!this.myBackBuffer.isBufferEmpty()) {
			String curUserInput = this.myBackBuffer.getBufferHead();
			myBackend.setCommand(curUserInput);
		}
	}
	/**
	 * This method adds the command to buffer queue without truly informing the
	 * backend.
	 * 
	 * @param command
	 */
	public void passToBackend(String command) {
		this.myBackBuffer.addToBuffer(command);
		this.updateBackend();
	}
	/**
	 * This method reads from buffer the incoming update of turtle state inside
	 * the buffer and updates the front end state by state.
	 */
	public void passToFrontend(Turtle command) {
		this.myFrontBuffer.addToBuffer(command);
		this.updateFrontend();
	}
	/**
	 * This method gives the backend the freedom to send alerts
	 * 
	 * @param message
	 */
	public void alert(String message) {
		this.myGUIController.sendAlert(message);
	}
}
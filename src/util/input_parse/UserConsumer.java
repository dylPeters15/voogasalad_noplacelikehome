package util.input_parse;

import java.util.function.Consumer;

import backend.util.MutableGameState;

public class UserConsumer {

	private Consumer<MutableGameState> userConsumer = 
(hello) -> {hello.endTurn();};
			
	public UserConsumer() {
	}

	boolean doNothing() {
		System.out.println("Consumer");
		return false;
	}

	public Consumer<MutableGameState> getConsumer() {
		return userConsumer;
	}
}

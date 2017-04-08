package input_parse;

import backend.util.GameState;
import input_parse.InputDecoder.Type;

public class ParseMain {
	
	public static void main(String[] args) {
		GameState gameState = new GameState();
		Parser parser = new Parser(gameState);
		parser.parse(Type.PREDICATE, "(hello) -> {hello.endTurn(); return true;};");
		UserConsumer consumer = new UserConsumer();
		consumer.getConsumer().accept(gameState);
	}

}

package input_parse;

import java.util.function.Predicate;

import backend.util.MutableGameState;

public class UserPredicate {

	private Predicate<MutableGameState> userPredicate = 
	(hello) -> {hello.endTurn(); return hello.getTurnNumber() == 0;};

	public UserPredicate() {
	}

	boolean doNothing() {
		System.out.println("Predicate");
		return false;
	}

	public Predicate<MutableGameState> getPredicate() {
		return userPredicate;
	}
}
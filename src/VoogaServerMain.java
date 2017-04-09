import backend.util.GameState;
import backend.util.ImmutableGameState;
import backend.util.io.XMLSerializer;
import util.net.ObservableServer;

import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * @author Created by th174 on 4/4/2017.
 */
public class VoogaServerMain {
	public static final int PORT = 10023;
	public static final int TIMEOUT = 120;

	public static void main(String[] args) throws Exception {
		//TODO
		XMLSerializer<ImmutableGameState> serializer = new XMLSerializer<>();
//		JSONSerializer<ImmutableGameState> serializer = new JSONSerializer<>(GameState.class);
		ObservableServer<ImmutableGameState> voogaServer = new ObservableServer<>(new GameState(), PORT, serializer, serializer, Duration.ofSeconds(TIMEOUT));
		Executors.newSingleThreadExecutor().submit(voogaServer);
		System.out.println("Server started successfully...");
	}
}

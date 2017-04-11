import backend.player.Player;
import backend.util.GameplayState;
import backend.util.io.XMLSerializer;
import javafx.application.Application;
import javafx.stage.Stage;
import util.net.ObservableClient;

import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

//import frontend.startup.StartupScreen;

/**
 * @author Created by th174 on 4/4/2017.
 */
public class VoogaClientMain extends Application {
	public static final int PORT = 10023;
	public static final String HOST = ObservableClient.LOCALHOST;
	public static final int TIMEOUT = 20;
	public static final String CHATBOX = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n------------TEST GAME STATE CHAT LOG------------\n\n%s\n\n >>  ";

	public static void main(String[] args) throws IOException, InterruptedException {
		String name = System.getProperty("user.name") + Math.random();
		XMLSerializer<GameplayState> serializer = new XMLSerializer<>();
		ObservableClient<GameplayState> client = new ObservableClient<>(HOST, PORT, serializer, serializer, Duration.ofSeconds(TIMEOUT));
		client.addListener(state -> {
			try {
				System.out.printf(CHATBOX,
						state.getPlayerByName(name).getChatLog().parallelStream()
								.map(Object::toString).collect(Collectors.joining("\n")));
			} catch (NullPointerException e) {
			}
		});
		Executors.newSingleThreadExecutor().submit(client);
		client.addToOutbox(state -> {
			state.addPlayer(new Player(name, "It's me!", ""));
			return state;
		});
		System.out.println("Client started successfully...");
		Scanner stdin = new Scanner(System.in);
		while (client.isActive()) {
			String input = stdin.nextLine();
			client.addToOutbox(state -> state.messageAll(input, state.getPlayerByName(name)));
		}
	}

	@Override
	public void start(Stage primaryStage) {
//		primaryStage.setTitle(ResourceBundle.getBundle("resources/Selections", Locale.getDefault()).getString("Title"));
//		StartupScreen userInterface = new StartupScreen();
//		primaryStage.setScene(userInterface.getPrimaryScene());
//		primaryStage.setResizable(true);
//		primaryStage.show();
	}
}

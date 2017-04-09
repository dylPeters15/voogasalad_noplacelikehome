import backend.player.Player;
import backend.player.Team;
import backend.unit.properties.Faction;
import backend.util.GameState;
import backend.util.ImmutableGameState;
import backend.util.io.JSONSerializer;
import javafx.stage.Stage;
import util.net.ObservableClient;

import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 4/4/2017.
 */
public class VoogaClientMain {
	public static final int PORT = 10023;
	public static final String HOST = ObservableClient.LOCALHOST;
	public static final int TIMEOUT = 20;
	public static final String CHATBOX = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n------------TEST GAME STATE CHAT LOG------------\n\n%s\n\n >>  ";

	public static void main(String[] args) throws IOException, InterruptedException {
		String name = System.getProperty("user.name");
		String teamName = name + "'s team";
		JSONSerializer<ImmutableGameState> serializer = new JSONSerializer<>(GameState.class);
		ObservableClient<ImmutableGameState> client = new ObservableClient<>(HOST, PORT, serializer, serializer, Duration.ofSeconds(TIMEOUT));
		client.addListener(state -> {
			try {
				System.out.printf(CHATBOX,
						state.getTeamByName(teamName)
								.get(name)
								.getChatLog()
								.parallelStream()
								.map(Object::toString)
								.collect(Collectors.joining("\n")));
			} catch (NullPointerException e) {
			}
		});
		Executors.newSingleThreadExecutor().submit(client);
		Thread.sleep(200);
		client.send(state -> state
				.addTeam(new Team(teamName, "us", "")));
		Thread.sleep(200);
		client.send(state -> {
			state.getTeamByName(teamName).addAll(new Player(name, Faction.UNDEAD, "It's me!", ""));
			return state;
		});
		System.out.println("Client started successfully...");
		Scanner stdin = new Scanner(System.in);
		while (client.isActive()) {
			String input = stdin.nextLine();
			client.send(state -> state.messageAll(input, state.getTeamByName(teamName).get(name)));
		}
	}

	//    @Override
	public void start(Stage primaryStage) {
//		primaryStage.setTitle(ResourceBundle.getBundle("resources/Selections", Locale.getDefault()).getString("Title"));
//		UI userInterface = new UI();
//		primaryStage.setScene(userInterface.getPrimaryScene());
//		primaryStage.setResizable(true);
//		primaryStage.show();
	}
}

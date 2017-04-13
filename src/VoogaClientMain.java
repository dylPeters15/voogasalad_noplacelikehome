import backend.cell.Terrain;
import backend.grid.GameBoard;
import backend.grid.ModifiableGameBoard;
import backend.player.ImmutablePlayer;
import backend.player.Player;
import backend.unit.Unit;
import backend.util.AuthoringGameState;
import backend.util.GameplayState;
import backend.util.ReadonlyGameplayState;
import backend.util.io.XMLSerializer;
import controller.Controller;
import frontend.View;
import frontend.util.ChatLogView;
import frontend.util.Updatable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import util.net.Modifier;
import util.net.ObservableClient;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;
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
	private static ObservableClient<GameplayState> client;
	private static GameplayState gameplayState;

	public static void main(String[] args) throws IOException, InterruptedException {
		String name = System.getProperty("user.name");
		XMLSerializer<GameplayState> serializer = new XMLSerializer<>();
		client = new ObservableClient<>(HOST, PORT, serializer, serializer, Duration.ofSeconds(TIMEOUT));
		client.addListener(state -> {
			try {
				System.out.printf(CHATBOX, state.getPlayerByName(name).getChatLog().parallelStream().map(Object::toString).collect(Collectors.joining("\n")));
				gameplayState = state;
			} catch (NullPointerException e) {
			}
		});
		Executors.newSingleThreadExecutor().submit(client);
		client.addToOutbox(state -> {
			state.addPlayer(new Player(name, "It's me!", ""));
			return state;
		});
		launch(args);
//		Scanner stdin = new Scanner(System.in);
//		while (client.isActive()) {
//			String input = stdin.nextLine();
//			client.addToOutbox(state -> state.messageAll(input, state.getPlayerByName(name)));
//		}
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle(ResourceBundle.getBundle("resources/Selections", Locale.getDefault()).getString("Title"));
		ChatLogView chatLogView = new ChatLogView(System.getProperty("user.name"), new Controller<GameplayState>() {
			@Override
			public GameBoard getGrid() {
				return null;
			}

			@Override
			public ReadonlyGameplayState getReadOnlyGameState() {
				return null;
			}

			@Override
			public AuthoringGameState getAuthoringGameState() {
				return null;
			}

			@Override
			public GameplayState getGameState() {
				return gameplayState;
			}

			@Override
			public ImmutablePlayer getPlayer(String name) {
				return null;
			}

			@Override
			public void setView(View view) {

			}

			@Override
			public void setGameState(GameplayState newGameState) {

			}

			@Override
			public ModifiableGameBoard getModifiableCells() {
				return null;
			}

			@Override
			public void sendModifier(Modifier<GameplayState> modifier) {
				System.out.println("test");
				client.addToOutbox(modifier);
			}

			@Override
			public Collection<? extends Unit> getUnits() {
				return null;
			}

			@Override
			public Collection<? extends Terrain> getTerrains() {
				return null;
			}

			@Override
			public Collection<? extends Unit> getUnitTemplates() {
				return null;
			}

			@Override
			public Collection<? extends Terrain> getTerrainTemplates() {
				return null;
			}

			@Override
			public void addToUpdated(Updatable objectToUpdate) {

			}

			@Override
			public void removeFromUpdated(Updatable objectToUpdate) {

			}
		});
		Scene scene = new Scene(chatLogView.getObject(), 500, 500, new ImagePattern(new Image("resources/images/testImage.jpg")));
		scene.getStylesheets().add("resources/styles/notheme.css");
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.show();
	}
}

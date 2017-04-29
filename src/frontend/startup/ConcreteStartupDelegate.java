package frontend.startup;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.Optional;

import backend.util.ReadonlyGameplayState;
import controller.CommunicationController;
import controller.Controller;
import frontend.View;
import frontend.factory.wizard.WizardFactory;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.net.ObservableHost;

class ConcreteStartupDelegate implements StartupDelegate {

	@Override
	public void create(Stage stage) {
		try {
			create(stage, getPortNumber());
		} catch (Exception e) {
			throwServerAlert();
		}
	}

	@Override
	public void join(Stage stage) {
		try {
			join(stage, getHost(), getPortNumber());
		} catch (Exception e) {
			throwServerAlert();
		}
	}

	@Override
	public void play(Stage stage) {
		try {
			load(stage, getPortNumber());
		} catch (Exception e) {
			throwServerAlert();
		}
	}

	private void create(Stage stage, int port) {
		Controller control = new CommunicationController(
				System.getProperty("user.name") + "-" + System.currentTimeMillis() % 100);
		System.out.println("Creating wizard");
		WizardFactory.newWizard("gamestate", null).addObserver((o, arg) -> {
			System.out.println("Detected change in wizard");
			control.startServer((ReadonlyGameplayState) arg, port, Duration.ofSeconds(30));
			control.startClient(ObservableHost.LOCALHOST, port, Duration.ofSeconds(30));
			createGame(control, stage);
		});
	}

	private void join(Stage stage, String host, int port) {
		Controller control = new CommunicationController(
				System.getProperty("user.name") + "-" + System.currentTimeMillis() % 100);
		control.startClient(host, port, Duration.ofSeconds(30));
		createGame(control, stage);
	}

	private void load(Stage stage, int port) {
		Controller control = new CommunicationController(
				System.getProperty("user.name") + "-" + System.currentTimeMillis() % 100);
		control.startServer(loadFile(control), port, Duration.ofSeconds(30));
		control.startClient(ObservableHost.LOCALHOST, port, Duration.ofSeconds(30));
		createGame(control, stage);
	}

	private void createGame(Controller control, Stage stage) {
		View view = new View(control, stage);
		stage.setScene(new Scene(view.getNode()));
		stage.setMaximized(true);
		control.updateAll();
	}

	private String getHost() {
		try {
			TextInputDialog dialog = new TextInputDialog("Enter Host Address");
			dialog.setTitle("Connecting to host");
			dialog.setHeaderText("Please enter the hostname of the server you are trying to connect to.");
			dialog.setContentText("Hostname/IP address");
			Optional<String> result = dialog.showAndWait();
			return result.orElseThrow(RuntimeException::new);
		} catch (Exception e) {
			return ObservableHost.LOCALHOST;
		}
	}

	private ReadonlyGameplayState loadFile(Controller control) {
		try {
			FileChooser chooser = new FileChooser();
			chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".xml Files", "*.xml"));
			return control.load(Paths.get(chooser.showOpenDialog(null).getAbsolutePath()));
		} catch (Exception e) {
			Platform.exit();
			return null;
		}
	}

	private boolean isValidPort(int port) {
		return (port > 1024 && port <= 65535);
	}

	private void throwServerAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("Invalid Port Number"); // TODO: ResourceBundle
		alert.show();
	}

	private int getPortNumber() {
		try {
			TextInputDialog dialog = new TextInputDialog("Enter a number (1024 - 65535)");
			dialog.setTitle("Creating server....");
			dialog.setHeaderText("Enter a port number for your server");
			dialog.setContentText("Port number:");
			Optional<String> result = dialog.showAndWait();
			if (isValidPort(Integer.parseInt(result.get()))) {
				return Integer.parseInt(result.get());
			} else {
				throw new RuntimeException();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

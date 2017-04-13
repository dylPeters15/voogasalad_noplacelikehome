package frontend.util;

import backend.player.ChatMessage;
import backend.util.GameplayState;
import controller.Controller;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class ChatLogView extends BaseUIManager {
	//TODO ResourceBundlify
	private final String HEADER;
	private final BorderPane pane;
	private final TextArea textArea;
	private final String playerName;
	private final MediaPlayer mediaPlayer;

	public ChatLogView(String playerName, Controller<GameplayState> controller) {
		super(controller);
		HEADER = String.format("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n----Joined [No place like 127.0.0.1]'s chat room!----\n\n---%s----\n\n", Instant.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)));
		pane = new BorderPane();
		textArea = initTextArea();
		pane.setCenter(textArea);
		pane.setBottom(initTextInputBox());
		this.playerName = playerName;
		mediaPlayer = new MediaPlayer(new Media(Paths.get("src/resources/steam_message_sound.mp3").toUri().toString()));
	}

	@Override
	public Region getObject() {
		return pane;
	}

	private TextArea initTextArea() {
		TextArea textArea = new TextArea(HEADER);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.positionCaret(textArea.getText().length());
		return textArea;
	}

	public void update() {
		textArea.setText(HEADER + getController().getGameState().getPlayerByName(playerName).getChatLog().stream().map(Object::toString).collect(Collectors.joining("\n\n")));
		textArea.positionCaret(textArea.getText().length());
		mediaPlayer.seek(Duration.ZERO);
		mediaPlayer.play();
	}

	private HBox initTextInputBox() {
		HBox bottomBox = new HBox();
		bottomBox.getStyleClass().add("hbox");
		bottomBox.setAlignment(Pos.BASELINE_CENTER);
		ComboBox<ChatMessage.AccessLevel> chatModeChooser = initComboBox();
		Label label1 = new Label("To:");
		label1.setMinWidth(30);
		TextField messageRecipientField = new TextField();
		messageRecipientField.setMinWidth(80);
		chatModeChooser.setOnAction(event -> showOrHideRecipientField(bottomBox, chatModeChooser, label1, messageRecipientField));
		TextField textContentInputField = new TextField();
		textContentInputField.setPrefWidth(600);
		textContentInputField.setOnKeyPressed(evt -> submitMessage(evt, chatModeChooser, textContentInputField, messageRecipientField));
		bottomBox.getChildren().addAll(chatModeChooser, textContentInputField);
		return bottomBox;
	}

	private ComboBox<ChatMessage.AccessLevel> initComboBox() {
		ComboBox<ChatMessage.AccessLevel> chatModeChooser = new ComboBox<>(FXCollections.observableArrayList(ChatMessage.AccessLevel.values()));
		chatModeChooser.setMinWidth(110);
		chatModeChooser.setValue(ChatMessage.AccessLevel.ALL);
		return chatModeChooser;
	}

	private void showOrHideRecipientField(HBox bottomBox, ComboBox<ChatMessage.AccessLevel> chatModeChooser, Label toLabel, TextField messageRecipientField) {
		if (chatModeChooser.getValue().equals(ChatMessage.AccessLevel.WHISPER)) {
			bottomBox.getChildren().add(1, messageRecipientField);
			bottomBox.getChildren().add(1, toLabel);
		} else {
			bottomBox.getChildren().remove(toLabel);
			bottomBox.getChildren().remove(messageRecipientField);
			messageRecipientField.clear();
		}
	}

	private void submitMessage(KeyEvent evt, ComboBox<ChatMessage.AccessLevel> chatModeChooser, TextField textContentInputField, TextField messageRecipientField) {
		if (evt.getCode() == KeyCode.ENTER) {
			getController().sendModifier(chatModeChooser.getValue().getSendMessageModifier(textContentInputField.getText(), playerName, messageRecipientField.getText()));
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			textContentInputField.clear();
		}
	}
}

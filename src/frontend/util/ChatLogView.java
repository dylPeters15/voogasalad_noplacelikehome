package frontend.util;

import backend.player.ChatMessage;
import controller.Controller;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.stream.Collectors;


/**
 * @author Created by th174 on 3/31/2017.
 */
public class ChatLogView extends BaseUIManager {
	//TODO ResourceBundlify
	private final BorderPane pane;
	private final TextArea textArea;
	private final String playerName;
	private final Controller controller;

	public ChatLogView(String playerName, Controller controller) {
		pane = new BorderPane();
		textArea = initTextArea();
		pane.setCenter(textArea);
		pane.setBottom(initTextInputBox());
		pane.setBackground(Background.EMPTY);
		this.playerName = playerName;
		this.controller = controller;
	}

	@Override
	public Region getObject() {
		return pane;
	}

	private TextArea initTextArea() {
		TextArea textArea = new TextArea("\n\n\n\n\n\n\n\n\n\n\n\n\n------------TEST GAME STATE CHAT LOG------------");
		textArea.setEditable(false);
		//TODO: this doesn't workg
		textArea.setBackground(Background.EMPTY);
		return textArea;
	}


	public void update(Controller controller) {
		textArea.setText(textArea.getText() + "\n" + controller.getGameplayState().getPlayerByName(playerName).getChatLog().stream().map(Object::toString).collect(Collectors.joining("\n")));
	}

	private HBox initTextInputBox() {
		HBox bottomBox = new HBox();
		bottomBox.setAlignment(Pos.BASELINE_CENTER);
		bottomBox.setBackground(Background.EMPTY);
		ComboBox<ChatMessage.AccessLevel> chatModeChooser = new ComboBox<>(FXCollections.observableArrayList(ChatMessage.AccessLevel.values()));
		chatModeChooser.setBackground(Background.EMPTY);
		chatModeChooser.setMinWidth(120);
		TextField messageRecipientField = new TextField("Player name...");
		messageRecipientField.setMinWidth(150);
		messageRecipientField.setBackground(Background.EMPTY);
		chatModeChooser.setOnAction(event -> {
			if (chatModeChooser.getValue().equals(ChatMessage.AccessLevel.WHISPER)) {
				bottomBox.getChildren().add(1, messageRecipientField);
			} else {
				bottomBox.getChildren().remove(messageRecipientField);
				messageRecipientField.clear();
			}
		});
		TextField textContentInputField = new TextField("Your message...");
		textContentInputField.setBackground(Background.EMPTY);
		textContentInputField.setPrefWidth(1000);
		textContentInputField.setBackground(Background.EMPTY);
		textContentInputField.setOnKeyPressed((KeyEvent evt) -> {
			if (evt.getCode() == KeyCode.ENTER) {
				controller.sendModifier(chatModeChooser.getValue().getSendMessageModifier(textContentInputField.getText(), playerName));
				textContentInputField.clear();
			}
		});
		bottomBox.getChildren().addAll(chatModeChooser, textContentInputField);
		return bottomBox;
	}
}

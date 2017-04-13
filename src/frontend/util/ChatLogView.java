package frontend.util;

import backend.player.ChatMessage;
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
		return textArea;
	}

	public void update(Controller controller) {
		textArea.setText(textArea.getText() + "\n" + controller.getGameplayState().getPlayerByName(playerName).getChatLog().stream().map(Object::toString).collect(Collectors.joining("\n")));
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
		textContentInputField.setOnKeyPressed(evt -> submitMessage(evt, chatModeChooser, textContentInputField));
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

	private void submitMessage(KeyEvent evt, ComboBox<ChatMessage.AccessLevel> chatModeChooser, TextField textContentInputField) {
		if (evt.getCode() == KeyCode.ENTER) {
			controller.sendModifier(chatModeChooser.getValue().getSendMessageModifier(textContentInputField.getText(), playerName));
			textContentInputField.clear();
		}
	}
}

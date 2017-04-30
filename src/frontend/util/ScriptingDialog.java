package frontend.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import controller.Controller;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import util.scripting.VoogaScriptEngine;
import util.scripting.VoogaScriptEngineManager;
import util.scripting.VoogaScriptException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * TODO: RESOUCE BUNDLE PLSSSS
 *
 * @author Created by th174 on 4/11/2017.
 */
public class ScriptingDialog extends BaseUIManager<Region> {
	private final BorderPane pane;
	private final TextArea scriptArea;
	private final ChoiceBox<String> languagesMenu;
	private VoogaScriptEngine scriptEngine;
	private BooleanProperty hasCompiled;
	private String strategy;
	private final double SPACING = 20;
	

	public ScriptingDialog(Controller controller) {
		super(controller);
		hasCompiled = new SimpleBooleanProperty(false);
		languagesMenu = new ChoiceBox<>(
				FXCollections.observableArrayList(VoogaScriptEngineManager.getAllSupportedScriptingLanguages()));
		pane = new BorderPane();
		scriptArea = new TextArea();
		Button compileButton = new Button();
		Button loadScriptButton = new Button();
		Button quickCreateButton = new Button(); 
		compileButton.textProperty().bind(getPolyglot().get("Compile"));
		loadScriptButton.textProperty().bind(getPolyglot().get("loadScript"));
		quickCreateButton.textProperty().bind(getPolyglot().get("quickAdd"));
		
		compileButton.setOnAction(evt -> {
			try {
				scriptEngine = VoogaScriptEngineManager.read(languagesMenu.getValue(), scriptArea.getText());
				hasCompiled.setValue(true);
			} catch (VoogaScriptException e) {
				handleException(e);
			}
		});
		
		loadScriptButton.setOnAction(evt -> {
			try {
				scriptArea.setText(loadScript());
			} catch (VoogaScriptException e) {
				handleException(e);
			}
		});
		
		quickCreateButton.setOnAction(evt -> {
			try {
				loadScript();
			} catch (VoogaScriptException e) {
				handleException(e);
			}
		});
		
		Label scriptingLabel = new Label();
		scriptingLabel.textProperty().bind(getPolyglot().get("chooseScriptingLang"));
		HBox topBox = new HBox(scriptingLabel);
		topBox.getChildren().add(languagesMenu);
		topBox.getChildren().add(quickCreateButton);
		topBox.setAlignment(Pos.CENTER);
		topBox.setSpacing(SPACING);
		
		HBox bottomBox = new HBox(loadScriptButton);
		bottomBox.getChildren().add(compileButton);
		bottomBox.setAlignment(Pos.TOP_RIGHT);
		languagesMenu.setOnAction(
				event -> scriptArea.setText(VoogaScriptEngineManager.getDefaultText(languagesMenu.getValue())));
		pane.setTop(topBox);
		pane.setBottom(bottomBox);
		pane.setCenter(scriptArea);
	}
	
	private String loadScript() {
		String code = "";
		final FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Open Script");
		this.configureFileChooser(filechooser);
		File myScript = filechooser.showOpenDialog(null);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(myScript));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			code = sb.toString();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	private void configureFileChooser(final FileChooser filechooser) {
		filechooser.setTitle("Load XML");
		filechooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/data/examples"));
		filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JAVA", "*.java"));
	}


	public ReadOnlyBooleanProperty hasCompiled() {
		return hasCompiled;
	}

	public void setPrompt(String strat) {
		scriptArea.setPromptText(getResourceBundle().getString(strat + languagesMenu.getValue()));
		strategy = strat;
	}

	private void handleException(Exception e) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.titleProperty().bind(getPolyglot().get("syntaxError"));
		alert.setHeaderText(null);
		alert.contentTextProperty().bind(getPolyglot().get("compileError"));
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.getCause().printStackTrace(pw);
		String exceptionText = sw.toString();
		Label label = new Label();
		label.textProperty().bind(getPolyglot().get("stackTrace"));
		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
		hasCompiled.setValue(false);
	}

	@Override
	public void update() {
	}

	@Override
	public Region getNode() {
		return pane;
	}

	public String getLanguage() {
		return languagesMenu.getValue();
	}

	public Optional<VoogaScriptEngine> getScriptEngine() {
		return Optional.ofNullable(scriptEngine);
	}
}

package frontend.newunitpane;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ModalNewUnitPane extends NewUnitPane {

	private Stage stage;

	public ModalNewUnitPane() {
		initialize();
	}

	@Override
	protected void submit() {
		super.submit();
		stage.close();
	}

	private void initialize() {
		stage = new Stage();
		stage.setScene(new Scene(getObject()));
		stage.show();
	}

}

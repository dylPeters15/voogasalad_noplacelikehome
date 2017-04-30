package frontend.factory.wizard.strategies.wizard_pages;

import backend.grid.GridPattern;
import controller.Controller;
import frontend.factory.wizard.strategies.wizard_pages.util.WizardGrid;
import javafx.scene.layout.Region;

/**
 * GridPatternPage is a WizardPage that shows the user a Grid that is similar to
 * the one the game is using and allows the user to select cells in order to
 * create a GridPattern object. This GridPattern object can be used as the range
 * for an attack or a movement pattern for a unit.
 * 
 * @author Dylan Peters
 *
 */
public class GridPatternPage extends BaseWizardPage {
	private WizardGrid grid;

	/**
	 * Creates a new instance of GridPatternPage
	 * 
	 * @param gameState
	 *            the AuthoringGameState that this page will use to populate its
	 *            fields
	 * @param descriptionKey
	 *            a String that can be used as a key to a ResourceBundle to set
	 *            the description of the page
	 */
	public GridPatternPage(Controller controller, String descriptionKey) {
		super(controller, descriptionKey);
		initialize();
	}

	private void initialize() {
		grid = new WizardGrid(getController().getAuthoringGameState());
		canNextWritable().setValue(true);
	}

	@Override
	public Region getNode() {
		return grid.getNode();
	}

	/**
	 * Returns a GridPattern based on the cells selected by the user.
	 * 
	 * @return a GridPattern based on the cells selected by the user.
	 */
	public GridPattern getGridPattern() {
		return grid.getGridPattern();
	}

}
/**
 *
 */
package frontend;

import backend.util.ImmutableGameState;
import controller.Controller;
import frontend.detailpane.DetailPane;
import frontend.templatepane.TemplatePane;
import frontend.toolspane.ToolsPane;
import frontend.voogamenubar.VoogaMenuBar;
import frontend.worldview.WorldView;

/**
 * @author Stone Mathers, Dylan Peters
 *         Created 4/3/2017
 */
public class View extends BaseUIManager {

    private VoogaMenuBar menuBar;
    private WorldView worldView;
    private ToolsPane toolsPane;
    private DetailPane infoPane;
    private TemplatePane tempPane;


    public View(ImmutableGameState gameState, Controller controller) {

    }


    public void update() {

    }

    private void instantiateListeners() {

    }

    @Override
    public Object getObject() {
        // TODO Auto-generated method stub
        return null;
    }

}

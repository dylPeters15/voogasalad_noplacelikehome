package frontend.interfaces.detailpane;

import backend.util.VoogaEntity;
import frontend.ClickHandler;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Region;
import util.polyglot.Polyglot;

public interface DetailPaneExternal {

	Region getNode();

	void setContent(VoogaEntity entity);

	Polyglot getPolyglot();

	void setClickHandler(ClickHandler clickHandler);

	void setAuthorMode();

	void setPlayMode();

	ObjectProperty<String> getStyleSheet();

}

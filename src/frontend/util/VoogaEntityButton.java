package frontend.util;

import backend.util.VoogaEntity;
import controller.Controller;
import frontend.ClickHandler;
import frontend.ClickableUIComponent;
import frontend.View;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;


/**
 * @author Created by th174 on 4/21/17.
 */
public class VoogaEntityButton extends SelectableUIComponent<Button> {
	private final Button entityButton;
	private final VoogaEntity entity;

	public VoogaEntityButton(VoogaEntity entity, int size, Controller controller, ClickHandler clickHandler) {
		super(controller, clickHandler);
		this.entity = entity;
		ImageView sprite = new ImageView(View.getImg(entity.getImgPath()));
		entityButton = new Button("", sprite);
		entityButton.setPadding(new Insets(10, 10, 10, 10));
		sprite.setFitWidth(size);
		sprite.setFitHeight(size);
		entityButton.setTooltip(new Tooltip(entity.getFormattedName()));
		entityButton.setPadding(Insets.EMPTY);
		//entityButton.setOnMouseClicked(event -> setAsSelected());
		entityButton.setOnDragDetected(event -> {
			getObject().startFullDrag();
			setAsSelected();
			System.out.println("detected");
		});
	}

	@Override
	public Button getObject() {
		return entityButton;
	}

	public VoogaEntity getEntity() {
		return entity;
	}

	@Override
	public void actInAuthoringMode(ClickableUIComponent target, Object additonalInfo, ClickHandler clickHandler, Event event) {
		setAsSelected();
	}

	@Override
	public void actInGameplayMode(ClickableUIComponent target, Object additionalInfo, ClickHandler clickHandler, Event event) {
		actInAuthoringMode(target, additionalInfo, clickHandler, event);
	}
}

package backend.util;

import backend.game_engine.GameState;

/**
 * Timmy
 *
 * @author Created by th174 on 3/28/2017.
 */
public abstract class VoogaInstance<T extends VoogaObject> extends VoogaObject {
    private final T template;
    private final GameState gameState;
    private boolean isVisible;

    protected VoogaInstance(String unitName, T template, GameState gameState) {
        super(unitName, template.getDescription(), template.getImgPath());
        this.template = template;
        this.gameState = gameState;
    }

    protected GameState getGameState() {
        return gameState;
    }

    public T getTemplate() {
        return template;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}

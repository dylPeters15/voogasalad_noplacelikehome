package backend.util;

import backend.game_engine.GameState;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Timmy
 *
 * @author Created by th174 on 3/28/2017.
 */
public class GameObjectImpl implements GameObject {
    private final GameState currentGame;
    private final String name;
    private final String description;
    private final Path imgPath;
    private boolean isVisible;

    public GameObjectImpl(String name, Path imgPath, GameState currentGame) {
        this(name, "", imgPath, currentGame);
    }

    public GameObjectImpl(String name, String imgPath, GameState currentGame) {
        this(name, "", imgPath, currentGame);
    }

    public GameObjectImpl(String name, String description, String imgPath) {
        this(name, description, imgPath, null);
    }

    public GameObjectImpl(String name, String description, Path imgPath) {
        this(name, description, imgPath, null);
    }

    public GameObjectImpl(String name, String description, String imgPath, GameState currentGame) {
        this(name, description, Paths.get(imgPath), currentGame);
    }

    public GameObjectImpl(String name, String description, Path imgPath, GameState currentGame) {
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
        this.currentGame = currentGame;
    }

    public GameState getGame() {
        return currentGame;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Path getImgPath() {
        return imgPath;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public String toXml() {
        //TODO: Make Tavo XStream this shit
        throw new RuntimeException("Not implemented yet");
    }

    protected static <T extends GameObjectImpl> Collection<T> getPredefined(Class<T> clazz) {
        return Arrays.stream(clazz.getFields()).map(e -> {
            try {
                return e.get(null);
            } catch (IllegalAccessException e1) {
                throw new RuntimeException("This will never happen");
            }
        }).filter(clazz::isInstance).map(clazz::cast).collect(Collectors.toList());
    }
}

package backend;

import java.nio.file.Path;
import java.nio.file.Paths;

import backend.game_engine.GameState;
import backend.io.XMLsavable;

/**
 * Alex
 * @author Created by th174 on 3/28/2017.
 */
public class GameObjectImpl implements XMLsavable {
    private final GameState currentGame;
    private final String name;
    private final String description;
    private final Path imgPath;

    public GameObjectImpl(String name, Path imgPath, GameState currentGame) {
        this(name, "", imgPath, currentGame);
    }

    public GameObjectImpl(String name, String imgPath, GameState currentGame) {
        this(name, "", imgPath, currentGame);
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
    public String toXml() {
        throw new RuntimeException("Not implemented yet");
    }
}

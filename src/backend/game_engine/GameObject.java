package backend.game_engine;

import java.nio.file.Path;
import java.nio.file.Paths;

import backend.io.XMLsavable;

/**
 * Alex
 * @author Created by th174 on 3/28/2017.
 */
public class GameObject implements XMLsavable {
    private final GameEngine currentGame;
    private final String name;
    private final String description;
    private final Path imgPath;

    public GameObject(String name, Path imgPath, GameEngine currentGame) {
        this(name, "", imgPath, currentGame);
    }

    public GameObject(String name, String imgPath, GameEngine currentGame) {
        this(name, "", imgPath, currentGame);
    }

    public GameObject(String name, String description, String imgPath, GameEngine currentGame) {
        this(name, description, Paths.get(imgPath), currentGame);
    }

    public GameObject(String name, String description, Path imgPath, GameEngine currentGame) {
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
        this.currentGame = currentGame;
    }

    public GameEngine getGame() {
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

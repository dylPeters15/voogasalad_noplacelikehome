package backend;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Created by th174 on 3/28/2017.
 */
public class GameObject implements XMLsavable {
    private final Game currentGame;
    private final String name;
    private final String description;
    private final Path imgPath;

    public GameObject(String name, Path imgPath, Game currentGame) {
        this(name, "", imgPath, currentGame);
    }

    public GameObject(String name, String imgPath, Game currentGame) {
        this(name, "", imgPath, currentGame);
    }

    public GameObject(String name, String description, String imgPath, Game currentGame) {
        this(name, description, Paths.get(imgPath), currentGame);
    }

    public GameObject(String name, String description, Path imgPath, Game currentGame) {
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
        this.currentGame = currentGame;
    }

    public Game getGame() {
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

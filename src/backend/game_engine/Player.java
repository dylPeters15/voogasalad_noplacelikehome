package backend.game_engine;

import java.nio.file.Path;

/**
 * Alex
 * @author Created by th174 on 3/28/2017.
 */
public class Player extends GameObject {
    protected Player(String name, String description, String imgPath, GameEngine currentGame) {
        super(name, description, imgPath, currentGame);
    }

    protected Player(String name, String description, Path imgPath, GameEngine currentGame) {
        super(name, description, imgPath, currentGame);
    }
}

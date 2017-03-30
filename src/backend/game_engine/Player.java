package backend.game_engine;

import backend.GameObjectImpl;

import java.nio.file.Path;

/**
 * Alex
 * @author Created by th174 on 3/28/2017.
 */
public class Player extends GameObjectImpl {
    protected Player(String name, String description, String imgPath, GameState currentGame) {
        super(name, description, imgPath, currentGame);
    }

    protected Player(String name, String description, Path imgPath, GameState currentGame) {
        super(name, description, imgPath, currentGame);
    }
}

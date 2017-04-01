package backend.networking;

import backend.util.GameState;

import java.io.Serializable;

/**
 * @author Created by th174 on 4/1/2017.
 */
@FunctionalInterface
public interface Message extends Serializable {
    void execute(GameState gameState);
}

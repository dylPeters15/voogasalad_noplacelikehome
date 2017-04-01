package backend.networking;

import java.io.Serializable;

/**
 * @author Created by th174 on 4/1/2017.
 */
@FunctionalInterface
public interface Message<T> extends Serializable {
    void execute(T gameState);
}

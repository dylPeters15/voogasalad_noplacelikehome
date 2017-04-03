package util.net;

import java.io.Serializable;

/**
 * This interface provides a way for the client and server to communicate by sending lambda expressions that modifies or creates new game state.
 * <p>
 * This interface represents some kind of to the shared state, which if approved by the server, is propagated across all other clients.
 *
 * @param <T> The type of variable used to represent networked shared state.
 * @author Created by th174 on 4/1/2017.
 * @see Request,Modifier,Server, Server.ServerThread ,Client, Host ,Listener, AbstractHost
 */
@FunctionalInterface
public interface Modifier<T> extends Serializable {
    /**
     * @param state State before the action is applied.
     * @return Returns the new state after the action is applied.
     * @throws Exception Thrown if the implementation throws an exception
     */
    T modify(T state) throws Exception;
}

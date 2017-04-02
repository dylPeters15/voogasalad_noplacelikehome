package util.net;

import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * This class provides a way for the client and server to communicate by sending lambda expressions that modifies or creates new game state.
 * <p>
 * It allows for changes to the shared state to be received by the server and propagated across all other clients.
 * <p>
 * Requests are proposed by clients and sent to the server for approval. If the server approves the request, the request will be propagated to all clients.
 *
 * @param <T> The type of variable used to represent networked shared state.
 * @author Created by th174 on 4/1/2017.
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote
 */
public class VoogaRequest<T> implements Serializable {
    private final SerializableStateModifier<T> modifier;
    private final Instant timeStamp;

    /**
     * @param action A lambda expression that modifies a state
     */
    public VoogaRequest(SerializableStateModifier<T> action) {
        this.modifier = action;
        this.timeStamp = Instant.now(Clock.systemUTC());
    }

    /**
     * @param state State before the action is applied
     */
    public void modify(T state) {
        try {
            modifier.accept(state);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return Timestamp when this request was created
     */
    public Instant getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "Request @ " + timeStamp.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    /**
     * Extends Consumer to be serializable and operate on an XML serializable state.
     *
     * @param <T> The type of variable used to represent networked shared state
     */
    @FunctionalInterface
    public interface SerializableStateModifier<T> extends Serializable {
        /**
         * @param state State before modification
         * @throws Exception Thrown if the implementation throws an exception
         */
        void accept(T state) throws Exception;
    }
}

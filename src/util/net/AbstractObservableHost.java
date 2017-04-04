package util.net;

import util.io.Serializer;
import util.io.Unserializer;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * This interface provides a general API for a host communicating over TCP/IP with another remote host.
 * <p>
 * It can send and receive requests that modify networked state from a remote host.
 * <p>
 * State modifications can be seen with an Observable
 *
 * @param <T> The type of variable used to represent network shared state.
 * @author Created by th174 on 4/2/2017.
 * @see Request,Modifier,ObservableServer,ObservableServer.ServerThread,ObservableClient,ObservableHost,AbstractObservableHost, RemoteListener
 */
public abstract class AbstractObservableHost<T> {
    public static final Duration NEVER_TIMEOUT = Duration.ZERO;
    public static final String LOCALHOST = "127.0.0.1";
    public static final Serializer NO_SERIALIZER = obj -> (Serializable) obj;
    public static final Unserializer NO_UNSERIALIZER = obj -> obj;
    private final Serializer<T> serializer;
    private final Unserializer<T> unserializer;
    private final Collection<Consumer<T>> stateUpdateListeners;
    private final Duration timeout;

    /**
     * Creates an instance of an AbstractObservableHost
     */
    protected AbstractObservableHost() {
        this(NO_SERIALIZER, NO_UNSERIALIZER);
    }

    /**
     * @param serializer   Converts the state to a Serializable form, so that it can be sent to the client
     * @param unserializer Converts the Serializable form of the state back into its original form of type T
     */
    protected AbstractObservableHost(Serializer<T> serializer, Unserializer<T> unserializer) {
        this(serializer, unserializer, NEVER_TIMEOUT);
    }

    /**
     * @param serializer   Converts the state to a Serializable form, so that it can be sent to the client
     * @param unserializer Converts the Serializable form of the state back into its original form of type T
     * @param timeout      Timeout duration for the socket connection
     */
    protected AbstractObservableHost(Serializer<T> serializer, Unserializer<T> unserializer, Duration timeout) {
        this.serializer = serializer;
        this.unserializer = unserializer;
        this.stateUpdateListeners = new ArrayList<>();
        this.timeout = timeout;
    }

    /**
     * Starts communicating with the remote host
     *
     * @throws Exception Thrown if exception occurs in starting the host
     */
    public abstract void start() throws Exception;

    /**
     * @return Returns the local state, which should match network shared state
     */
    public abstract T getState();

    /**
     * This method is invoked when a new state is received from the remote host.
     *
     * @param newState  The new state sent from the remote host.
     * @param timeStamp Request creation timestamp
     * @throws Exception Thrown when an error occurs while processing the request the new state.
     */
    protected abstract void handle(T newState, Instant timeStamp);

    /**
     * This method is invoked when a state modifier is received from the remote host.
     *
     * @param stateModifier The state modifier received from the remote host.
     * @param timeStamp     Request creation timestamp
     * @throws Exception Thrown when an error occurs while processing the state modifier.
     */
    protected abstract void handle(Modifier<T> stateModifier, Instant timeStamp);

    /**
     * Sends a state modifier to the remote host
     *
     * @param modifier Modifier to be sent over network.
     * @return Returns true if request was sent successfully
     * @throws Exception Thrown when error occurs in serializing or sending the modifier.
     */
    protected abstract boolean send(Modifier<T> modifier);

    /**
     * Sends a full new state to the remote host
     *
     * @param newState New state to be serialized and sent over network
     * @return Returns true if request was sent successfully
     * @throws Exception Thrown when error occurs in serializing or sending the modifier.
     */
    protected abstract boolean send(T newState);

    /**
     * @return Returns true while the connection associated with this object is active
     */
    public abstract boolean isActive();

    /**
     * @return Returns the amount time until the socket times out, in milliseconds
     */
    protected Duration getTimeout() {
        return timeout;
    }

    /**
     * @return Returns the serializer currently used by this host
     */
    protected Serializer<T> getSerializer() {
        return serializer;
    }

    /**
     * @return Returns the serializer currently usd by this host
     */
    protected Unserializer<T> getUnserializer() {
        return unserializer;
    }

    /**
     * Notifies all currently registered observers of a value change.
     */
    protected void fireStateUpdatedEvent() {
        stateUpdateListeners.forEach(e -> e.accept(getState()));
    }

    /**
     * Adds a listener which will be notified whenever the local state is updated.
     *
     * @param stateUpdateListener The listener to register
     */
    public void addListener(Consumer<T> stateUpdateListener) {
        stateUpdateListeners.add(stateUpdateListener);
    }

    /**
     * Removes the given listener from the list of listeners, that are notified whenever the local state is updated.
     *
     * @param stateUpdateListener The listener to remove
     */
    public void removeListener(Consumer<T> stateUpdateListener) {
        stateUpdateListeners.remove(stateUpdateListener);
    }
}

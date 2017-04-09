package util.net;

import util.io.Serializer;
import util.io.Unserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * This class provides a basic implementation of a host process that connects to and communicates with a remote host.
 * <p>
 * It modifies a state of type T, and notifies listeners of changes to the state.
 * <p>
 * The shared network state should model the distributed networked state of your program, and contain information that needs to be replicated on each of the clients.
 *
 *
 * @param <T> The type of variable used to represent network shared state.
 * @author Created by th174 on 4/1/2017.
 * @see Request,Modifier,ObservableServer,ObservableServer.ServerDelegate,ObservableClient,ObservableHost
 */
public abstract class ObservableHost<T> implements Runnable {
    public static final Duration NEVER_TIMEOUT = Duration.ZERO;
    public static final String LOCALHOST = "127.0.0.1";
    private final Serializer<T> serializer;
    private final Unserializer<T> unserializer;
    private final Collection<Consumer<T>> stateUpdateListeners;
    private final Duration timeout;
    private AtomicInteger commitIndex;
    private volatile T state;

    /**
     * @param serializer   Converts the state to a Serializable form, so that it can be sent to the client
     * @param unserializer Converts the Serializable form of the state back into its original form of type T
     * @param timeout Socket timeout duration
     */
    protected ObservableHost(Serializer<T> serializer, Unserializer<T> unserializer, Duration timeout) {
        this.serializer = serializer;
        this.unserializer = unserializer;
        this.stateUpdateListeners = new ArrayList<>();
        this.commitIndex = new AtomicInteger(0);
        this.timeout = timeout;
    }

    @Override
    public abstract void run();

    /**
     * @return Returns the state on the local machine, which should match the network's shared state
     */
    protected final T getState() {
        return state;
    }

    /**
     * Sets the state on the local host.
     *
     * @param state State on local host, should match networked state
     */
    protected synchronized void setState(T state) {
        this.state = state;
    }

    /**
     * @return Returns current local commit index
     */
    protected final int getCommitIndex() {
        return commitIndex.get();
    }

    /**
     * Increments the commit index by one
     */
    protected final void setCommitIndex(int value) {
        commitIndex.set(value);
    }

    /**
     * This method is invoked on all requests received from the remote host
     *
     * @param request Incoming request received from remote host
     * @return Returns true if the host was able to successfully process the request sent from the remote host
     */
    protected boolean handleRequest(Request request) {
	    if (validateRequest(request)) {
            if (request.get() instanceof Modifier) {
                handle((Modifier<T>) request.get());
            } else {
                try {
                    handle(getUnserializer().unserialize(request.get()));
                } catch (Unserializer.UnserializationException | ClassCastException e) {
                	e.printStackTrace();
                    handleError();
                    return false;
                }
            }
            return true;
        } else {
            handleError();
            return false;
        }
    }

    /**
     * Invoked when the remote host sends a request indicating that it is in an error state
     *
     * @return Returns true if error is handled successfully
     */
    protected abstract boolean handleError();

    /**
     * Checks if a request received from the remote host is a valid request.
     *
     * @param incomingRequest Request received from remote host
     * @return Returns true if the request is valid
     */
    protected abstract boolean validateRequest(Request incomingRequest);

    /**
     * Invoked when the local host receives a modifier sent from the remote host
     *
     * @param modifier modifier receieved from remote host
     */
    protected void handle(Modifier<T> modifier) {
        this.state = modifier.modify(state);
        fireStateUpdatedEvent();
    }

    /**
     * Invoked when the local host receives a new state from the remote host
     *
     * @param state state received from remote host
     */
    protected void handle(T state) {
        this.state = state;
        fireStateUpdatedEvent();
    }

    /**
     * Sends a request to the remote host
     *
     * @param request Request to be sent to the remote host
     * @return Returns true if request was sent successfully
     */
    protected abstract boolean send(Request request);

    /**
     * @param state New state object to be serialized and sent to the remote host
     * @return Returns a request that contains a serialized version of the state.
     */
    protected final Request getRequest(T state) {
        return new Request<>(getSerializer().serialize(state), getCommitIndex());
    }

    /**
     * Sends a serialized new state object to the remote host
     *
     * @param state New state object to be serialized and sent to the remote host
     * @return Returns true if the request was sent successfully
     */
    public final boolean send(T state) {
        return send(getRequest(state));
    }

    /**
     * @param modifier Modifier and sent to the remote host
     * @return Returns a request that contains a modification of the state
     */
    public final Request getRequest(Modifier<T> modifier) {
        return new Request<>(modifier, getCommitIndex());
    }

    /**
     * Sends a modifier on the state to the remote host
     *
     * @param modifier Modifier to be sent and applied to the state
     * @return Returns true if the request was sent successfully
     */
    public final boolean send(Modifier<T> modifier) {
        return send(getRequest(modifier));
    }

    /**
     * @return Returns a heartbeat request notifying the remote host that the local host is in a normal operation state.
     */
    protected final Request getHeartBeatRequest() {
        return new Request<>(Request.HEARTBEAT, (getCommitIndex()));
    }

    /**
     * Sends a heartbeat request notifying the remote host that the local host is in a normal operation state.
     *
     * @return Returns true if the request was sent successfully
     */
    protected final boolean sendHeartBeatRequest() {
        return send(getHeartBeatRequest());
    }

    /**
     * @return Returns an error request notifying the remote host that the local host is currently in an error state.
     */
    protected final Request getErrorRequest() {
        return new Request<>(Request.ERROR, getCommitIndex());
    }

    /**
     * Sends an error request notifying the remote host that the local host is currently in an error state.
     *
     * @return Returns true if the message was sent successfully
     */
    protected final boolean sendErrorRequest() {
        return send(getErrorRequest());
    }

    /**
     * Adds a listener which will be notified whenever the local state is updated.
     *
     * @param stateUpdateListener The listener to register
     */
    public final void addListener(Consumer<T> stateUpdateListener) {
        stateUpdateListeners.add(stateUpdateListener);
    }

    /**
     * Removes the given listener from the list of listeners, that are notified whenever the local state is updated.
     *
     * @param stateUpdateListener The listener to remove
     */
    public final void removeListener(Consumer<T> stateUpdateListener) {
        stateUpdateListeners.remove(stateUpdateListener);
    }

    private void fireStateUpdatedEvent() {
        stateUpdateListeners.forEach(e -> e.accept(getState()));
    }

    /**
     * @return Returns true while the connection associated with this object is active
     */
    public abstract boolean isActive();

    /**
     * @return Returns the connection timeout duration for this host
     */
    protected Duration getTimeout() {
        return timeout;
    }

    /**
     * @return Returns the serializer currently used by this host
     */
    protected final Serializer<T> getSerializer() {
        return serializer;
    }

    /**
     * @return Returns the serializer currently usd by this host
     */
    protected final Unserializer<T> getUnserializer() {
        return unserializer;
    }

    /**
     * Wraps exceptions thrown from in socket I/O
     */
    public static class RemoteConnectionException extends RuntimeException {
        protected RemoteConnectionException(Exception e) {
            super("Error connecting to remote host: " + e.getMessage(), e);
        }
    }

    /**
     * Thrown when an invalid request is received by this host
     */
    public static class InvalidRequestException extends RuntimeException {
        protected InvalidRequestException(String s) {
            super("Invalid request received: " + s);
        }
    }
}

package util.net;

import util.io.Serializer;
import util.io.Unserializer;

import java.io.IOException;
import java.net.Socket;
import java.time.Instant;

/**
 * This class provides a simple implementation of a client that connects to a server with a given server name and port.
 * <p>
 * The client can request changes to the state, but cannot modify the state directly unless instructed to do so by the server.
 *
 * @param <T> The type of variable used to represent network shared state.
 * @author Created by th174 on 4/1/2017.
 * @see Request,Modifier,ObservableServer,ObservableServer.ServerThread,ObservableClient,ObservableHost,AbstractObservableHost, RemoteListener
 */
public class ObservableClient<T> extends ObservableHost<T> {
    private volatile T state;

    /**
     * Creates a client connected to a server located at host:port, and starts listening for requests sent from the server
     *
     * @param host {@inheritDoc}
     * @param port {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    public ObservableClient(int port) throws IOException {
        this(LOCALHOST, port, NO_SERIALIZER, NO_UNSERIALIZER);
    }

    /**
     * Creates a client connected to a server located at host:port, and starts listening for requests sent from the server
     *
     * @param host {@inheritDoc}
     * @param port {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    public ObservableClient(String host, int port) throws IOException {
        this(host, port, NO_SERIALIZER, NO_UNSERIALIZER);
    }

    /**
     * Creates a client connected to a server located at host:port, and starts listening for requests sent from the server
     *
     * @param host         {@inheritDoc}
     * @param port         {@inheritDoc}
     * @param serializer   {@inheritDoc}
     * @param unserializer {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    public ObservableClient(String host, int port, Serializer<T> serializer, Unserializer<T> unserializer) throws IOException {
        super(new Socket(host, port), serializer, unserializer);
    }

    @Override
    public T getState() {
        return state;
    }

    /**
     * Sets the local state to match the network shared state.
     * <p>
     * On clients, this should only ever be called in response to a request from the server. On servers, this should only be called if the request is approved.
     * <p>
     * All observers registered observers are notified when this method is invoked.
     *
     * @param state State on server to be set to new local state
     */
    protected void setState(T state) {
        this.state = state;
        fireStateUpdatedEvent();
    }

    /**
     * Sets the local state to be equal to the new network state.
     *
     * @param newState New state received from remote server.
     */
    public void handle(T newState, Instant timeStamp) {
        setState(newState);
    }

    /**
     * Applies the state modifier to the local state in order to stay in sync with the network state.
     *
     * @param stateModifier State modifier received from remote server.
     * @throws Exception
     */
    public void handle(Modifier<T> stateModifier, Instant timeStamp) {
        try {
            setState(stateModifier.modify(getState()));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}


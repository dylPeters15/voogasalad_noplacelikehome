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
 * @see Request,Modifier,Server,ServerThread,Client,Host,AbstractHost,Listener
 */
public class Client<T> extends AbstractHost<T> {

    /**
     * Creates a client connected to a server located at host:port, and starts listening for requests sent from the server
     *
     * @param host {@inheritDoc}
     * @param port {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    public Client(int port) throws IOException {
        this(LOCALHOST, port, NO_SERIALIZER, NO_UNSERIALIZER);
    }

    /**
     * Creates a client connected to a server located at host:port, and starts listening for requests sent from the server
     *
     * @param host {@inheritDoc}
     * @param port {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    public Client(String host, int port) throws IOException {
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
    public Client(String host, int port, Serializer<T> serializer, Unserializer<T> unserializer) throws IOException {
        super(new Socket(host, port), serializer, unserializer);
        beginListening();
    }

    /**
     * Sets the local state to be equal to the new network state.
     *
     * @param newState New state received from remote server.
     */
    public void handle(T newState, Instant timeStamp) throws Exception {
        setState(newState);
    }

    /**
     * Applies the state modifier to the local state in order to stay in sync with the network state.
     *
     * @param stateModifier State modifier received from remote server.
     * @throws Exception
     */
    public void handle(Modifier<T> stateModifier, Instant timeStamp) throws Exception {
        setState(stateModifier.modify(getState()));
    }
}


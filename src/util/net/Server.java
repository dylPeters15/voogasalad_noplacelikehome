package util.net;

import util.io.Serializer;
import util.io.Unserializer;

import java.net.ServerSocket;
import java.time.Clock;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;

/**
 * This class provides a general server that allows multiple simultaneous client connections.
 * <p>
 * The server creates a child thread listening to each client.
 *
 * @param <T> The type of variable used to represent networked shared state.
 * @author Created by th174 on 4/1/2017.
 * @see Request,Modifier,Server,ServerThread,Client,AbstractHost,Host,Listener
 */
public class Server<T> implements Host<T> {
    private final Collection<ServerThread<T>> childThreads;
    private final Serializer<T> serializer;
    private final Unserializer<T> unserializer;
    private final ServerSocket serverSocket;
    private Instant mostRecentTimeStamp;
    private volatile T state;

    /**
     * Constructs an instance of VoogaServer without serialization
     *
     * @param initialState The initial networked shared state
     * @param port         Port to listen on for new client connections
     * @throws Exception Thrown if ServerSocket could not be created, or if exception is thrown in serialization
     */
    public Server(T initialState, int port) throws Exception {
        this(initialState, port, NO_SERIALIZER, NO_UNSERIALIZER);
    }

    /**
     * Constructs an instance of VoogaServer
     *
     * @param initialState The initial networked shared state
     * @param port         Port to listen on for new client connections
     * @param serializer   Converts the state to a Serializable form, so that it can be sent to the client
     * @param unserializer Converts the Serializable form of the state back into its original form of type T
     * @throws Exception Thrown if ServerSocket could not be created, or if exception is thrown in serialization
     */
    public Server(T initialState, int port, Serializer<T> serializer, Unserializer<T> unserializer) throws Exception {
        this.state = initialState;
        this.serializer = serializer;
        this.mostRecentTimeStamp = Instant.now(Clock.systemUTC());
        this.unserializer = unserializer;
        this.childThreads = new HashSet<>();
        serverSocket = new ServerSocket(port);
    }

    /**
     * Listens for connections from clients.
     * <p>
     * For each client, this method creates a child thread that listens to the client in the background, and the child thread is added to a child thread pool.
     */
    public void beginListening() {
        new Thread(() -> {
            try {
                while (serverSocket.isBound() && !serverSocket.isClosed()) {
                    ServerThread<T> child = new ServerThread<>(this, serverSocket.accept());
                    childThreads.add(child);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public T getState() {
        return state;
    }

    /**
     * Sends a serialized version of the state to all clients.
     *
     * @param state New state object to be serialized and propagated across all servers and clients
     * @return Returns true if state is sent successfully
     */
    @Override
    public synchronized boolean send(T state) {
        childThreads.forEach(e -> e.send(state));
        return isActive();
    }

    /**
     * Sends a modifier to all clients to be applied.
     *
     * @param modifier Request to be applied to the networked state on all clients.
     * @return Returns true if the requests were sent successfully
     */
    @Override
    public synchronized boolean send(Modifier<T> modifier) throws Exception {
        childThreads.forEach(e -> e.send(modifier));
        return isActive();
    }

    @Override
    public synchronized void handle(T newState, Instant timeStamp) throws Exception {
        if (checkTimeStamp(timeStamp)) {
            state = newState;
            send(newState);
        }
    }

    @Override
    public synchronized void handle(Modifier<T> stateModifier, Instant timeStamp) throws Exception {
        if (checkTimeStamp(timeStamp)) {
            state = stateModifier.modify(getState());
            send(stateModifier);
        }
    }

    @Override
    public boolean isActive() {
        return serverSocket.isBound() && !serverSocket.isClosed() && !childThreads.isEmpty();
    }

    @Override
    public Serializer<T> getSerializer() {
        return serializer;
    }

    @Override
    public Unserializer<T> getUnserializer() {
        return unserializer;
    }

    private boolean checkTimeStamp(Instant timestamp) {
        if (timestamp.isAfter(mostRecentTimeStamp)) {
            mostRecentTimeStamp = timestamp;
        }
        return true;
    }
}

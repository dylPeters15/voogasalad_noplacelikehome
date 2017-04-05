package util.net;

import util.io.Serializer;
import util.io.Unserializer;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class provides a general server that allows multiple simultaneous client connections.
 * <p>
 * The server creates a child thread listening to each client.
 *
 * @param <T> The type of variable used to represent networked shared state.
 * @author Created by th174 on 4/1/2017.
 * @see Request,Modifier,ObservableServer,ObservableClient, ObservableHost ,SocketConnection
 */
public class ObservableServer<T> extends ObservableHost<T> {
    private static final int DEFAULT_THREAD_POOL_SIZE = 12;
    private final long heartBeatIntervalMillis;
    private final Collection<ServerDelegate> connections;
    private final ServerSocket serverSocket;
    private final ScheduledExecutorService executor;

    /**
     * Constructs an instance of VoogaServer without serialization
     *
     * @param initialState The initial networked shared state.
     * @param port         Port to listen on for new client connections
     * @throws Exception Thrown if ServerSocket could not be created, or if exception is thrown in serialization
     */
    public ObservableServer(T initialState, int port) throws Exception {
        this(initialState, port, Serializer.NONE, Unserializer.NONE);
    }

    /**
     * Constructs an instance of VoogaServer
     *
     * @param initialState The initial networked shared state.
     * @param port         Port to listen on for new client connections
     * @param serializer   Converts the state to a Serializable form, so that it can be sent to the client
     * @param unserializer Converts the Serializable form of the state back into its original form of type T
     * @throws Exception Thrown if ServerSocket could not be created, or if exception is thrown in serialization
     */
    public ObservableServer(T initialState, int port, Serializer<T> serializer, Unserializer<T> unserializer) throws Exception {
        this(initialState, port, serializer, unserializer, NEVER_TIMEOUT);
    }

    /**
     * Constructs an instance of VoogaServer
     *
     * @param initialState The initial networked shared state.
     * @param port         Port to listen on for new client connections
     * @param serializer   Converts the state to a Serializable form, so that it can be sent to the client
     * @param unserializer Converts the Serializable form of the state back into its original form of type T
     * @param timeout      Timeout duration for all connections to the client
     * @throws Exception Thrown if ServerSocket could not be created, or if exception is thrown in serialization
     */
    public ObservableServer(T initialState, int port, Serializer<T> serializer, Unserializer<T> unserializer, Duration timeout) throws Exception {
        super(serializer, unserializer, timeout);
        setState(initialState);
        setCommitIndex(0);
        this.connections = new HashSet<>();
        this.serverSocket = new ServerSocket(port);
        this.executor = Executors.newScheduledThreadPool(DEFAULT_THREAD_POOL_SIZE);
        heartBeatIntervalMillis = getTimeout().toMillis() / 2;
    }

    /**
     * Listens for connections from clients.
     * <p>
     * For each client, this method creates a child thread that listens to the client in the background, and the child thread is added to a child thread pool.
     */
    @Override
    public void run() {
        executor.scheduleAtFixedRate(this::sendHeartBeatRequest, 0, heartBeatIntervalMillis, TimeUnit.MILLISECONDS);
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                ServerDelegate delegate = new ServerDelegate(serverSocket.accept());
                executor.submit(delegate);
                connections.add(delegate);
            }
        } catch (Exception e) {
            throw new RemoteConnectionException(e);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            executor.shutdown();
        }
    }

    /**
     * Sets the server's local state, then sends the new state to all clients to be applied.
     *
     * @param newState New state to be applied to the local state and send to all clients.
     * @return Returns true if the requests were sent successfully
     */
    public final boolean sendAndApply(T newState) {
        setState(newState);
        setCommitIndex(getCommitIndex() + 1);
        return send(newState);
    }

    /**
     * Applies a modifier to the server's local state, then sends the modifier to all clients to be applied.
     *
     * @param modifier Request to be applied to the networked state on all clients.
     * @return Returns true if the requests were sent successfully
     */
    public final boolean sendAndApply(Modifier<T> modifier) {
        setState(modifier.modify(getState()));
        setCommitIndex(getCommitIndex() + 1);
        return send(modifier);
    }

    @Override
    protected synchronized void handle(T newState) {
        sendAndApply(newState);
    }

    @Override
    protected synchronized void handle(Modifier<T> stateModifier) {
        sendAndApply(stateModifier);
    }

    @Override
    protected boolean handleError() {
        throw new InvalidRequestException("Error");
    }

    @Override
    protected boolean send(Request request) {
        for (Iterator<ServerDelegate> iterator = connections.iterator(); iterator.hasNext(); ) {
            ServerDelegate e = iterator.next();
            if (!e.send(request)) {
                e.shutDown();
                iterator.remove();
            }
        }
        return isActive();
    }

    @Override
    public boolean isActive() {
        return serverSocket.isBound() && !serverSocket.isClosed() && !connections.isEmpty();
    }

    @Override
    protected boolean validateRequest(Request request) {
        return request.getCommitIndex() == getCommitIndex() && !Request.isError(request);
    }

    /**
     * This class is delegated to be the server to listen to a client on a single socket and relays information between the main server and the client.
     *
     * @author Created by th174 on 4/1/2017.
     * @see Request,Modifier,ObservableServer,ObservableClient, ObservableHost ,SocketConnection
     */
    protected class ServerDelegate implements Runnable {
        private final SocketConnection connection;

        /**
         * @param socket Socket to listen on for client requests.
         * @throws IOException Thrown if socket is not open for reading and writing, or if an exception is thrown in serialization
         */
        public ServerDelegate(Socket socket) throws IOException {
            connection = new SocketConnection(socket, ObservableServer.this.getTimeout());
            System.out.println("Client connected:\t" + connection);
        }

        @Override
        public void run() {
            send(getHeartBeatRequest());
            connection.listen(this::handleRequest);
        }

        private boolean handleRequest(Request<? extends Serializable> request) {
            if (Request.isHeartbeat(request)) {
                System.out.println("Heartbeat Received: " + request);
                return true;
            } else if (!validateRequest(request)) {
                return send(getRequest(getState()));
            } else return ObservableServer.this.handleRequest(request);
        }

        protected boolean send(Request request) {
            return connection.send(request);
        }

        private void shutDown() {
            connection.shutDown();
        }
    }
}

package util.net;

import util.io.Serializer;

import java.net.ServerSocket;
import java.net.Socket;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class provides a general server that allows multiple simultaneous client connections.
 * <p>
 * The server creates a child thread listening to each client.
 *
 * @param <T> The type of variable used to represent networked shared state.
 * @author Created by th174 on 4/1/2017.
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote
 */
public class VoogaServer<T> implements VoogaRemote<T> {
    public static final Predicate ALWAYS_VALID = voogaRequest -> true;
    private final T state;
    private final List<VoogaServerThread<T>> childThreads = new ArrayList<>();
    private final Serializer<T> stateSerializer;
    private final ServerSocket serverSocket;
    private Predicate<VoogaRequest<T>> requestValidator;
    private Instant mostRecentTimeStamp;

    /**
     * @param initialState    The initial networked shared state
     * @param stateSerializer Converts the initial state to a Serializable form, so that it can be sent to the client
     * @param port            Port to listen on for new client connections
     * @throws Exception Thrown if ServerSocket could not be created, or if exception is thrown in serialization
     */
    public VoogaServer(T initialState, Serializer<T> stateSerializer, int port) throws Exception {
        this(initialState, stateSerializer, port, (Predicate<VoogaRequest<T>>) ALWAYS_VALID);
    }

    /**
     * @param initialState     The initial networked shared state
     * @param stateSerializer  Converts the initial state to a Serializable form, so that it can be sent to the client
     * @param port             Port to listen on for new client connections
     * @param requestValidator Predicate that checks whether requests are valid, default always valid
     * @throws Exception Thrown if ServerSocket could not be created, or if exception is thrown in serialization
     */
    public VoogaServer(T initialState, Serializer<T> stateSerializer, int port, Predicate<VoogaRequest<T>> requestValidator) throws Exception {
        this.state = initialState;
        this.stateSerializer = stateSerializer;
        this.mostRecentTimeStamp = Instant.now(Clock.systemUTC());
        this.requestValidator = requestValidator;
        serverSocket = new ServerSocket(port);
    }

    /**
     * Listens for connections from clients.
     * <p>
     * For each client, this method creates a child thread that listens to the client in the background, and the child thread is added to a child thread pool.
     *
     * @throws Exception Thrown if server cannot open socket to connect to client
     */
    public void listenForClients() {
        new Thread(() -> {
            try {
                while (true) {
                    VoogaServerThread<T> child = new VoogaServerThread<>(this, serverSocket.accept(), state, stateSerializer);
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
     * Accepts and applies request if the request is valid, as determined by validateRequest(VoogaRequest)
     *
     * @param request Request received from client
     * @see this#validateReqeust(VoogaRequest)
     */
    @Override
    public void handleRequest(VoogaRequest<T> request) {
        if (validateReqeust(request)) {
            mostRecentTimeStamp = request.getTimeStamp();
            request.modify(state);
            sendRequest(request);
        }
    }

    /**
     * @return Timestamp of most recently accepted request
     */
    public Instant getMostRecentTimeStamp() {
        return mostRecentTimeStamp;
    }

    /**
     * Checks the request for validity.
     * <p>
     * The request is valid if it is more recent that the previous received request and the specified requestValidator returns true
     *
     * @param request Reqeust to be validated
     * @return Returns true if the new request is more recent that the most recent valid request.
     */
    protected boolean validateReqeust(VoogaRequest<T> request) {
        return request.getTimeStamp().isAfter(getMostRecentTimeStamp()) && requestValidator.test(request);
    }

    /**
     * @return Returns socket of first child thread
     */
    @Override
    public Socket getSocket() {
        return childThreads.get(0).getSocket();
    }

    /**
     * Sends a request to all clients to be applied.
     *
     * @param request Request to be applied to the networked state on all clients.
     * @return Returns true if the requests were sent successfully
     */
    @Override
    public boolean sendRequest(VoogaRequest<T> request) {
        childThreads.removeIf(e -> !e.sendRequest(request));
        return !childThreads.isEmpty();
    }
}

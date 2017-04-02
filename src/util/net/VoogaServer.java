package util.net;

import util.io.Serializer;

import java.io.IOException;
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
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote
 */
public class VoogaServer<T> implements VoogaRemote<T> {
    private final T state;
    private final Collection<VoogaServerThread<T>> childThreads = new HashSet<>();
    private final Serializer<T> stateSerializer;
    private Instant mostRecentTimeStamp;

    /**
     * @param initialState Initial starting state. Should be identical to the initial state on all clients.
     */
    public VoogaServer(T initialState, Serializer<T> stateSerializer) {
        this.state = initialState;
        this.stateSerializer = stateSerializer;
        this.mostRecentTimeStamp = Instant.now(Clock.systemUTC());
    }

    /**
     * @param port Port to listen to client connections on
     * @throws IOException Thrown if server cannot open socket to connect to client
     */
    public void listenForClients(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            VoogaServerThread<T> child = new VoogaServerThread<>(this, serverSocket.accept(), state, stateSerializer);
            childThreads.add(child);
        }
    }

    /**
     * Accepts and applies request if the request is valid, as determined by validateRequest(VoogaRequest)
     *
     * @param request Request received from client
     * @see this#validateReqeust(VoogaRequest)
     */
    protected void readRequest(VoogaRequest<T> request) {
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
     * Checks the request for validity. The request is valid if it is more recent that the previous received request.
     * Extend this method to do additional checks for validity.
     *
     * @param request Reqeust to be v
     * @return Returns true if the new request is more recent that the most recent request.
     */
    protected boolean validateReqeust(VoogaRequest<T> request) {
        return request.getTimeStamp().isAfter(getMostRecentTimeStamp());
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

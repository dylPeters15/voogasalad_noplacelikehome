package util.net;

import util.io.Serializer;

import java.io.Serializable;
import java.net.Socket;

/**
 * This class listens to a client on a single socket and relays information between the main server and the client.
 *
 * @param <T> The type of variable used to represent networked shared state.
 * @author Created by th174 on 4/1/2017.
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote
 */
public class VoogaServerThread<T> extends VoogaRemote<T> {
    private final VoogaServer<T> parentServer;

    /**
     * @param parentServer    Parent server creating this thread.
     * @param socket          Socket to listen on for client requests.
     * @param initialState    Initialstate to be sent to the client.
     * @param stateSerializer Converts the initial state to serializable form, so it can be sent to the client
     * @throws Exception Thrown if socket is not open for reading and writing, or if exception thrown in serialization
     */
    public VoogaServerThread(VoogaServer<T> parentServer, Socket socket, T initialState, Serializer<T> stateSerializer) throws Exception {
        super(socket);
        this.parentServer = parentServer;
        sendRequest(stateSerializer.serialize(initialState));
        beginListening();
    }

    /**
     * Sends request to parent server to be handled
     *
     * @param request Incoming request from client
     */
    @Override
    public void handleRequest(Serializable request) {
        parentServer.handleRequest(request);
        setState(parentServer.getState());
    }
}

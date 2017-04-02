package util.net;

import util.io.Serializer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class listens to a client on a single socket and relays information between the main server and the client.
 *
 * @param <T> The type of variable used to represent networked shared state.
 * @author Created by th174 on 4/1/2017.
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote
 */
public class VoogaServerThread<T> implements VoogaRemote<T> {
    private final ObjectOutputStream outputToClient;
    private final Socket socket;
    private final VoogaServer<T> parentServer;

    /**
     * @param parentServer    Parent server creating this thread.
     * @param socket          Socket to listen on for client requests.
     * @param initialState    Initialstate to be sent to the client.
     * @param stateSerializer Converts the initial state to serializable form, so it can be sent to the client
     * @throws Exception Thrown if socket is not open for reading and writing, or if exception thrown in serialization
     */
    public VoogaServerThread(VoogaServer<T> parentServer, Socket socket, T initialState, Serializer<T> stateSerializer) throws Exception {
        this.socket = socket;
        this.parentServer = parentServer;
        this.outputToClient = new ObjectOutputStream(socket.getOutputStream());
        outputToClient.flush();
        ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
        outputToClient.writeObject(stateSerializer.serialize(initialState));
        new Listener<>(inputFromClient, this::handleRequest).start();
    }

    @Override
    public T getState() {
        return parentServer.getState();
    }

    /**
     * Sends request to parent server to be handled
     *
     * @param request Incoming request from client
     */
    @Override
    public void handleRequest(VoogaRequest<T> request) {
        parentServer.handleRequest(request);
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    /**
     * Sends request to a client server through a socket.
     *
     * @param request Request sent to client to be applied.
     * @return Returns true if the request was sent successfully
     */
    @Override
    public boolean sendRequest(VoogaRequest<T> request) {
        return writeRequestTo(request, outputToClient);
    }
}

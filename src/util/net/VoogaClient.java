package util.net;

import util.io.Unserializer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * This class provides a simple implementation of a client that connects to a server with a given server name and port.
 * The client can send and listen to request, but cannot modify the state directly unless instructed to do so by the server.
 *
 * @param <T> The type of variable used to represent network shared state.
 * @author Created by th174 on 4/1/2017.
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote
 */
public class VoogaClient<T> implements VoogaRemote<T> {
    private final T state;
    private final ObjectOutputStream outputToServer;
    private final Socket socket;

    /**
     * Creates a client connected to a server located at host:port
     *
     * @param host         Hostname of the server
     * @param port         Port on the server to connect to
     * @param unserializer Unserializes state
     * @throws Exception Thrown if server is not found, or if socket is not open for writing, or if data received from server cannot be unserialized.
     */
    public VoogaClient(String host, int port, Unserializer<T> unserializer) throws Exception {
        socket = new Socket(host, port);
        this.outputToServer = new ObjectOutputStream(socket.getOutputStream());
        this.outputToServer.flush();
        ObjectInputStream inputFromServer = new ObjectInputStream(socket.getInputStream());
        this.state = unserializer.unserialize((Serializable) inputFromServer.readObject());
        new Listener<>(inputFromServer, this::handleRequest).start();
    }

    @Override
    public T getState() {
        return state;
    }

    /**
     * Applies request sent from server
     *
     * @param request Incoming request from server
     */
    @Override
    public void handleRequest(VoogaRequest<T> request) {
        request.modify(state);
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    /**
     * Sends a request proposal to the server. If the request is accepted, the change will be propagated to all clients.
     *
     * @param request Request to be applied to the networked state if accepted.
     * @return Returns true if request sent successfully
     */
    @Override
    public boolean sendRequest(VoogaRequest<T> request) {
        return writeRequestTo(request, outputToServer);
    }
}

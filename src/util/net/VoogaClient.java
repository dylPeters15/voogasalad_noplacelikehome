package util.net;

import util.io.Unserializer;

import java.io.Serializable;
import java.net.Socket;

/**
 * This class provides a simple implementation of a client that connects to a server with a given server name and port.
 * <p>
 * The client can send and listen to request, but cannot modify the state directly unless instructed to do so by the server.
 *
 * @param <T> The type of variable used to represent network shared state.
 * @author Created by th174 on 4/1/2017.
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote,Remote
 */
public class VoogaClient<T> extends VoogaRemote<T> {
    private final Unserializer<T> unserializer;

    /**
     * Creates a client connected to a server located at host:port
     *
     * @param host         Hostname of the server
     * @param port         Port on the server to connect to
     * @param unserializer Unserializes state
     * @throws Exception Thrown if server is not found, or if socket is not open for writing, or if data received from server cannot be unserialized.
     */
    public VoogaClient(String host, int port, Unserializer<T> unserializer) throws Exception {
        super(new Socket(host, port));
        this.unserializer = unserializer;
        beginListening();
    }


    /**
     * Applies request sent from server
     * <p>
     * A request sent from the server has been approved, and all clients must apply the request
     *
     * @param request Incoming request from server
     */
    @Override
    public synchronized void handleRequest(Serializable request) {
        if (request instanceof VoogaRequest) {
            setState(((VoogaRequest<T>) request).modify(getState()));
        } else {
            try {
                setState(unserializer.unserialize(request));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

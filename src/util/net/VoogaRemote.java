package util.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * This class provides a basic implementation for a client or server communicating over TCP/IP.
 * <p>
 * It can send and receive requests from a remote host and modifies a network shared state.
 *
 * @param <T> The type of variable used to represent network shared state.
 * @author Created by th174 on 4/2/2017.
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote,Remote
 */
public abstract class VoogaRemote<T> implements Remote<T> {
    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private volatile T state;

    /**
     * Creates a new VoogaRemote server or client that listens on a socket for requests.
     *
     * @param socket Socket to listen on for client requests.
     * @throws IOException Thrown if socket is not open for reading and writing
     */
    public VoogaRemote(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.outputStream.flush();
    }

    /**
     * Creates a separate thread to being listening to input sent over the network. When input is found, handleRequest is called on it.
     *
     * @throws IOException Thrown in socket is not open for listening
     */
    protected void beginListening() throws IOException {
        new Listener(socket, this::handleRequest).start();
    }

    @Override
    public T getState() {
        return state;
    }

    @Override
    public boolean isActive() {
        return getSocket().isConnected() && !getSocket().isClosed() && getSocket().isBound();
    }

    /**
     * Sets the local state to match the network shared state.
     * <p>
     * On clients, this should only ever be called in response to a request from the server. On servers, this should only be called if the request is approved.
     *
     * @param state State on server to be set to new local state
     */
    protected void setState(T state) {
        this.state = state;
    }

    /**
     * @return Returns the socket this VoogaRemote is operating on
     */
    protected Socket getSocket() {
        return socket;
    }

    @Override
    public abstract void handleRequest(Serializable request);

    public boolean sendRequest(Serializable request) {
        if (!isActive()) {
            return false;
        }
        try {
            outputStream.writeObject(request);
            return true;
        } catch (IOException e) {
            try {
                getSocket().close();
                System.out.println("Connection Closed: " + getSocket());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }
}

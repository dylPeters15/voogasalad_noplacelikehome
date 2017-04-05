package util.net;

import util.io.Serializer;
import util.io.Unserializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;

/**
 * This class provides a basic implementation for a host client or server communicating over TCP/IP.
 * <p>
 * It can send and receive requests from a remote host and modifies a network shared state.
 *
 * @param <T> The type of variable used to represent network shared state.
 * @author Created by th174 on 4/2/2017.
 * @see Request,Modifier,ObservableServer, ObservableServer.ClientConnection ,ObservableClient,ObservableHost,AbstractObservableHost,RemoteListener
 */
public abstract class ObservableHost<T> extends AbstractObservableHost<T> {
    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private volatile int commitIndex;

    /**
     * Creates a new VoogaRemote server or client that listens on a socket for requests.
     *
     * @param socket Socket to listen on for client requests.
     * @throws IOException Thrown if socket is not open for reading and writing.
     */
    public ObservableHost(Socket socket) throws Exception {
        this(socket, Serializer.NONE, Unserializer.NONE, NEVER_TIMEOUT);
    }

    /**
     * Creates a new VoogaRemote server or client that listens on a socket for requests.
     *
     * @param socket       Socket to listen on for client requests.
     * @param serializer   Converts a state of type T into a Serializable form to be sent over the network.
     * @param unserializer Converts a Serializable form of a state into type T.
     * @param timeout      {@inheritDoc}
     * @throws IOException Thrown if socket is not open for reading and writing.
     */
    public ObservableHost(Socket socket, Serializer<T> serializer, Unserializer<T> unserializer, Duration timeout) throws IOException {
        super(serializer, unserializer, timeout);
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.outputStream.flush();
        getSocket().setSoTimeout((int) getTimeout().toMillis());
    }

    /**
     * Creates a separate thread to being listening to input sent over the network. When a request is found, handle is called on the content of the request.
     */
    @Override
    public void run() {
        submitTask(new RemoteListener(socket, this::handleRequest));
    }

    @Override
    protected boolean validateRequest(Request<?> incomingRequest) {
        if (incomingRequest.getCommitIndex() >= this.commitIndex) {
            commitIndex = incomingRequest.getCommitIndex();
            return true;
        }
        return false;
    }

    @Override
    protected int getCommitIndex() {
        return commitIndex;
    }

    @Override
    public boolean isActive() {
        return getSocket().isConnected() && !getSocket().isClosed() && getSocket().isBound();
    }

    /**
     * @return Returns the socket this VoogaRemote is operating on
     */
    protected Socket getSocket() {
        return socket;
    }

    /**
     * This method is called when a new request is received from the remote host.
     * <p>
     * It delegates handling of the new request to other handle methods.
     *
     * @param request Request received from remote host.
     * @throws RuntimeException Thrown when an error occurs while unserializing the request, or when an error occurs in processing the request.
     */
    protected final synchronized void handleRequest(Request<? extends Serializable> request) {
        if (validateRequest(request)) {
            if (request.get().equals(Request.HEARTBEAT)) {
                handleHeartBeat();
            } else if (Modifier.class.isAssignableFrom(request.getContentType())) {
                handleAndNotify((Modifier<T>) request.get(), request.getTimeStamp());
            } else {
                handleAndNotify(getUnserializer().unserialize(request.get()), request.getTimeStamp());
            }
        } else {
            throw new InvalidRequestException(request.toString());
        }
    }

    @Override
    protected abstract void handle(Modifier<T> modifier, Instant timeStamp);

    @Override
    protected abstract void handle(T state, Instant timeStamp);

    /**
     * This method is invoked when a heartbeat is received from the remote host.
     */
    protected abstract void handleHeartBeat();

    /**
     * Sends a request to the remote host through the socket
     *
     * @param request Request to be send to remote host
     * @return Returns true if request was sent successfully
     */
    protected synchronized boolean sendRequest(Request<? extends Serializable> request) {
        if (!isActive()) {
            shutDown();
            return false;
        }
        try {
            outputStream.writeObject(request);
            return true;
        } catch (IOException e) {
            throw new RemoteConnectionException(e);
        }
    }

    @Override
    public boolean send(Modifier<T> modifier) {
        try {
            return sendRequest(new Request<>(modifier, getCommitIndex()));
        } catch (RemoteConnectionException e) {
            shutDown();
            return false;
        }
    }

    @Override
    public boolean send(T state) {
        return sendRequest(new Request<>(getSerializer().serialize(state), getCommitIndex()));
    }

    protected final boolean sendHeartBeat() {
        try {
            return sendRequest(Request.heartbeatRequest(getCommitIndex()));
        } catch (RemoteConnectionException e) {
            shutDown();
            return false;
        }
    }

    @Override
    public void shutDown() {
        try {
            socket.close();
            System.out.println("Connection closed @ " + getSocket());
        } catch (IOException e) {
            throw new RemoteConnectionException(e);
        }
        super.shutDown();
    }
}

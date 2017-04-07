package util.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.time.Duration;
import java.util.function.Consumer;

/**
 * This class provides a interface to for hosts to communicate by sending and receiving requests over a socket.
 * <p>
 * It can listen to a socket's input stream and send requests to a socket's output stream.
 *
 * @author Created by th174 on 4/5/2017.
 * @see Request,Modifier,ObservableServer,ObservableServer.ServerDelegate,ObservableClient,ObservableHost
 */
public class SocketConnection {
    private final Socket socket;
    private final ObjectOutputStream outputStream;

    /**
     * Creates a socket connection from a socket
     *
     * @param socket  Socket that this connection is attached to
     * @param timeout Duration to wait for activity on the socket before it times out
     */
    public SocketConnection(Socket socket, Duration timeout) {
        try {
            this.socket = socket;
            this.socket.setSoTimeout((int) timeout.toMillis());
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.outputStream.flush();
        } catch (Exception e) {
            throw new ObservableHost.RemoteConnectionException(e);
        }
    }

    /**
     * Continuously listens for requests sent over the socket, and handles them with a request handler
     *
     * @param requestHandler Consumer that handles requests through the socket
     */
    public void listen(Consumer<Request> requestHandler) {
        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            while (isActive()) {
                Request request = (Request<? extends Serializable>) inputStream.readObject();
                requestHandler.accept(request);
            }
        } catch (IOException e) {
        } catch (Exception e) {
            throw new ObservableHost.RemoteConnectionException(e);
        } finally {
            shutDown();
        }
    }

    /**
     * Sends a request through the socket
     *
     * @param request Request to be sent through the socket
     * @return Returns true if the request was sent successfully
     */
    public synchronized boolean send(Request request) {
        try {
            outputStream.writeObject(request);
            return isActive();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Closes the connection
     */
    public void shutDown() {
        try {
            socket.close();
            System.out.println("Connection closed: " + socket);
        } catch (IOException e) {
            throw new ObservableHost.RemoteConnectionException(e);
        }
    }

    /**
     * @return Returns true if this connection is currently active
     */
    public boolean isActive() {
        return socket.isConnected() && !socket.isClosed() && !socket.isInputShutdown() && !socket.isOutputShutdown() && socket.isBound();
    }

    @Override
    public String toString() {
        return "Connection on " + socket;
    }
}


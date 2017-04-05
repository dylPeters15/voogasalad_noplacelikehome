package util.net;

import util.net.AbstractObservableHost.RemoteConnectionException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.function.Consumer;

/**
 * This class listens to sent over a socket requests in a background thread, and handles each request with a specified Consumer.
 *
 * @author Created by th174 on 4/1/2017.
 * @see Request,Modifier,ObservableServer, ObservableServer.ClientConnection ,ObservableClient,ObservableHost,AbstractObservableHost,RemoteListener
 */
public class RemoteListener extends Thread {
    private final Consumer<Request> requestHandler;
    private final Socket socket;
    private final ObjectInputStream inputStream;

    /**
     * @param socket         Bound socket to listen to requests on.
     * @param requestHandler Consumer that accepts each incoming request.
     */
    public RemoteListener(Socket socket, Consumer<Request> requestHandler) {
        this.socket = socket;
        try {
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RemoteConnectionException(e);
        }
        this.requestHandler = requestHandler;
    }

    /**
     * Continuously listens for requests, and handles them with a Consumer requestHandler
     */
    @Override
    public void run() {
        try {
            while (socket.isConnected() && socket.isBound() && !socket.isClosed()) {
                requestHandler.accept((Request) inputStream.readObject());
            }
        } catch (SocketTimeoutException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                throw new RemoteConnectionException(e1);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RemoteConnectionException(e);
        }
    }
}
package util.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.time.Duration;
import java.util.function.Consumer;

/**
 * @author Created by th174 on 4/5/2017.
 */
public class SocketConnection {
    private final Socket socket;
    private final ObjectOutputStream outputStream;

    public SocketConnection(Socket socket, Duration timeout) throws ObservableHostBase.RemoteConnectionException {
        try {
            this.socket = socket;
            this.socket.setSoTimeout((int) timeout.toMillis());
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.outputStream.flush();
        } catch (Exception e) {
            throw new ObservableHostBase.RemoteConnectionException(e);
        }
    }

    public void listen(Consumer<Request> requestHandler) {
        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            while (isActive()) {
                Request request = (Request<? extends Serializable>) inputStream.readObject();
                requestHandler.accept(request);
            }
        } catch (SocketException e) {
        } catch (Exception e) {
            throw new ObservableHostBase.RemoteConnectionException(e);
        } finally {
            shutDown();
        }
    }

    public synchronized boolean send(Request request) {
        try {
            outputStream.writeObject(request);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void shutDown() {
        try {
            socket.close();
            System.out.println("Connection closed: " + socket);
        } catch (IOException e) {
            throw new ObservableHostBase.RemoteConnectionException(e);
        }
    }

    public boolean isActive() {
        return socket.isConnected() && !socket.isClosed() && !socket.isInputShutdown() && !socket.isOutputShutdown() && socket.isBound();
    }

    @Override
    public String toString() {
        return "Connection on " + socket;
    }
}


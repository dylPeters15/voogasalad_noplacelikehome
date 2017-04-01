package backend.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Created by th174 on 4/1/2017.
 */
public class VoogaServer<T> implements VoogaRemote<T> {
    private final T gameState;
    private boolean isListening;
    private Collection<VoogaServerThread<T>> childThreads = new ArrayList<>();

    public VoogaServer(T gameState) {
        this.gameState = gameState;
    }

    public void listenForClients(int port) throws IOException, VoogaConnectionException {
        isListening = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (isListening) {
                VoogaServerThread<T> child = new VoogaServerThread<>(this, serverSocket.accept());
                child.start();
                childThreads.add(child);
            }
        } catch (Exception e) {
            throw new VoogaConnectionException(e);
        } finally {
            isListening = false;
        }
    }

    public boolean isListening() {
        return isListening;
    }

    void readMessage(Message<T> message) {
        message.execute(gameState);
        sendMessage(message);
    }

    @Override
    public void sendMessage(Message<T> message) {
        childThreads.forEach(e -> e.sendMessage(message));
    }

    private class VoogaConnectionException extends Exception {
        VoogaConnectionException(Exception e) {
            super(e);
        }
    }
}

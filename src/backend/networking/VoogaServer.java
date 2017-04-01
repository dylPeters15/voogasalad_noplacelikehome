package backend.networking;

import backend.util.GameState;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Created by th174 on 4/1/2017.
 */
public class VoogaServer implements VoogaRemote {
    private final GameState gameState;
    private boolean isListening;
    private Collection<VoogaServerThread> childThreads = new ArrayList<>();

    public VoogaServer(GameState gameState) {
        this.gameState = gameState;
    }

    public void listenForClients(int port) throws IOException, VoogaConnectionException {
        isListening = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (isListening) {
                VoogaServerThread child = new VoogaServerThread(this, serverSocket.accept());
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

    void readMessage(Message message) {
        message.execute(gameState);
        sendMessage(message);
    }

    @Override
    public void sendMessage(Message message) {
        childThreads.forEach(e -> e.sendMessage(message));
    }

    private class VoogaConnectionException extends Exception {
        VoogaConnectionException(Exception e) {
            super(e);
        }
    }
}

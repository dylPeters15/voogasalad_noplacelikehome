package backend.networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Created by th174 on 4/1/2017.
 */
public class VoogaClient<T> implements VoogaRemote<T> {
    private final T gameState;
    private ObjectOutputStream outputToServer;

    public VoogaClient(String serverName, int port, T gameState) throws IOException {
        this.gameState = gameState;
        Socket socket = new Socket(serverName, port);
        this.outputToServer = new ObjectOutputStream(socket.getOutputStream());
        new Listener<>(socket, this::handleMessage).start();
    }

    private void handleMessage(Message<T> message) {
        message.execute(gameState);
    }

    public void sendMessage(Message<T> out) {
        VoogaRemote.sendTo(out, outputToServer);
    }
}

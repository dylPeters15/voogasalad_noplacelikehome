package backend.networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Created by th174 on 4/1/2017.
 */
class VoogaServerThread extends VoogaRemote.Listener implements VoogaRemote {
    private final ObjectOutputStream outputToClient;

    VoogaServerThread(VoogaServer parentServer, Socket socket) throws IOException {
        super(socket, parentServer::readMessage);
        this.outputToClient = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void sendMessage(Message out) {
        VoogaRemote.sendTo(out, outputToClient);
    }
}

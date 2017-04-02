package util.net;

import util.io.XMLSerializable;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * This class provides a simple implementation of a client that connects to a server with a given server name and port.
 * The client can send and listen to request, but cannot modify the state directly unless instructed to do so by the server.
 *
 * @param <T> The type of variable used to represent network shared state.
 * @author Created by th174 on 4/1/2017.
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote
 */
public class VoogaClient<T extends XMLSerializable> implements VoogaRemote<T> {
    private final T state;
    private final ObjectOutputStream outputToServer;
    private final Socket socket;

    /**
     * Creates a client connected to a server located at host:port
     *
     * @param host Hostname of the server
     * @param port Port on the server to connect to
     * @throws IOException                  Thrown if server is not found, or if socket is not open for writing.
     * @throws XMLSerializable.XMLException Thrown if the client was unable to parse the initial state sent by the server
     */
    public VoogaClient(String host, int port) throws IOException, XMLSerializable.XMLException {
        socket = new Socket(host, port);
        this.outputToServer = new ObjectOutputStream(socket.getOutputStream());
        this.state = (T) XMLSerializable.createFromXML(new DataInputStream(socket.getInputStream()).readUTF());
        new Listener<>(socket, this::handleRequest).start();
    }

    /**
     * @return Address of server that this client is bound to
     */
    public InetAddress getAddress() {
        return socket.getInetAddress();
    }


    private void handleRequest(VoogaRequest<T> request) {
        request.modify(state);
    }

    /**
     * Sends a request proposal to the server. If the request is accepted, the change will be propagated to all clients.
     *
     * @param request Request to be applied to the networked state if accepted.
     * @return Returns true if request sent successfully
     */
    @Override
    public boolean sendRequest(VoogaRequest<T> request) {
        try {
            outputToServer.writeObject(request);
            return true;
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }
}

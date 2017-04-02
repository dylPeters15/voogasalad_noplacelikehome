package util.net;

import util.io.Serializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class listens to a client on a single socket and relays information between the main server and the client.
 *
 * @param <T> The type of variable used to represent networked shared state.
 * @author Created by th174 on 4/1/2017.
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote
 */
public class VoogaServerThread<T> extends VoogaRemote.Listener<T> implements VoogaRemote<T> {
    private final ObjectOutputStream outputToClient;

    /**
     * @param parentServer Parent server creating this thread.
     * @param socket       Socket to listen on for client requests.
     * @param initialState Initialstate to be sent to the client.
     * @throws Exception Thrown if socket is not open for reading and writing, or if exception thrown in serialization
     */
    public VoogaServerThread(VoogaServer<T> parentServer, Socket socket, T initialState, Serializer<T> stateSerializer) throws Exception {
        super(socket, parentServer::readRequest);
        ObjectOutputStream initialStateOutput = new ObjectOutputStream(socket.getOutputStream());
        initialStateOutput.writeObject(stateSerializer.serialize(initialState));
        this.outputToClient = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    /**
     * Sends request to a client server through a socket.
     *
     * @param request Request sent to client to be applied.
     * @return Returns true if the request was sent successfully
     */
    @Override
    public boolean sendRequest(VoogaRequest<T> request) {
        try {
            outputToClient.writeObject(request);
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

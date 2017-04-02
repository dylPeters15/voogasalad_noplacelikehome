package util.net;

import java.net.Socket;
import java.time.Instant;

/**
 * This class listens to a client on a single socket and relays information between the main server and the client.
 *
 * @param <T> The type of variable used to represent networked shared state.
 * @author Created by th174 on 4/1/2017.
 * @see Request,Modifier,Server,ServerThread,Client,AbstractHost,Host,Listener
 */
public class ServerThread<T> extends AbstractHost<T> {
    private final Server<T> parentServer;

    /**
     * @param parentServer Parent server creating this thread.
     * @param socket       Socket to listen on for client requests.
     * @throws Exception Thrown if socket is not open for reading and writing, or if an exception is thrown in serialization
     */
    public ServerThread(Server<T> parentServer, Socket socket) throws Exception {
        super(socket, parentServer.getSerializer(), parentServer.getUnserializer());
        this.parentServer = parentServer;
        send(parentServer.getState());
        beginListening();
    }

    @Override
    public void handle(Modifier<T> modifier, Instant timeStamp) throws Exception {
        parentServer.handle(modifier, timeStamp);
    }

    @Override
    public void handle(T state, Instant timeStamp) throws Exception {
        parentServer.handle(state, timeStamp);
    }
}

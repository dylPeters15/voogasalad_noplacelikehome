package util.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * This class listens to sent over a socket requests in a background thread, and handles each request with a specified Consumer.
 *
 * @author Created by th174 on 4/1/2017.
 * @see Request,Modifier,ObservableServer,ObservableServer.ServerThread,ObservableClient,ObservableHost,AbstractObservableHost,Listener
 */
public class Listener extends Thread {
    private final Consumer<Request> requestHandler;
    private final Socket socket;
    private final ObjectInputStream inputStream;

    /**
     * @param socket         Bound socket to listen to requests on.
     * @param requestHandler Consumer that accepts each incoming request.
     * @throws IOException Thrown if socket input is closed.
     */
    public Listener(Socket socket, Consumer<Request> requestHandler) throws IOException {
        this.socket = socket;
        this.inputStream = new ObjectInputStream(socket.getInputStream());
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
        } catch (IOException | ClassNotFoundException e) {
        } finally {
            try {
                socket.close();
                System.out.println("\nConnection closed:\t" + socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
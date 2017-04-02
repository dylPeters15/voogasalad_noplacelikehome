package util.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * This interface provides a basic framework for communications between networked.
 *
 * @param <T> The type of state modified in the request.
 * @author Created by th174 on 4/1/2017.
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote
 */
public interface VoogaRemote<T> {
    /**
     * @return Returns local instance of the network shared state. Should be the same on all clients and servers.
     */
    T getState();

    /**
     * @return Returns the socket this Object is operating on
     */
    Socket getSocket();

    /**
     * Handle incoming request sent over network
     *
     * @param request Incoming request to be handled
     */
    void handleRequest(VoogaRequest<T> request);

    /**
     * Utility method to write request to output stream and handle failures
     *
     * @param request Request to be written
     * @param output  OutputStream to write request to
     * @return Returns true if the request is written successfully
     */
    default boolean writeRequestTo(VoogaRequest<T> request, ObjectOutputStream output) {
        try {
            output.writeObject(request);
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

    /**
     * Sends a request through a socket's output stream.
     *
     * @param request Request to be applied to the networked state if accepted.
     * @return Returns true if the request was sent successfully
     */
    boolean sendRequest(VoogaRequest<T> request);

    /**
     * This class listens to requests in a background thread, and handles each request with a specified Consumer.
     *
     * @param <T> The type of state modified in the request.
     */
    class Listener<T> extends Thread {
        private final Consumer<VoogaRequest<T>> requestHandler;
        private ObjectInputStream inputStream;

        /**
         * @param inputStream    inputStream to listen to requests on.
         * @param requestHandler Consumer that accepts each incoming request.
         * @throws IOException Thrown if socket input is closed.
         */
        public Listener(ObjectInputStream inputStream, Consumer<VoogaRequest<T>> requestHandler) throws IOException {
            this.inputStream = inputStream;
            this.requestHandler = requestHandler;
        }

        /**
         * Continuously listens for requests
         */
        @Override
        public void run() {
            try {
                while (true) {
                    requestHandler.accept((VoogaRequest<T>) inputStream.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

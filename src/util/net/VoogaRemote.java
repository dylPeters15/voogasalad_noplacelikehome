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
     * @return Returns the socket this Object is operating on
     */
    Socket getSocket();

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
        private final Socket socket;
        private final Consumer<VoogaRequest<T>> requestHandler;
        private ObjectInputStream inputStream;

        /**
         * @param socket         Socket to listen on for requests.
         * @param requestHandler Consumer that accepts each incoming request.
         * @throws IOException Thrown if socket input is closed.
         */
        public Listener(Socket socket, Consumer<VoogaRequest<T>> requestHandler) throws IOException {
            this.socket = socket;
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.requestHandler = requestHandler;
        }

        /**
         * @return Returns address being listened to
         */
        protected Socket getSocket() {
            return socket;
        }

        /**
         * Continuously listens for requests
         */
        @Override
        public void run() {
            try {
                while (!socket.isInputShutdown()) {
                    requestHandler.accept((VoogaRequest<T>) inputStream.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
            } finally {
                try {
                    socket.close();
                    System.out.println("Connection Closed: " + socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

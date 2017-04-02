package util.net;

import util.io.XMLSerializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * This interface provides a basic framework for communications between networked.
 *
 * @param <T> The type of state modified in the request.
 * @author Created by th174 on 4/1/2017.
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote
 */
public interface VoogaRemote<T extends XMLSerializable> {

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
    class Listener<T extends XMLSerializable> extends Thread {
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
        public Socket getSocket() {
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

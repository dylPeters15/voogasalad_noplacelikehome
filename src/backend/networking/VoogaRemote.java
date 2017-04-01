package backend.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * @author Created by th174 on 4/1/2017.
 */
interface VoogaRemote<T> {
    static <T> void sendTo(Message<T> message, ObjectOutputStream destination) {
        try {
            destination.writeObject(message);
        } catch (IOException e) {
            try {
                destination.writeObject(message);
            } catch (IOException e1) {
                try {
                    destination.writeObject(message);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    void sendMessage(Message<T> message);

    class Listener<T> extends Thread {
        private final Socket socket;
        private final Consumer<Message<T>> messageHandler;
        private ObjectInputStream messageInput;

        Listener(Socket socket, Consumer<Message<T>> messageHandler) throws IOException {
            this.socket = socket;
            this.messageInput = new ObjectInputStream(socket.getInputStream());
            this.messageHandler = messageHandler;
        }

        @Override
        public void run() {
            try {
                while (socket.isConnected()) {
                    messageHandler.accept((Message<T>) messageInput.readObject());
                }
            } catch (Exception e) {
                e.printStackTrace();
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

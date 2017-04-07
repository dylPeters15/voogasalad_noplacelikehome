package chat_client;

import util.net.ObservableServer;

import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * This class models a simple chat server. It allows clients to connect to the local host and facilitate communication between the client users.
 * <p>
 * An ObservableServer listens for requests from each client in a background thread, and relays them to the other clients if approved.
 * <p>
 * The server sends heartbeat requests periodically to each client, and either confirms that they are functioning properly or attempts to repair errors if they are not.
 * <p>
 * New clients are provided with a full log of all previous messages sent since the server began running.
 * <p>
 * If a client fails to respond within the timeout duration, the connection is terminated.
 *
 * @author Created by th174 on 3/30/2017.
 */
public class ChatServerMain {
    public static final int PORT = 1337;

    public static void main(String[] args) throws Exception {
        ObservableServer<ChatLog> voogaServer = new ObservableServer<>(
                new ChatLog("----------Welcome to team No_place_like_~/'s chat!----------\n\n"),
                PORT,
                ChatLog.CHAT_LOG_TEST_SERIALIZER,
                ChatLog.CHAT_LOG_TEST_UNSERIALIZER,
                Duration.ofSeconds(30));
        Executors.newSingleThreadExecutor().submit(voogaServer);
        System.out.println("Server started successfully...");
    }
}

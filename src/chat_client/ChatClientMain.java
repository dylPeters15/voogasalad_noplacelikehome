package chat_client;

import util.net.ObservableClient;
import util.net.ObservableHost;

import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * This class allows a client user to communicate with a chat server by sending input to stdin.
 * <p>
 * The current chat log from the server is written to stdout every time it is updated.
 * <p>
 * An ObservableClient runs in a background thread to handle client-server communication, by sending and receiving requests.
 * <p>
 * If the server fails to respond in the specified timeout duration, the connection is terminated, and the client program exits.
 *
 * @author Created by th174 on 4/1/2017.
 */
public class ChatClientMain {
    public static final String SERVER_HOSTNAME = ObservableHost.LOCALHOST;
    public static final int PORT = 1337;
    /**
     * Nasty hack lol, but IDE consoles don't support actually screen clearing
     */
    public static final String CLEARSCREEN = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    public static void main(String[] args) throws Exception {
        ObservableClient<ChatLog> voogaClient = new ObservableClient<>(
                SERVER_HOSTNAME,
                PORT,
                ChatLog.CHAT_LOG_TEST_SERIALIZER,
                ChatLog.CHAT_LOG_TEST_UNSERIALIZER,
                Duration.ofSeconds(30));
        System.out.println("Client started successfully...");
        Scanner stdin = new Scanner(System.in);
        voogaClient.addListener(client -> System.out.print(CLEARSCREEN + client.getChatLog() + "\n\n>>  "));
        Executors.newSingleThreadExecutor().submit(voogaClient);
        while (voogaClient.isActive()) {
            String input = stdin.nextLine();
            String user = System.getProperty("user.name");
            voogaClient.send(state -> state.appendMessage(input, user));
        }
    }
}

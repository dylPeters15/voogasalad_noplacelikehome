package chat_client;

import util.net.ObservableServer;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class ChatServerMain {
    public static final int PORT = 10023;

    public static void main(String[] args) throws Exception {
        ObservableServer<ChatLog> voogaServer = new ObservableServer<>(
                new ChatLog("----------Welcome to team No_place_like_~/'s chat room!----------\n\nPlease make sure you have pulled the latest commit from origin/master.\n"),
                PORT,
                ChatLog.CHAT_LOG_TEST_SERIALIZER,
                ChatLog.CHAT_LOG_TEST_UNSERIALIZER,
                Duration.ofSeconds(20));
        voogaServer.addListener(ChatServerMain::printNewRequest);
//        voogaServer.addListener(e -> voogaServer.sendAndApply(simpleChatLogTest -> simpleChatLogTest.appendMessage("Server says hello! ", "SERVER")));
        voogaServer.start();
    }

    private static void printNewRequest(ChatLog simpleChatLogTest) {
        System.out.println("Request received from client:\n\t@" + Instant.now(Clock.systemUTC()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }
}

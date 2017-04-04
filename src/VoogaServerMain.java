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
public class VoogaServerMain {
    public static final int PORT = 10023;

    public static void main(String[] args) throws Exception {
        ObservableServer<SimpleChatLogTest> voogaServer = new ObservableServer<>(
                new SimpleChatLogTest(),
                PORT,
                SimpleChatLogTest.CHAT_LOG_TEST_SERIALIZER,
                SimpleChatLogTest.CHAT_LOG_TEST_UNSERIALIZER,
                Duration.ofMinutes(5));
        voogaServer.addListener(VoogaServerMain::printNewRequest);
//        voogaServer.addListener(e -> voogaServer.sendAndApply(simpleChatLogTest -> simpleChatLogTest.appendMessage("Server says hello! ", "SERVER")));
        voogaServer.start();
    }

    private static void printNewRequest(SimpleChatLogTest simpleChatLogTest) {
        System.out.println("Request received from client:\n\t@" + Instant.now(Clock.systemUTC()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }
}

import util.net.Server;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class VoogaServerMain {
    public static final int PORT = 10023;


    public static void main(String[] args) throws Exception {
        Server<SimpleChatLogTest> voogaServer = new Server<>(
                new SimpleChatLogTest(),
                PORT,
                SimpleChatLogTest.CHAT_LOG_TEST_SERIALIZER,
                SimpleChatLogTest.CHAT_LOG_TEST_UNSERIALIZER);
        voogaServer.start();
        while (!voogaServer.isActive()) {
            Thread.sleep(2000);
            System.out.println("No clients yet.");
        }
        for (int i = 0; voogaServer.isActive(); i++) {
            System.out.println(voogaServer.getState());
            Thread.sleep(5000);
        }
    }
}

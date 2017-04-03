import util.io.Serializer;
import util.io.Unserializer;

/**
 * Class used for testing client-server networking
 *
 * @author Created by th174 on 4/1/2017.
 */
public class SimpleChatLogTest {
    public static final Serializer<SimpleChatLogTest> CHAT_LOG_TEST_SERIALIZER = SimpleChatLogTest::toString;
    public static final Unserializer<SimpleChatLogTest> CHAT_LOG_TEST_UNSERIALIZER = obj -> (SimpleChatLogTest) Class.forName(obj.toString().split("=")[0]).getConstructor(String.class).newInstance(obj.toString().split("=")[1]);

    private String log;

    public SimpleChatLogTest() {
        this("");
    }

    public SimpleChatLogTest(String log) {
        this.log = log;
    }

    public void add(String test) {
        log = test;
    }

    public String getLast() {
        return log;
    }

    public SimpleChatLogTest appendMessage(String message, String user) {
        add(String.format("%s\n<%s>:  %s", getLast(), user, message));
        return this;
    }

    @Override
    public String toString() {
        return getClass().getName() + "= " + log;
    }
}
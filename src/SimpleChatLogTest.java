import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class used for testing client-server networking
 *
 * @author Created by th174 on 4/1/2017.
 */
public class SimpleChatLogTest {
    private List<String> log;

    public SimpleChatLogTest() {
        this("");
    }

    public SimpleChatLogTest(String log) {
        this.log = new ArrayList<>(Collections.singletonList(log));
    }

    public void add(String test) {
        log.add(test);
    }

    public String getLast() {
        return log.get(log.size() - 1);
    }

    public SimpleChatLogTest appendMessage(String message, String user) {
        add(String.format("%s\n<%s>:  %s", getLast(), user, message));
        return this;
    }

    @Override
    public String toString() {
        return getClass().getName() + "= " + log.toString().replaceAll("[\\[,\\]]", "");
    }
}
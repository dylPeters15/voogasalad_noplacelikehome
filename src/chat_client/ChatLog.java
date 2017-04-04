package chat_client;

import util.io.Serializer;
import util.io.Unserializer;

/**
 * Class used for testing client-server networking
 *
 * @author Created by th174 on 4/1/2017.
 */
public class ChatLog {
    public static final Serializer<ChatLog> CHAT_LOG_TEST_SERIALIZER = ChatLog::toString;
    public static final Unserializer<ChatLog> CHAT_LOG_TEST_UNSERIALIZER = obj -> (ChatLog) Class.forName(obj.toString().split("=")[0]).getConstructor(String.class).newInstance(obj.toString().split("=")[1]);

    private String log;

    public ChatLog() {
        this(" ");
    }

    public ChatLog(String log) {
        this.log = log;
    }

    public void add(String test) {
        log = test;
    }

    public String getLast() {
        return log;
    }

    public ChatLog appendMessage(String message, String user) {
        add(String.format("%s\n<%s>:  %s", getLast(), user, message));
        return this;
    }

    @Override
    public String toString() {
        return getClass().getName() + "=" + log;
    }
}
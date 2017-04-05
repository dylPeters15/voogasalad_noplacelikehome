package util.net;

import java.io.Serializable;

/**
 * This class provides contains all communications between the client and the server.
 * Each request contains a content as well as a timestamp of when the request was created.
 * The server uses the timestamp to determine whether or not to approve the request.
 *
 * @param <T> The type of the content contained in the request.
 * @author Created by th174 on 4/2/2017.
 * @see Request,Modifier,ObservableServer,ObservableClient,ObservableHostBase,SocketConnection
 */
public final class Request<T extends Serializable> implements Serializable {
    public static final Serializable ERROR = "ERROR";
    public static final Serializable HEARTBEAT = "HEARTBEAT";
    private final T content;
    private final int commitIndex;

    /**
     * Creates a new request with content and a timestamp of the creation time.
     *
     * @param content Content of request
     */
    public Request(T content) {
        this(content, 0);
    }

    /**
     * Creates a new request with content and a timestamp of the creation time.
     *
     * @param content     Content of request
     * @param commitIndex commitIndex of the sender of this request
     */
    public Request(T content, int commitIndex) {
        this.content = content;
        this.commitIndex = commitIndex;
    }

    /**
     * Tests if the specified request is a heartbeat request
     *
     * @param request request to test
     * @return Returns true if request is a heartbeat
     */
    public static boolean isHeartbeat(Request request) {
        return request.get().equals(HEARTBEAT);
    }

    /**
     * Tests is the specified request is an error request
     *
     * @param request request to test
     * @return Returns true if request is an error request
     */
    public static boolean isError(Request request) {
        return request.get().equals(ERROR);
    }

    /**
     * @return Returns the content contained inside with this Request
     */
    public T get() {
        return content;
    }

    /**
     * @return Returns the commitIndex that this Request was sent with
     */
    public int getCommitIndex() {
        return commitIndex;
    }

    @Override
    public String toString() {
        return String.format("Request:\n\tContent:\t%s\n\tCommit:\t%d", content, commitIndex);
    }
}

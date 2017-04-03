package util.net;

import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * This class provides contains all communications between the client and the server.
 * Each request contains a content as well as a timestamp of when the request was created.
 * The server uses the timestamp to determine whether or not to approve the request.
 *
 * @param <T> The type of the content contained in the request.
 * @author Created by th174 on 4/2/2017.
 * @see Request,Modifier,Server,Server.ServerThread,Client,AbstractHost,Host,Listener
 */
public final class Request<T extends Serializable> implements Serializable {
    private final T content;
    private final Instant timeStamp;

    /**
     * Creates a new request with content and a timestamp of the creation time.
     *
     * @param content Content of request
     */
    public Request(T content) {
        this.content = content;
        this.timeStamp = Instant.now(Clock.systemUTC());
    }

    /**
     * @return Returns the content contained inside with this Request
     */
    public T get() {
        return content;
    }

    /**
     * @return Returns the type of content contained inside this Request
     */
    public Class<?> getContentType() {
        return content.getClass();
    }

    /**
     * @return Timestamp when this request was created
     */
    public Instant getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return String.format("Request @ %s:\n\t%s", timeStamp.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)), content);
    }
}

package util.net;

import java.io.Serializable;

/**
 * This interface provides a unified API for a server or client communicating over TCP/IP.
 * <p>
 * It can send and receive requests from a remote host.
 *
 * @param <T> The type of variable used to represent network shared state.
 * @author Created by th174 on 4/2/2017.
 * @see VoogaRequest,VoogaServer,VoogaServerThread,VoogaClient,VoogaRemote,Remote
 */
public interface Remote<T> {
    /**
     * @return Returns the local state, which should match network shared state
     */
    T getState();

    /**
     * Handles requests sent from the host.
     *
     * @param request Request to be handled
     */
    void handleRequest(Serializable request);

    /**
     * Sends a request to the host that this VoogaRemote is connected to.
     *
     * @param request Request to be sent over netowkr
     * @return Returns true if request was sent successfully
     */
    boolean sendRequest(Serializable request);

    /**
     * @return Returns true while the connection associated with this object is active
     */
    boolean isActive();
}

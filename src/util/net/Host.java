package util.net;

import util.io.Serializer;
import util.io.Unserializer;

import java.io.Serializable;
import java.time.Instant;

/**
 * This interface provides a unified API for a server or client communicating over TCP/IP.
 * <p>
 * It can send and receive requests from a remote host.
 *
 * @param <T> The type of variable used to represent network shared state.
 * @author Created by th174 on 4/2/2017.
 * @see Request,Modifier,Server,ServerThread,Client,AbstractHost,Host,Listener
 */
public interface Host<T> {
    String LOCALHOST = "127.0.0.1";
    Serializer NO_SERIALIZER = obj -> (Serializable) obj;
    Unserializer NO_UNSERIALIZER = obj -> obj;

    /**
     * @return Returns the local state, which should match network shared state
     */
    T getState();

    /**
     * This method is invoked when a new state is received from the remote host.
     *
     * @param newState  The new state sent from the remote host.
     * @param timeStamp Request creation timestamp
     * @throws Exception Thrown when an error occurs while processing the request the new state.
     */
    void handle(T newState, Instant timeStamp) throws Exception;

    /**
     * This method is invoked when a state modifier is received from the remote host.
     *
     * @param stateModifier The state modifier received from the remote host.
     * @param timeStamp     Request creation timestamp
     * @throws Exception Thrown when an error occurs while processing the state modifier.
     */
    void handle(Modifier<T> stateModifier, Instant timeStamp) throws Exception;

    /**
     * Sends a state modifier to the remote host
     *
     * @param modifier Modifier to be sent over network.
     * @return Returns true if request was sent successfully
     * @throws Exception Thrown when error occurs in serializing or sending the modifier.
     */
    boolean send(Modifier<T> modifier) throws Exception;

    /**
     * Sends a full new state to the remote host
     *
     * @param newState New state to be serialized and sent over network
     * @return Returns true if request was sent successfully
     * @throws Exception Thrown when error occurs in serializing or sending the modifier.
     */
    boolean send(T newState) throws Exception;

    /**
     * @return Returns true while the connection associated with this object is active
     */
    boolean isActive();

    /**
     * @return Returns the serializer currently used by this host
     */
    Serializer<T> getSerializer();

    /**
     * @return Returns the serializer currently usd by this host
     */
    Unserializer<T> getUnserializer();
}

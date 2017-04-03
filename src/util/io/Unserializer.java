package util.io;

import java.io.Serializable;

/**
 * This functional interface creates an unserializable object from a serializable one
 *
 * @author Created by th174 on 4/1/2017.
 */
@FunctionalInterface
public interface Unserializer<T> {
    /**
     * @param obj Object to be converted from serializable to unserializable form
     * @return Unserializable form of object
     * @throws Exception Thrown if implementation throws exception
     */
    T unserialize(Serializable obj) throws Exception;
}

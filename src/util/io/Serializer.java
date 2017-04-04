package util.io;

import java.io.Serializable;

/**
 * This functional interface creates a serializable object from an unserializable one.
 *
 * @author Created by th174 on 4/1/2017.
 */
@FunctionalInterface
public interface Serializer<T> {
    Serializer NONE = obj -> (Serializable) obj;
    /**
     * @param obj Object to be converted to Serializable form
     * @return Serializable form of obj
     * @throws Exception Thrown if implementation throws exception
     */
    Serializable serialize(T obj) throws Exception;
}

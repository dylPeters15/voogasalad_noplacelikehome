package util.io;

/**
 * This functional interface creates an unserializable object from a serializable one
 *
 * @author Created by th174 on 4/1/2017.
 */
@FunctionalInterface
public interface Unserializer<T> {
    /**
     * @param serializedObject Serializable form of object
     * @return Unserialized form of object
     */
    T unserialize(Object serializedObject) throws Exception;
}

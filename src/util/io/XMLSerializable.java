package util.io;

import java.lang.reflect.InvocationTargetException;

/**
 * Tavo
 *
 * @author Created by th174 on 3/27/2017.
 */
public interface XMLSerializable {
    static XMLSerializable createFromXML(String xml) throws XMLException {
        //TODO
        //Test implementation:
        String[] temp = xml.split("=");
        try {
            return (XMLSerializable) Class.forName(temp[0]).getConstructor(String.class).newInstance(temp[1]);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            throw new XMLException(xml, e);
        }
    }

    String toXml();

    class XMLException extends Exception {
        XMLException(String s, Exception e) {
            //TODO Resourcebundlify
            super("An error occurred parsing the XML.\n\t" + s, e);
        }
    }
}

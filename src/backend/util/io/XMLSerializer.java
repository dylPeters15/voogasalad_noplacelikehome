package backend.util.io;

import com.thoughtworks.xstream.XStream;
import util.io.Serializer;
import util.io.Unserializer;

import java.io.Serializable;

/**
 * @author Created by th174 on 4/9/2017.
 */
public class XMLSerializer<T> implements Serializer<T>, Unserializer<T> {
	private static final XStream xStream = new XStream();

	@Override
	public String doSerialize(T obj) throws Exception {
		return xStream.toXML(obj);
	}

	@Override
	public T doUnserialize(Serializable obj) throws Exception {
		return (T) xStream.fromXML((String) obj);
	}
}

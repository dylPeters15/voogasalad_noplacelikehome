package backend.util.io;

import com.google.gson.Gson;
import util.io.Serializer;
import util.io.Unserializer;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * @author Created by th174 on 4/9/2017.
 */
public class JSONSerializer<T> implements Serializer<T>, Unserializer<T> {
	private static Gson gson = new Gson();
	private final Type objType;
	public JSONSerializer(Type type){
		this.objType = type;
	}

	@Override
	public String doSerialize(T obj) throws Exception {
		return gson.toJson(obj);
	}

	@Override
	public T doUnserialize(Serializable obj) throws Exception {
		return gson.fromJson((String) obj, objType);
	}
}

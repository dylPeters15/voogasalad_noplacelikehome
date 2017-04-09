package util.input_parse;

@FunctionalInterface
public interface InputDecoder<T, V> {

	String getCode(T type, V string);

	default String getName(V string) {
		String code = (String) string;
		return code.split("\\s+")[0];
	}

	enum Type{
		PREDICATE("UserPredicate", "name (state) -> *"),
		BIPREDICATE("UserBiPredicate", "name (player, state) -> *"),
		CONSUMER("UserConsumer", "name (state) -> *"),
		BICONSUMER("UserBiConsumer", "name (player, state) -> *");

		private String typeString;

		private String parseFormat;

		Type(String type, String format){
			parseFormat = format;
			typeString = type;
		}

		public String getType(){
			return typeString;
		}

		public String getFormat(){
			return parseFormat;
		}
	}
}

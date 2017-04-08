package input_parse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import backend.util.MutableGameState;
import input_parse.InputDecoder.Type;

public class Parser {
	
	private InputDecoder<Type, String> myInputDecoder = (type, string) -> {
		String everything = (String) string;
		java.util.List<String> code = Arrays.asList(everything.split("\\s+"));
		//code.remove(0);
		String returnString = String.join(" ", code);
		return returnString;
	};

	private String filePath = "src/input_parse/";
	private File file;

	private MutableGameState gameState;

	private List<String> inputHistory;
	private Map<String, Class> classMap;

	public Parser(MutableGameState gamestate) {
		gameState = gamestate;
	}

	public void parse(Type type, String string) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		String className = myInputDecoder.getName(string);
		String code = myInputDecoder.getCode(type, string);
		String classText = type.getType();
		
		/*
		// Write code here that will write out the object correctly.
		*/
		
		file = new File(filePath + classText + ".java");

		List<String> lines;
		try {
			lines = Files.readAllLines(file.toPath());
			lines.set(9, code);
			Files.write(file.toPath(), lines);
			compiler.run(null, null, null, file.toPath().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

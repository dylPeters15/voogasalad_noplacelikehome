package controller;
/**
 * Exception class
 * 
 * @author ncp14
 *
 */
public class ParsingErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
	public ParsingErrorException(String message, Object ... values) {
		super(String.format(message, values));
	}
	
	public ParsingErrorException(Throwable cause, String message, Object ...objects) {
		super(String.format(message, objects));
	}
	
	public ParsingErrorException(Throwable cause) {
		super(cause);
	}
}

package assignment;

public class WrongFileFormatException extends Exception{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String message;
	
	public WrongFileFormatException(String msg) {
		super(msg);
		message = msg;
	}
}

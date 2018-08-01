package exceptions;

@SuppressWarnings("serial")
public class DisplayException extends Exception {
	
	public DisplayException(String message) {
		super("DISPLAY:: " + message);
	}

}

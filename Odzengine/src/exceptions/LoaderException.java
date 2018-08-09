package exceptions;

@SuppressWarnings("serial")
public class LoaderException extends Exception {
	
	public LoaderException(String message) {
		super("LOADER:: " + message);
	}

}

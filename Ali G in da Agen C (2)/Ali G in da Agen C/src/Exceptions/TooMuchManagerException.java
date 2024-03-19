package Exceptions;

@SuppressWarnings("serial")
public class TooMuchManagerException extends RuntimeException {
	public TooMuchManagerException() {
		
	}
	public TooMuchManagerException(String s) {
		super(s);
	}
}

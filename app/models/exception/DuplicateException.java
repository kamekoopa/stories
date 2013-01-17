package models.exception;


public class DuplicateException extends ApplicationException {
	private static final long serialVersionUID = -3773482728993624074L;

	public DuplicateException(final String message){
		super(401, message);
	}
}

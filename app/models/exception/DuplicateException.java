package models.exception;


public class DuplicateException extends ApplicationException {
	private static final long serialVersionUID = -3773482728993624074L;

	public DuplicateException() {
		super(409, "resource duplication");
	}

	public DuplicateException(Long id) {
		super(404, "resource duplication [id: "+id+"]");
	}

	public DuplicateException(String name) {
		super(404, "resource duplication [name: "+name+"]");
	}
}

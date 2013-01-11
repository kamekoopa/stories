package models.exception;


public class NotFoundException extends ApplicationException {
	private static final long serialVersionUID = -3773482728993624074L;

	public NotFoundException() {
		super(404, "resource not found");
	}

	public NotFoundException(Long id) {
		super(404, "resource not found [id: "+id+"]");
	}

	public NotFoundException(String name) {
		super(404, "resource not found [name: "+name+"]");
	}
}

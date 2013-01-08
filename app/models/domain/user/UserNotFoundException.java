package models.domain.user;

import models.exception.ApplicationException;

public class UserNotFoundException extends ApplicationException {
	private static final long serialVersionUID = -3773482728993624074L;

	public UserNotFoundException() {
		super(404, "user not found");
	}

	public UserNotFoundException(Long id) {
		super(404, "user not found [id: "+id+"]");
	}

	public UserNotFoundException(String name) {
		super(404, "user not found [name: "+name+"]");
	}
}

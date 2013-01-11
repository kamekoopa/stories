package models.domain.model.user;

import models.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
	private static final long serialVersionUID = -3773482728993624074L;

	public UserNotFoundException() {
		super("user not found");
	}

	public UserNotFoundException(Long id) {
		super("user not found [id: "+id+"]");
	}

	public UserNotFoundException(String name) {
		super("user not found [name: "+name+"]");
	}
}

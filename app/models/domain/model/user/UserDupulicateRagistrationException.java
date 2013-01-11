package models.domain.model.user;

import models.exception.DuplicateException;

public class UserDupulicateRagistrationException extends DuplicateException {
	private static final long serialVersionUID = -3773482728993624074L;

	public UserDupulicateRagistrationException() {
		super("user identifier duplication");
	}

	public UserDupulicateRagistrationException(Long id) {
		super("user identifier duplication [id: "+id+"]");
	}

	public UserDupulicateRagistrationException(String name) {
		super("user identifier duplication [name: "+name+"]");
	}
}

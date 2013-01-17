package models.domain.model.user;

import models.exception.DuplicateException;

public class UserDupulicateRagistrationException extends DuplicateException {
	private static final long serialVersionUID = -3773482728993624074L;

	public UserDupulicateRagistrationException(final String message) {
		super(message);
	}
}

package models.domain.user;

import models.exception.ApplicationException;

public class UnAuthorizedIdentityException extends ApplicationException {
	private static final long serialVersionUID = 6066369107643528629L;

	public UnAuthorizedIdentityException(final String message){
		super(401, message);
	}
}

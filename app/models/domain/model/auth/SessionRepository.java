package models.domain.model.auth;

import models.exception.NotFoundException;


public interface SessionRepository {

	public void store(final ServerSession serverSession);

	public ServerSession get(final SessionId sessId) throws NotFoundException;

	public void delete(final SessionId sessId);
}

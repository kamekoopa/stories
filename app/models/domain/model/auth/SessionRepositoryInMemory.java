package models.domain.model.auth;

import models.domain.model.OnMemoryCache;
import models.domain.model.user.User;
import models.exception.NotFoundException;

public class SessionRepositoryInMemory implements SessionRepository {


	@Override
	public void store(final ServerSession session) {

		String sessId = session.getIdentifier().toString();
		String userIdentifier = session.getSessionOwner().getIdentifier();

		OnMemoryCache.instance.set(sessId, userIdentifier);
	}

	@Override
	public ServerSession get(SessionId sessId) throws NotFoundException {

		String userIdentifier = OnMemoryCache.instance.get(sessId.toString());
		User user = User.Finder.findByIdentifier(userIdentifier);

		return new ServerSession(sessId, user);
	}

	@Override
	public void delete(SessionId sessId) {

		OnMemoryCache.instance.delete(sessId.toString());
	}
}

package models.domain.model.auth;

import models.domain.model.OnMemoryCache;

public class SessionRepositoryInMemory implements SessionRepository {


	@Override
	public void store(final SessionId sessId, final String identity) {

		OnMemoryCache.instance.set(sessId.toString(), identity);
	}

	@Override
	public String get(SessionId sessId) {

		return OnMemoryCache.instance.get(sessId.toString());
	}

	@Override
	public void delete(SessionId sessId) {

		OnMemoryCache.instance.delete(sessId.toString());
	}
}

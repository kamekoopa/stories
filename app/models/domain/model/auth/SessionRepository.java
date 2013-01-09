package models.domain.model.auth;


public interface SessionRepository {

	public void store(final SessionId sessId, final String identity);

	public String get(final SessionId sessId);

	public void delete(final SessionId sessId);
}

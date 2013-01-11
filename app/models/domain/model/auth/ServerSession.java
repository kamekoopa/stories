package models.domain.model.auth;

import models.domain.model.Entity;
import models.domain.model.user.User;

public class ServerSession extends Entity<SessionId, ServerSession>{

	protected final SessionId sessId;

	protected final User user;

	protected ServerSession(final SessionId sessId, final User user){
		this.sessId = sessId;
		this.user = user;
	}

	@Override
	public SessionId getIdentifier() {
		return this.sessId;
	}

	public User getSessionOwner(){
		return this.user;
	}

	public static class Builder {

		public static ServerSession create(final User user){

			SessionId sessId = new SessionId(user);
			return new ServerSession(sessId, user);
		}
	}
}

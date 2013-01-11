package models.domain.model.auth;

import models.domain.model.user.User;
import models.exception.NotFoundException;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class SessionRetrieveService {

	@Inject
	private SessionRepository sessionRepository;

	private final ClientSession clientSession;

	@Inject
	public SessionRetrieveService(@Assisted ClientSession clientSession){
		this.clientSession = clientSession;
	}

	public User getSessionOwner() throws UnAuthorizedIdentityException {

		SessionId sessId = this.clientSession.getSessionId();

		try {

			ServerSession serverSession = this.sessionRepository.get(sessId);

			return serverSession.getSessionOwner();

		}catch (NotFoundException e) {
			throw new UnAuthorizedIdentityException("sessid that provide from client is not authorized");
		}
	}

	public User getSessionOwnerWithSessIdRegenerate() throws UnAuthorizedIdentityException {

		SessionId now = this.clientSession.getSessionId();

		try {

			ServerSession currentSession = this.sessionRepository.get(now);
			User sessionOwner = currentSession.getSessionOwner();

			ServerSession newSession = ServerSession.Builder.create(sessionOwner);

			this.sessionRepository.store(newSession);
			this.sessionRepository.delete(now);

			this.clientSession.markAuthenticated(newSession);

			return sessionOwner;

		} catch (NotFoundException e) {
			throw new UnAuthorizedIdentityException("sessid that provide from client is not authorized");
		}
	}

	public static interface SessionRetrieveServiceBuilder{
		public SessionRetrieveService create(ClientSession clientSession);
	}
}

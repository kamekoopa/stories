package models.domain.model.auth;

import models.domain.model.user.User;
import models.domain.model.user.UserRetrieveService;
import models.exception.NotFoundException;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class SessionPersistentService {

	@Inject
	private SessionRepository sessionRepository;

	private final UserRetrieveService userRetrieveService = new UserRetrieveService();

	private final ClientSession clientSession;

	@Inject
	public SessionPersistentService(@Assisted ClientSession clientSession){
		this.clientSession = clientSession;
	}

	public SessionId createSession(final AuthenticationInfo authInfo) throws UnAuthorizedIdentityException {

		try {

			User user = this.userRetrieveService.findByAuthenticationInfo(authInfo);

			ServerSession serverSession = ServerSession.Builder.create(user);
			this.sessionRepository.store(serverSession);

			this.clientSession.markAuthenticated(serverSession);

			return serverSession.getIdentifier();

		}catch(NotFoundException e){
			throw new UnAuthorizedIdentityException("invalid username or password");
		}
	}

	public void destroySession(){

		SessionId sessId = this.clientSession.getSessionId();
		this.sessionRepository.delete(sessId);

		this.clientSession.removeAuthenticated();
	}


	public static interface SessionPersistentServiceBuilder{
		public SessionPersistentService create(ClientSession clientSession);
	}
}

package models.applications;

import models.domain.model.auth.AuthenticationInfo;
import models.domain.model.auth.SessionId;
import models.domain.model.auth.SessionRepository;
import models.domain.model.auth.UnAuthorizedIdentityException;
import models.domain.model.auth.formvalue.Login;
import models.domain.model.user.User;
import models.domain.model.user.UserNotFoundException;
import models.domain.model.user.UserRepository;

import org.apache.commons.lang3.StringUtils;

import play.data.Form;
import play.mvc.Http.Session;

import com.google.inject.Inject;

public class AuthService {

	@Inject
	private UserRepository userRepository;

	@Inject
	private SessionRepository sessionRepository;

	public SessionId authenticate(final Form<Login> form, final Session sessionCookie) throws UnAuthorizedIdentityException {

		try{

			AuthenticationInfo authInfo = form.get().getAuthInfo();

			User user = this.userRepository.findByAuthenticationInfo(authInfo);
			SessionId sessionId = new SessionId(user);

			this.sessionRepository.store(sessionId, user.getIdentifier());
			setSessionId(sessionCookie, sessionId);

			return sessionId;

		}catch(UserNotFoundException e){
			throw new UnAuthorizedIdentityException("invalid username or password");
		}
	}

	public void deauthenticate(final Session session){

		SessionId sessId = sessionId(session);
		this.sessionRepository.delete(sessId);
	}

	public User getSessionOwner(final Session session) throws UnAuthorizedIdentityException {

		String identity = this.getIndentity(session);

		try{
			return this.userRepository.findByIdentity(identity);
		}catch (UserNotFoundException e) {
			throw new UnAuthorizedIdentityException("sessid that provide from client not exists in session store in server");
		}
	}

	public User getSessionOwnerWithRegenerateSessionId(final Session session) throws UnAuthorizedIdentityException {

		User user = this.getSessionOwner(session);

		SessionId now = sessionId(session);
		SessionId newer = new SessionId(user);

		this.sessionRepository.store(newer, user.getIdentifier());
		this.sessionRepository.delete(now);

		setSessionId(session, newer);

		return user;
	}

	private String getIndentity(final Session session) throws UnAuthorizedIdentityException {

		String identity = this.sessionRepository.get(sessionId(session));
		if(StringUtils.isEmpty(identity)){
			throw new UnAuthorizedIdentityException("sessid that provide from client not exists in session store in server");
		}else{
			return identity;
		}
	}

	private static SessionId sessionId(final Session session){
		return new SessionId(session.get("sessid"));
	}

	private static void setSessionId(final Session session, final SessionId sessionId){
		session.put("sessid", sessionId.toString());
	}
}

package models.domain.user;

import models.domain.cache.OnMemoryCache;

import org.apache.commons.codec.digest.DigestUtils;

import play.mvc.Http.Session;

public class AuthService {

	private final UserRepository userRepository;
	private final OnMemoryCache cache;

	public AuthService(){
		this.userRepository = new UserRepository();
		this.cache = OnMemoryCache.instance;
	}

	public String getIdentity(final Session session) throws UnAuthorizedIdentityException {

		String name = this.cache.get(session.get("sessid"));
		if(name == null || name.equals("")){
			throw new UnAuthorizedIdentityException("specified sessionId is not authorized");
		}else{
			return name;
		}
	}

	public String getIdentityWithSessionIdRegenerate(final Session session) throws UnAuthorizedIdentityException {

		String name = this.getIdentity(session);
		String newSessId = this.createSessionId(name);

		this.cache.set(newSessId, name);
		this.cache.delete(session.get("sessid"));

		session.put("sessid", newSessId);

		return name;
	}

	public String authenticate(final String username, final String password, final Session session) throws UnAuthorizedIdentityException {

		try {

			User user = this.userRepository.findByAuthenticationInfo(username, password);

			String sessionId = this.createSessionId(user.getUserName());

			this.cache.set(sessionId, user.getUserName());
			session.put("sessid", sessionId);

			return sessionId;

		} catch (UserNotFoundException e) {
			throw new UnAuthorizedIdentityException("invalid username or password");
		}
	}

	public void deauthenticate(final Session session){

		String sessionId = session.get("sessid");

		this.cache.delete(sessionId);
		session.remove("sessid");
	}

	private String createSessionId(String name){
		return DigestUtils.sha1Hex(name + ":" + Long.valueOf(System.currentTimeMillis()).toString());
	}
}

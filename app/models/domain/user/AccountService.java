package models.domain.user;

import models.domain.cache.OnMemoryCache;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.JsonNode;

public class AccountService {

	private final UserRepository userRepository;
	private final OnMemoryCache cache;

	public AccountService(){
		this.userRepository = new UserRepository();
		this.cache = OnMemoryCache.instance;
	}

	public User create(final String userName){
		return this.userRepository.create(userName);
	}

	public String authenticate(final String name) throws UnAuthorizedIdentityException {

		try {

			User user = this.userRepository.findByName(name);

			String token = this.createLoginToken(user);
			this.cache.set(token, token);

			return token;

		} catch (UserNotFoundException e) {
			throw new UnAuthorizedIdentityException(e.getMessage());
		}
	}

	public void logout(String token){

		this.cache.delete(token);
	}

	public User obtainById(final Long id) throws UserNotFoundException {
		return this.userRepository.findById(id);
	}

	public User modifyByJson(final Long id, final JsonNode json) throws UserNotFoundException {
		User modified = this.obtainById(id).setFromJson(json);
		this.userRepository.modify(modified);
		return modified;
	}

	public void removeAccount(final Long id) throws UserNotFoundException {
		User deleting = this.obtainById(id);
		this.userRepository.removeWithPhysical(deleting);
	}

	private String createLoginToken(User user){
		return DigestUtils.sha1Hex(user.users.name);
	}
}

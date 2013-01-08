package models.domain.user;

import org.codehaus.jackson.JsonNode;

public class AccountService {

	private final UserRepository userRepository;

	public AccountService(){
		this.userRepository = new UserRepository();
	}

	public User create(final String userName){
		return this.userRepository.create(userName);
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
}

package models.domain.model.user;

import models.domain.model.auth.AuthenticationInfo;

public interface UserRepository {

	public void register(User user);

	public User findByIdentity(final String identity) throws UserNotFoundException ;

	public User findByAuthenticationInfo(AuthenticationInfo authInfo) throws UserNotFoundException ;
}

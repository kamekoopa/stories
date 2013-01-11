package models.domain.model.user;

import models.domain.model.auth.AuthenticationInfo;

public class UserRetrieveService {

	public User findByIdentitifier(String identity) throws UserNotFoundException {

		return  User.Finder.findByIdentifier(identity);
	}

	public User findByAuthenticationInfo(AuthenticationInfo authInfo) throws UserNotFoundException {

		return User.Finder.findByAuthInfo(authInfo);
	}
}

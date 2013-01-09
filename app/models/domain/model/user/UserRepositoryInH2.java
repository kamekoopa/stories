package models.domain.model.user;

import models.domain.model.auth.AuthenticationInfo;
import models.orm.ebeans.Users;

public class UserRepositoryInH2 implements UserRepository {

	@Override
	public void register(User user) {

		Users newface = new Users();
		newface.name = user.getUsername();
		newface.password = user.getPassword().toString();

		newface.save();
	}

	@Override
	public User findByIdentity(String identity) throws UserNotFoundException {

		Users users = Users.find.where().eq("name", identity).findUnique();

		return returnOrThrow(users);
	}

	@Override
	public User findByAuthenticationInfo(AuthenticationInfo authInfo) throws UserNotFoundException {

		Users users = Users.find.where()
			.eq("name"    , authInfo.username())
			.eq("password", authInfo.password().toString())
			.findUnique();

		return returnOrThrow(users);
	}

	private static User returnOrThrow(Users users) throws UserNotFoundException {

		if(users == null){
			throw new UserNotFoundException();
		}else{
			return User.Builder.fromOrmEntity(users);
		}
	}
}

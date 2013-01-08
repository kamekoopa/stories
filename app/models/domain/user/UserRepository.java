package models.domain.user;

import models.orm.ebeans.Users;

import org.apache.commons.codec.digest.DigestUtils;

public class UserRepository {

	public User create(final String name, final String password){

		Users newface = new Users();
		newface.name = name;
		newface.password = DigestUtils.sha1Hex(password);
		newface.save();

		return new User(newface);
	}

	public User findById(final Long id) throws UserNotFoundException {

		Users users = Users.find.byId(id);

		if(users == null){
			throw new UserNotFoundException(id);
		}else{
			return new User(users);
		}
	}

	public User findByName(final String name) throws UserNotFoundException {

		Users users = Users.find.where().eq("name", name).findUnique();

		if(users == null){
			throw new UserNotFoundException(name);
		}else{
			return new User(users);
		}
	}

	public User findByAuthenticationInfo(final String userName, final String password) throws UserNotFoundException {

		String passwdHash = DigestUtils.sha1Hex(password);

		Users users = Users.find.where()
			.eq("name", userName)
			.eq("password", passwdHash)
			.findUnique();

		if(users == null){
			throw new UserNotFoundException();
		}else{
			return new User(users);
		}
	}

	public void modify(User user){
		user.users.update();
	}

	public void removeWithPhysical(User user){
		user.users.delete();
	}
}

package models.domain.model.user;

import models.domain.model.Entity;
import models.orm.ebeans.Users;

public class User extends Entity<String, User> {

	private String username;

	private Password password;

	@Override
	public String getIdentifier() {
		return this.getUsername();
	}

	public String getUsername(){
		return this.username;
	}

	public Password getPassword(){
		return this.password;
	}

	public static class Builder{

		public static User newface(final String username, final String rawpassword){

			User newface = new User();
			newface.username = username;
			newface.password = Password.fromRaw(rawpassword);

			return newface;
		}

		public static User fromOrmEntity(final Users users){

			User user = new User();
			user.username = users.name;
			user.password = new Password(users.password);

			return user;
		}
	}
}

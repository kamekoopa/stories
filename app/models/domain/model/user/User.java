package models.domain.model.user;

import java.util.ArrayList;
import java.util.List;

import models.domain.model.Entity;
import models.domain.model.auth.AuthenticationInfo;
import models.domain.model.boxes.Box;
import models.infra.ebean.entity.BoxEbean;
import models.infra.ebean.entity.UserEbean;

public class User extends Entity<String, User> {

	protected final UserEbean ebean;

	protected User(final UserEbean ebean){
		this.ebean = ebean;
	}

	@Override
	public String getIdentifier() {
		return this.getUsername();
	}

	public String getUsername(){
		return this.ebean.name;
	}

	public User addBox(Box box){

		this.ebean.createdBoxes.add(box.ebean());

		return this;
	}

	public List<Box> getMyBoxes(){

		List<Box> boxes = new ArrayList<>();
		for(BoxEbean boxEbean : this.ebean.createdBoxes){
			boxes.add(Box.Builder.fromEbean(boxEbean));
		}

		return boxes;
	}

	public static class Builder {

		public static User newface(final String username, final String rawpassword){

			UserEbean newface = new UserEbean();
			newface.name = username;
			newface.pass = Password.fromRaw(rawpassword).toString();
			newface.createdBoxes = new ArrayList<>();
			newface.createdCards = new ArrayList<>();

			return new User(newface);
		}

		public static User fromEbean(final UserEbean ebean){

			return new User(ebean);
		}
	}

	public static class Finder {

		public static User findByAuthInfo(AuthenticationInfo authInfo) throws UserNotFoundException {

			UserEbean ebean = UserEbean.find.where()
				.eq("name"    , authInfo.username())
				.eq("pass", authInfo.password().toString())
				.findUnique();

			return returnOrThrow(ebean);
		}

		public static User findByIdentifier(String identifier) throws UserNotFoundException {

			UserEbean ebean = UserEbean.find.where()
				.eq("name"    , identifier)
				.findUnique();

			return returnOrThrow(ebean);
		}

		private static User returnOrThrow(UserEbean ebean) throws UserNotFoundException {

			if(ebean == null){
				throw new UserNotFoundException();
			}else{
				return new User(ebean);
			}
		}

	}
}

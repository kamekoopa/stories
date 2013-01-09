package models.domain.model.user.formvalue;

public class UserRegistration {
	public String username;
	public String password;

	public static UserRegistration defaultValue(){

		UserRegistration defaultValue = new UserRegistration();
		defaultValue.username = "";
		defaultValue.password = "";

		return defaultValue;
	}
}

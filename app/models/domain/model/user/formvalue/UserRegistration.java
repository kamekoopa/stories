package models.domain.model.user.formvalue;

import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

public class UserRegistration {

	@Required(message = "error.require.username")
	public String username;

	@Required(message = "error.require.password")
	@Pattern(message = "error.invalid.password_format", value = "[a-zA-Z0-9\\_\\-@]+")
	public String password;

	public static UserRegistration defaultValue(){

		UserRegistration defaultValue = new UserRegistration();
		defaultValue.username = "";
		defaultValue.password = "";

		return defaultValue;
	}
}

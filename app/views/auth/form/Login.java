package views.auth.form;

import play.data.validation.Constraints.Required;

public class Login {

	@Required
	public String name;

	@Required
	public String password;

	@Required
	public String callback;
}

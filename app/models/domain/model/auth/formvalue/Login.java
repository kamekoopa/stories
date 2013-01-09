package models.domain.model.auth.formvalue;

import models.domain.model.auth.AuthenticationInfo;
import models.domain.model.user.Password;
import play.data.validation.Constraints.Required;

public class Login {

	@Required
	public String name;

	@Required
	public String password;

	@Required
	public String callback;

	public AuthenticationInfo getAuthInfo(){
		return new AuthenticationInfo(this.name, Password.fromRaw(this.password));
	}

	public static Login defaultValue(){

		Login def = new Login();
		def.name = "";
		def.password = "";
		def.callback = "";

		return def;
	}
}

package controllers.auth;

import models.domain.user.AuthService;
import models.domain.user.UnAuthorizedIdentityException;
import models.utils.LoggedIn;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.auth.form.Login;

public class AuthController extends Controller {

	public static Result login(){

		Login defaultValue = new Login();
		defaultValue.name = "";

		Form<Login> loginForm = form(Login.class).fill(defaultValue);

		return ok(views.html.auth.login.render(loginForm));
	}

	public static Result authenticate(){

		Form<Login> loginForm = form(Login.class).bindFromRequest();

		if(loginForm.hasErrors()){
			return badRequest(views.html.auth.login.render(loginForm));

		}else{

			try {

				Login formValue = loginForm.get();

				new AuthService().authenticate(formValue.name, formValue.password, session());

				return redirect(formValue.callback);

			} catch (UnAuthorizedIdentityException e) {

				loginForm.reject(e.getMessage());
				return unauthorized(views.html.auth.login.render(loginForm));
			}
		}
	}

	@LoggedIn
	public static Result deauthenticate(){

		new AuthService().deauthenticate(session());

		DynamicForm form = form().bindFromRequest();
		String callback = form.get("callback");
		callback = callback == null ? "/" : callback;

		return redirect(callback);
	}

	@LoggedIn
	public static Result loggedin() throws UnAuthorizedIdentityException {

		String name = new AuthService().getIdentity(session());

		return ok(views.html.auth.loggedin.render(name));
	}
}

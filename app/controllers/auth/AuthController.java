package controllers.auth;

import models.applications.AuthService;
import models.domain.model.auth.UnAuthorizedIdentityException;
import models.domain.model.auth.formvalue.Login;
import models.utils.LoggedIn;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import plugins.GuicePlugin;

public class AuthController extends Controller {

	private final static GuicePlugin guice = Play.application().plugin(GuicePlugin.class);

	private final static AuthService authService = guice.get(AuthService.class);

	public static Result login(){

		Form<Login> loginForm = form(Login.class).fill(Login.defaultValue());

		return ok(views.html.auth.login.render(loginForm));
	}

	public static Result authenticate(){

		Form<Login> loginForm = form(Login.class).bindFromRequest();

		if(loginForm.hasErrors()){
			return badRequest(views.html.auth.login.render(loginForm));

		}else{

			try {

				authService.authenticate(loginForm, session());

				return redirect(loginForm.get().callback);

			} catch (UnAuthorizedIdentityException e) {

				loginForm.reject(e.getMessage());
				return unauthorized(views.html.auth.login.render(loginForm));
			}
		}
	}

	@LoggedIn
	public static Result deauthenticate(){

		authService.deauthenticate(session());

		DynamicForm form = form().bindFromRequest();
		String callback = form.get("callback");
		callback = callback == null ? "/" : callback;

		return redirect(callback);
	}

	@LoggedIn
	public static Result loggedin() throws UnAuthorizedIdentityException {

		String identity = authService.getSessionOwner(session()).getIdentifier();

		return ok(views.html.auth.loggedin.render(identity));
	}
}

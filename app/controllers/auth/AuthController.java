package controllers.auth;

import models.applications.AuthService;
import models.domain.model.auth.UnAuthorizedIdentityException;
import models.domain.model.auth.formvalue.Login;
import models.utils.LoggedIn;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class AuthController extends Controller {

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

				new AuthService(Http.Context.current()).authenticate(loginForm, session());

				return redirect(loginForm.get().callback);

			} catch (UnAuthorizedIdentityException e) {

				loginForm.reject(e.getMessage());
				return unauthorized(views.html.auth.login.render(loginForm));
			}
		}
	}

	@LoggedIn
	public static Result deauthenticate(){

		new AuthService(Http.Context.current()).deauthenticate();

		DynamicForm form = form().bindFromRequest();
		String callback = form.get("callback");
		callback = callback == null ? "/" : callback;

		return redirect(callback);
	}

	@LoggedIn
	public static Result loggedin() throws UnAuthorizedIdentityException {

		String identity = new AuthService(Http.Context.current()).getSessionOwner().getIdentifier();

		return ok(views.html.auth.loggedin.render(identity));
	}
}

package controllers.auth;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.applications.AuthService;
import models.domain.model.auth.UnAuthorizedIdentityException;
import models.domain.model.auth.formvalue.Login;
import models.utils.LoggedIn;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import plugins.ThymeleafPlugin;

public class AuthController extends Controller {

	public static Result login() throws FileNotFoundException {

		Form<Login> loginForm = form(Login.class).fill(Login.defaultValue());

		List<ValidationError> errors = new ArrayList<>();
		for (Entry<String, List<ValidationError>> entry : loginForm.errors().entrySet() ){
			errors.addAll(entry.getValue());
		}

		Map<String, Object> vars = new HashMap<>();
		vars.put("form", loginForm);
		vars.put("errors", errors);

		return ThymeleafPlugin.ok("auth/login", vars);
	}

	public static Result authenticate(){

		Form<Login> loginForm = form(Login.class).bindFromRequest();

		if(loginForm.hasErrors()){

			Map<String, Object> vars = new HashMap<>();
			vars.put("form", loginForm);

			return ThymeleafPlugin.badRequest("auth/login", vars);

		}else{

			try {

				new AuthService(Http.Context.current()).authenticate(loginForm);

				return redirect(loginForm.get().callback);

			} catch (UnAuthorizedIdentityException e) {

				loginForm.reject(e.getMessage());

				List<ValidationError> errors = new ArrayList<>();
				for (Entry<String, List<ValidationError>> entry : loginForm.errors().entrySet() ){
					errors.addAll(entry.getValue());
				}

				Map<String, Object> vars = new HashMap<>();
				vars.put("form", loginForm);
				vars.put("errors", errors);

				return ThymeleafPlugin.unauthorized("auth/login", vars);
			}
		}
	}

	@LoggedIn
	public static Result deauthenticate(){

		new AuthService(Http.Context.current()).deauthenticate();

		DynamicForm form = form().bindFromRequest();
		String callback = form.get("callback");
		callback = callback == null ? controllers.auth.routes.AuthController.login().url() : callback;

		return redirect(callback);
	}
}

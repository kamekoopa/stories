package controllers.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.applications.AuthService;
import models.applications.UserService;
import models.domain.model.auth.UnAuthorizedIdentityException;
import models.domain.model.auth.formvalue.Login;
import models.domain.model.user.formvalue.UserRegistration;
import models.exception.DuplicateException;
import play.Play;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import plugins.GuicePlugin;
import plugins.ThymeleafPlugin;

public class UsersController extends Controller {

	private final static GuicePlugin guice = Play.application().plugin(GuicePlugin.class);

	private static final UserService userService = guice.get(UserService.class);


	public static Result input(){

		Form<UserRegistration> registerForm = form(UserRegistration.class).fill(UserRegistration.defaultValue());

		List<ValidationError> errors = new ArrayList<>();
		for (Entry<String, List<ValidationError>> entry : registerForm.errors().entrySet() ){
			errors.addAll(entry.getValue());
		}

		Map<String, Object> vars = new HashMap<>();
		vars.put("form", registerForm);
		vars.put("errors", errors);

		return ThymeleafPlugin.ok("users/input", vars);
	}

	public static Result confirm(){

		Form<UserRegistration> registerForm = form(UserRegistration.class).bindFromRequest();

		List<ValidationError> errors = new ArrayList<>();
		for (Entry<String, List<ValidationError>> entry : registerForm.errors().entrySet() ){
			errors.addAll(entry.getValue());
		}

		Map<String, Object> vars = new HashMap<>();
		vars.put("form", registerForm);
		vars.put("errors", errors);

		return ThymeleafPlugin.ok("users/confirmation", vars);
	}

	public static Result register() throws DuplicateException, UnAuthorizedIdentityException {

		Form<UserRegistration> registerForm = form(UserRegistration.class).bindFromRequest();

		userService.registerNewface(registerForm);

		UserRegistration registered = registerForm.value().get();
		Form<Login> loginForm = form(Login.class).fill(Login.set(registered.username, registered.password, "/dash"));

		new AuthService(Http.Context.current()).authenticate(loginForm);

		return redirect("/dash");
	}
}
package controllers.users;

import java.util.HashMap;
import java.util.Map;

import models.applications.AuthService;
import models.applications.UserService;
import models.domain.model.auth.UnAuthorizedIdentityException;
import models.domain.model.auth.formvalue.Login;
import models.domain.model.user.formvalue.UserRegistration;
import models.exception.DuplicateException;
import models.utils.FormErrors;
import play.Play;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import plugins.GuicePlugin;
import plugins.ThymeleafPlugin;
import static play.data.Form.form;

public class UsersController extends Controller {

	private final static GuicePlugin guice = Play.application().plugin(GuicePlugin.class);

	private static final UserService userService = guice.get(UserService.class);


	public static Result input(){

		Form<UserRegistration> registerForm = form(UserRegistration.class).fill(UserRegistration.defaultValue());

		Map<String, Object> vars = new HashMap<>();
		vars.put("form", registerForm);
		vars.put("errors", new FormErrors(registerForm));

		return ThymeleafPlugin.ok("users/input", vars);
	}

	public static Result confirm(){

		Form<UserRegistration> registerForm = form(UserRegistration.class).bindFromRequest();

		Map<String, Object> vars = new HashMap<>();
		vars.put("form", registerForm);
		vars.put("errors", new FormErrors(registerForm));

		if(registerForm.hasErrors()){

			return ThymeleafPlugin.badRequest("users/input", vars);

		}else{

			if(new UserService().existsUser(registerForm.get().username)){

				registerForm.reject("error.conflict.duplicate_id");

				return ThymeleafPlugin.conflict("users/input", vars);

			}else{
				return ThymeleafPlugin.ok("users/confirmation", vars);
			}
		}
	}

	public static Result register() {

		Form<UserRegistration> registerForm = form(UserRegistration.class).bindFromRequest();

		try {
			userService.registerNewface(registerForm);
		} catch (DuplicateException e) {

			registerForm.reject(e.getMessage());

			Map<String, Object> vars = new HashMap<>();
			vars.put("form", registerForm);
			vars.put("errors", new FormErrors(registerForm));

			return ThymeleafPlugin.conflict("users/input", vars);
		}


		UserRegistration registered = registerForm.value().get();
		Form<Login> loginForm = form(Login.class).fill(Login.set(registered.username, registered.password, "/dash"));

		try {
			new AuthService(Http.Context.current()).authenticate(loginForm);
		} catch (UnAuthorizedIdentityException e) {

			registerForm.reject("error.fail.create_user");

			Map<String, Object> vars = new HashMap<>();
			vars.put("form", registerForm);
			vars.put("errors", new FormErrors(registerForm));

			return ThymeleafPlugin.internalServerError("users/input", vars);
		}

		return redirect("/dash");
	}
}
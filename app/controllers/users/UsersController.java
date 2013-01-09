package controllers.users;

import models.applications.UserService;
import models.domain.model.user.formvalue.UserRegistration;
import models.utils.InjectorWrapper;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.users.confirmation;
import views.html.users.input;

public class UsersController extends Controller {

	private static final UserService userService = InjectorWrapper.get(UserService.class);

	public static Result input(){

		Form<UserRegistration> registerForm = form(UserRegistration.class).fill(UserRegistration.defaultValue());

		return ok(input.render(registerForm));
	}

	public static Result confirm(){

		Form<UserRegistration> registerForm = form(UserRegistration.class).bindFromRequest();

		return ok(confirmation.render(registerForm));
	}

	public static Result register(){

		Form<UserRegistration> registerForm = form(UserRegistration.class).bindFromRequest();

		userService.registerNewface(registerForm);

		return ok(confirmation.render(registerForm));
	}
}
package controllers.users;

import models.applications.AuthService;
import models.applications.UserService;
import models.domain.model.auth.UnAuthorizedIdentityException;
import models.domain.model.auth.formvalue.Login;
import models.domain.model.user.formvalue.UserRegistration;
import models.exception.DuplicateException;
import play.Play;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import plugins.GuicePlugin;
import views.html.users.confirmation;
import views.html.users.input;

public class UsersController extends Controller {

	private final static GuicePlugin guice = Play.application().plugin(GuicePlugin.class);

	private static final UserService userService = guice.get(UserService.class);


	public static Result input(){

		Form<UserRegistration> registerForm = form(UserRegistration.class).fill(UserRegistration.defaultValue());

		return ok(input.render(registerForm));
	}

	public static Result confirm(){

		Form<UserRegistration> registerForm = form(UserRegistration.class).bindFromRequest();

		return ok(confirmation.render(registerForm));
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
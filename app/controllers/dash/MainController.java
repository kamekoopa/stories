package controllers.dash;

import java.util.List;

import models.applications.AuthService;
import models.applications.UserService;
import models.domain.model.auth.UnAuthorizedIdentityException;
import models.domain.model.boxes.Box;
import models.domain.model.boxes.formvalue.BoxCreation;
import models.domain.model.user.User;
import models.exception.NotFoundException;
import models.utils.LoggedIn;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class MainController extends Controller {

	@LoggedIn
	public static Result index() throws UnAuthorizedIdentityException, NotFoundException{

		Form<BoxCreation> form = form(BoxCreation.class).fill(BoxCreation.defaultValue());

		User viewer = new AuthService(Http.Context.current()).getSessionOwner();
		List<Box> boxes = viewer.getMyBoxes();

		return ok(views.html.dash.index.render(form, boxes));
	}

	@LoggedIn
	public static Result createBox() throws UnAuthorizedIdentityException, NotFoundException{

		Form<BoxCreation> newBoxForm = form(BoxCreation.class).bindFromRequest();

		User viewer = new AuthService(Http.Context.current()).getSessionOwner();
		new UserService().addNewBox(viewer.getIdentifier(), newBoxForm);

		return redirect(controllers.dash.routes.MainController.index());
	}
}

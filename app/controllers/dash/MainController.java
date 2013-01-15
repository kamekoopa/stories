package controllers.dash;

import models.applications.AuthService;
import models.applications.CardService;
import models.applications.UserService;
import models.domain.model.auth.UnAuthorizedIdentityException;
import models.domain.model.boxes.BoxList;
import models.domain.model.boxes.formvalue.BoxCreation;
import models.domain.model.cards.formvalue.CardCreation;
import models.domain.model.cards.formvalue.CardFinish;
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
		BoxList boxes = viewer.getMyBoxes();

		return ok(views.html.dash.index.render(form, boxes));
	}

	@LoggedIn
	public static Result createBox() throws UnAuthorizedIdentityException, NotFoundException{

		Form<BoxCreation> newBoxForm = form(BoxCreation.class).bindFromRequest();

		User viewer = new AuthService(Http.Context.current()).getSessionOwner();
		new UserService().addNewBox(viewer.getIdentifier(), newBoxForm);

		return redirect(controllers.dash.routes.MainController.index());
	}

	@LoggedIn
	public static Result createCard() throws UnAuthorizedIdentityException, NotFoundException{

		Form<CardCreation> newCardForm = form(CardCreation.class).bindFromRequest();

		User viewer = new AuthService(Http.Context.current()).getSessionOwner();
		new UserService().addNewCard(viewer.getIdentifier(), newCardForm);

		return redirect(controllers.dash.routes.MainController.index());
	}

	@LoggedIn
	public static Result finishCard() throws NotFoundException {

		Form<CardFinish> finishForm = form(CardFinish.class).bindFromRequest();
		new CardService().finishStory(finishForm);

		return redirect(controllers.dash.routes.MainController.index());
	}
}

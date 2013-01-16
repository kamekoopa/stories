package controllers.dash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import plugins.ThymeleafPlugin;

public class MainController extends Controller {

	@LoggedIn
	public static Result index() throws UnAuthorizedIdentityException, NotFoundException{

		Form<BoxCreation> form = form(BoxCreation.class).fill(BoxCreation.defaultValue());

		User viewer = new AuthService(Http.Context.current()).getSessionOwner();
		BoxList boxes = viewer.getMyBoxes();

		List<ValidationError> errors = new ArrayList<>();
		for (Entry<String, List<ValidationError>> entry : form.errors().entrySet() ){
			errors.addAll(entry.getValue());
		}

		Map<String, Object> vars = new HashMap<>();
		vars.put("form", form);
		vars.put("errors", errors);
		vars.put("boxes", boxes);

		return ThymeleafPlugin.ok("dash/index", vars);
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
		new CardService().changeDoneState(finishForm);

		return redirect(controllers.dash.routes.MainController.index());
	}
}

package controllers.dash;

import java.util.HashMap;
import java.util.Map;

import models.applications.AuthService;
import models.applications.CardService;
import models.applications.UserService;
import models.domain.model.auth.UnAuthorizedIdentityException;
import models.domain.model.boxes.Box;
import models.domain.model.boxes.BoxList;
import models.domain.model.boxes.formvalue.BoxCreation;
import models.domain.model.cards.formvalue.CardCreation;
import models.domain.model.cards.formvalue.CardFinish;
import models.domain.model.user.User;
import models.exception.NotFoundException;
import models.utils.FormErrors;
import models.utils.LoggedIn;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import plugins.ThymeleafPlugin;
import static play.data.Form.form;

public class MainController extends Controller {

	@LoggedIn
	public static Result index() throws UnAuthorizedIdentityException, NotFoundException{

		Form<BoxCreation> boxForm = form(BoxCreation.class).fill(BoxCreation.defaultValue());
		Form<CardCreation> cardForm = form(CardCreation.class).fill(CardCreation.defaultValue());

		User viewer = new AuthService(Http.Context.current()).getSessionOwner();
		BoxList boxes = Box.Finder.list();

		Map<String, Object> vars = new HashMap<>();
		vars.put("boxForm"   , boxForm);
		vars.put("boxErrors" , new FormErrors(boxForm));
		vars.put("cardForm"  , cardForm);
		vars.put("cardErrors", new FormErrors(cardForm));
		vars.put("viewer"    , viewer);
		vars.put("boxes"     , boxes);

		return ThymeleafPlugin.ok("dash/index", vars);
	}

	@LoggedIn
	public static Result createBox() throws UnAuthorizedIdentityException, NotFoundException{

		Form<BoxCreation> newBoxForm = form(BoxCreation.class).bindFromRequest();
		Form<CardCreation> cardForm = form(CardCreation.class).fill(CardCreation.defaultValue());

		User viewer = new AuthService(Http.Context.current()).getSessionOwner();

		if(!newBoxForm.hasErrors()){
			new UserService().addNewBox(viewer.getIdentifier(), newBoxForm);
		}

		BoxList boxes = viewer.getMyBoxes();

		Map<String, Object> vars = new HashMap<>();
		vars.put("boxForm"   , newBoxForm);
		vars.put("boxErrors" , new FormErrors(newBoxForm));
		vars.put("cardForm"  , cardForm);
		vars.put("cardErrors", new FormErrors(cardForm));
		vars.put("boxes"     , boxes);

		if(newBoxForm.hasErrors()){
			return ThymeleafPlugin.badRequest("dash/index", vars);
		}else{
			return ThymeleafPlugin.ok("dash/index", vars);
		}
	}

	@LoggedIn
	public static Result createCard() throws UnAuthorizedIdentityException, NotFoundException{

		Form<BoxCreation> boxForm = form(BoxCreation.class).fill(BoxCreation.defaultValue());
		Form<CardCreation> newCardForm = form(CardCreation.class).bindFromRequest();

		User viewer = new AuthService(Http.Context.current()).getSessionOwner();

		if(!newCardForm.hasErrors()){
			new UserService().addNewCard(viewer.getIdentifier(), newCardForm);
		}

		BoxList boxes = viewer.getMyBoxes();

		Map<String, Object> vars = new HashMap<>();
		vars.put("boxForm"   , boxForm);
		vars.put("boxErrors" , new FormErrors(boxForm));
		vars.put("cardForm"  , newCardForm);
		vars.put("cardErrors", new FormErrors(newCardForm));
		vars.put("boxes"     , boxes);

		if(newCardForm.hasErrors()){
			return ThymeleafPlugin.badRequest("dash/index", vars);
		}else{
			return ThymeleafPlugin.ok("dash/index", vars);
		}
	}

	@LoggedIn
	public static Result finishCard() throws UnAuthorizedIdentityException, NotFoundException{

		Form<CardFinish> finishForm = form(CardFinish.class).bindFromRequest();
		new CardService().changeDoneState(finishForm);

		return index();
	}
}

package models.applications;

import models.domain.model.boxes.Box;
import models.domain.model.boxes.formvalue.BoxCreation;
import models.domain.model.cards.formvalue.CardCreation;
import models.domain.model.user.User;
import models.domain.model.user.UserNotFoundException;
import models.domain.model.user.UserPersistentService;
import models.domain.model.user.UserRetrieveService;
import models.domain.model.user.formvalue.UserRegistration;
import models.exception.DuplicateException;
import models.exception.NotFoundException;
import play.data.Form;

public class UserService {

	public void registerNewface(final Form<UserRegistration> form) throws DuplicateException {

		new UserPersistentService().registerNewface(form.get());
	}

	public void addNewBox(final String creatorIdentifier, final Form<BoxCreation> form) throws NotFoundException {

		User creator = new UserRetrieveService().findByIdentitifier(creatorIdentifier);
		Box newBox = form.get().newBox();

		creator.addBox(newBox);

		new UserPersistentService().update(creator);
	}

	public void addNewCard(final String creatorIdentifier, final Form<CardCreation> form) throws NotFoundException{

		User creator = new UserRetrieveService().findByIdentitifier(creatorIdentifier);

		CardCreation createForm = form.get();

		Box parentBox = creator.getMyBoxes().get(createForm.boxId);
		parentBox.addStoryCard(createForm.newCard(creator));

		new UserPersistentService().update(creator);
	}

	public boolean existsUser(final String identifier){

		try {
			new UserRetrieveService().findByIdentitifier(identifier);
			return true;
		} catch (UserNotFoundException e) {
			return false;
		}
	}
}

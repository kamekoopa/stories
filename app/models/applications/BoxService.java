package models.applications;

import models.domain.model.boxes.Box;
import models.domain.model.boxes.BoxRepository;
import models.domain.model.boxes.formvalue.BoxCreation;
import models.domain.model.user.User;
import play.data.Form;

import com.google.inject.Inject;

public class BoxService {

	@Inject
	private BoxRepository boxRepository;

	public Box createNewBox(final User creator, final Form<BoxCreation> form){

		Box newBox = form.get().newBox(creator);

		return this.boxRepository.create(newBox);
	}
}

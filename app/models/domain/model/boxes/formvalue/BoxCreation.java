package models.domain.model.boxes.formvalue;

import models.domain.model.boxes.Box;
import models.domain.model.user.User;

public class BoxCreation {


	public String boxName;


	public Box newBox(final User creator){

		return Box.Builder.emptyBox(creator, this.boxName);
	}
}

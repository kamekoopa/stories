package models.domain.model.boxes.formvalue;

import models.domain.model.boxes.Box;

public class BoxCreation {


	public String boxName;

	public static BoxCreation defaultValue(){

		BoxCreation defaultValue = new BoxCreation();
		defaultValue.boxName = "";

		return defaultValue;
	}

	public Box newBox(){

		return Box.Builder.emptyBox(this.boxName);
	}
}

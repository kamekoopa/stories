package models.domain.model.boxes;

import models.domain.model.Entity;
import models.domain.model.user.User;
import models.infra.ebean.entity.BoxEbean;

public class Box extends Entity<Long, Box>{

	protected final BoxEbean ebean;

	protected Box(final BoxEbean boxEbean){
		this.ebean = boxEbean;
	}

	@Override
	public Long getIdentifier() {
		return this.ebean.id;
	}

	public String getBoxName(){
		return this.ebean.name;
	}

	public User getOwner(){
		return User.Builder.fromEbean(this.ebean.createdBy);
	}

	public BoxEbean ebean(){
		return this.ebean;
	}


	public static class Builder {

		public static Box emptyBox(final String boxName){

			BoxEbean emptyBox = new BoxEbean();
			emptyBox.id = null;
			emptyBox.name = boxName;

			return new Box(emptyBox);
		}

		public static Box fromEbean(final BoxEbean ebean){

			return new Box(ebean);
		}
	}
}

package models.domain.model.boxes;

import models.domain.model.Entity;
import models.domain.model.user.User;
import models.infra.ebean.entity.Boxes;

public class Box extends Entity<Long, Box>{

	private Long id;

	private String boxName;

	private User owner;

	@Override
	public Long getIdentifier() {
		return this.id;
	}

	public String getBoxName(){
		return this.boxName;
	}

	public User getOwner(){
		return this.owner;
	}


	public static class Builder {

		public static Box emptyBox(final User creator, final String boxName){

			Box emptyBox = new Box();
			emptyBox.id = null;
			emptyBox.boxName = boxName;
			emptyBox.owner = creator;

			return emptyBox;
		}

		public static Box fromOrmEntity(final Boxes boxes){

			Box box = new Box();
			box.id = boxes.id;
			box.boxName = boxes.name;
			//box.owner = User.Builder.fromOrmEntity(boxes.createdBy);

			return box;
		}
	}


}

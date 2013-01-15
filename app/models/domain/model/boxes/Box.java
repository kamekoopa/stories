package models.domain.model.boxes;

import java.util.Iterator;

import models.domain.model.Entity;
import models.domain.model.cards.Card;
import models.domain.model.cards.CardList;
import models.domain.model.user.User;
import models.infra.ebean.entity.BoxEbean;

public class Box extends Entity<Long, Box> implements Iterable<Card> {

	protected final BoxEbean ebean;

	protected final CardList cardList;

	protected Box(final BoxEbean boxEbean){
		this.ebean = boxEbean;
		this.cardList = new CardList(this.ebean.stories);
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

	public Box addStoryCard(Card card){

		this.cardList.add(card);

		return this;
	}

	public BoxEbean ebean(){
		return this.ebean;
	}

	public boolean isEmpty(){
		return this.cardList.isEmpty();
	}

	public Integer getTotalPoint(){
		return this.cardList.getTotalPoint();
	}

	public Integer getCompletedPoint(){
		return this.cardList.getCompletedPoint();
	}

	public Integer getIncompletedPoint(){
		return this.cardList.getIncompletedPoint();
	}

	@Override
	public Iterator<Card> iterator(){
		return this.cardList.iterator();
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

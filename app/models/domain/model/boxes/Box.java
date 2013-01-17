package models.domain.model.boxes;

import java.math.BigDecimal;
import java.util.Iterator;

import models.domain.model.Entity;
import models.domain.model.cards.StoryCard;
import models.domain.model.cards.StoryCardList;
import models.domain.model.user.User;
import models.infra.ebean.entity.BoxEbean;

public class Box extends Entity<Long, Box> implements Iterable<StoryCard> {

	protected final BoxEbean ebean;

	protected final StoryCardList cardList;

	protected Box(final BoxEbean boxEbean){
		this.ebean = boxEbean;
		this.cardList = new StoryCardList(this.ebean.stories);
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

	public Box addStoryCard(StoryCard card){

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

	public Integer getPercentageOfProgress(){

		BigDecimal percent;
		if(this.getTotalPoint().intValue() != 0){
			percent = BigDecimal.valueOf( (this.getCompletedPoint().doubleValue() / this.getTotalPoint().doubleValue()) * 100.0 );
		}else{
			percent = BigDecimal.valueOf(0.0);
		}

		return Integer.valueOf(percent.setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
	}

	@Override
	public Iterator<StoryCard> iterator(){
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

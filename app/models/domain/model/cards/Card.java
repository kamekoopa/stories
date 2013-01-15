package models.domain.model.cards;

import models.domain.model.Entity;
import models.domain.model.user.User;
import models.exception.NotFoundException;
import models.infra.ebean.entity.StoryCardEbean;

public class Card extends Entity<Long, Card>{

	protected final StoryCardEbean ebean;

	protected Card(StoryCardEbean ebean){
		this.ebean = ebean;
	}

	@Override
	public Long getIdentifier() {
		return this.ebean.id;
	}

	public String getFront(){
		return this.ebean.front;
	}

	public String getBack(){
		return this.ebean.back;
	}

	public Integer getPoints(){
		return this.ebean.points;
	}

	public boolean isDone(){
		return this.ebean.done;
	}

	public Card done(){
		this.ebean.done = true;
		return this;
	}

	public static class Builder {

		public static Card newCard(final User creator, final String front, final String back, final Integer points){

			StoryCardEbean newCard = new StoryCardEbean();
			newCard.createdBy = creator.ebean();
			newCard.front = front;
			newCard.back = back;
			newCard.done = false;
			newCard.points = points;

			return new Card(newCard);
		}

		public static Card fromEbean(StoryCardEbean ebean){
			return new Card(ebean);
		}
	}

	public static class Finder {

		public static Card findByIdentifier(Long identifier) throws NotFoundException {

			StoryCardEbean ebean = StoryCardEbean.find.byId(identifier);
			if(ebean == null){
				throw new NotFoundException(identifier);
			}else{
				return new Card(ebean);
			}
		}
	}
}

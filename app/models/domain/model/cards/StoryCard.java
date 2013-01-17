package models.domain.model.cards;

import models.domain.model.Entity;
import models.domain.model.user.User;
import models.exception.NotFoundException;
import models.infra.ebean.entity.StoryCardEbean;

public class StoryCard extends Entity<Long, StoryCard>{

	protected final StoryCardEbean ebean;

	protected StoryCard(StoryCardEbean ebean){
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

	public StoryCard done(){
		this.ebean.done = true;
		return this;
	}

	public StoryCard notDone(){
		this.ebean.done = false;
		return this;
	}
	
	public static class Builder {

		public static StoryCard newCard(final User creator, final String front, final String back, final Integer points){

			StoryCardEbean newCard = new StoryCardEbean();
			newCard.createdBy = creator.ebean();
			newCard.front = front;
			newCard.back = back;
			newCard.done = false;
			newCard.points = points;

			return new StoryCard(newCard);
		}

		public static StoryCard fromEbean(StoryCardEbean ebean){
			return new StoryCard(ebean);
		}
	}

	public static class Finder {

		public static StoryCard findByIdentifier(Long identifier) throws NotFoundException {

			StoryCardEbean ebean = StoryCardEbean.find.byId(identifier);
			if(ebean == null){
				throw new NotFoundException(identifier);
			}else{
				return new StoryCard(ebean);
			}
		}
	}
}

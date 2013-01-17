package models.domain.model.cards;

public class StoryCardPersistentService {

	public void update(StoryCard card){
		card.ebean.update();
	}
}

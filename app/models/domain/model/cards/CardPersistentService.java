package models.domain.model.cards;

public class CardPersistentService {

	public void update(Card card){
		card.ebean.update();
	}
}

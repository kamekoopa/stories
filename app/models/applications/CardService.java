package models.applications;

import models.domain.model.cards.StoryCard;
import models.domain.model.cards.StoryCardPersistentService;
import models.domain.model.cards.formvalue.CardFinish;
import models.exception.NotFoundException;
import play.data.Form;

public class CardService {

	public void changeDoneState(Form<CardFinish> form) throws NotFoundException {

		CardFinish cardFinish = form.get();

		StoryCard card = StoryCard.Finder.findByIdentifier(cardFinish.cardId);
		if(cardFinish.done){
			card.done();
		}else{
			card.notDone();
		}

		new StoryCardPersistentService().update(card);
	}
}

package models.applications;

import models.domain.model.cards.Card;
import models.domain.model.cards.CardPersistentService;
import models.domain.model.cards.formvalue.CardFinish;
import models.exception.NotFoundException;
import play.data.Form;

public class CardService {

	public void finishStory(Form<CardFinish> form) throws NotFoundException {

		CardFinish cardFinish = form.get();

		Card card = Card.Finder.findByIdentifier(cardFinish.cardId);
		card.done();

		new CardPersistentService().update(card);
	}
}

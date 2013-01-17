package models.domain.model.cards.formvalue;

import models.domain.model.cards.StoryCard;
import models.domain.model.user.User;

import org.springframework.format.annotation.NumberFormat;

import play.data.validation.Constraints.Required;

public class CardCreation {

	@Required
	@NumberFormat
	public Long boxId;

	@Required
	public String front;

	@Required
	public String back;

	@Required
	@NumberFormat
	public Integer points;


	public StoryCard newCard(User creator){

		return StoryCard.Builder.newCard(creator, this.front, this.back, this.points);
	}

	public static CardCreation defaultValue(){

		CardCreation def = new CardCreation();
		def.front = "";
		def.back = "";
		def.points = Integer.valueOf(0);

		return def;
	}
}

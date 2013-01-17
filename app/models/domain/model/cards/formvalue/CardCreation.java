package models.domain.model.cards.formvalue;

import models.domain.model.cards.StoryCard;
import models.domain.model.user.User;
import models.utils.IsPositiveNumber;
import play.data.validation.Constraints.Required;

public class CardCreation {

	@Required
	public Long boxId;

	@Required(message = "error.require.storyname")
	public String front;

	@Required(message = "error.require.done_cond")
	public String back;

	@Required(message = "error.require.points")
	@IsPositiveNumber(message = "error.invalid.points_format")
	public String points;


	public StoryCard newCard(User creator){

		return StoryCard.Builder.newCard(creator, this.front, this.back, Integer.valueOf(this.points));
	}

	public static CardCreation defaultValue(){

		CardCreation def = new CardCreation();
		def.front = "";
		def.back = "";
		def.points = "0";

		return def;
	}
}

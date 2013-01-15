package models.domain.model.cards.formvalue;

import org.springframework.format.annotation.NumberFormat;

import play.data.validation.Constraints.Required;

public class CardFinish {

	@Required
	@NumberFormat
	public Long cardId;
}

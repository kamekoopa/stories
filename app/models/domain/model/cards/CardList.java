package models.domain.model.cards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.infra.ebean.entity.StoryCardEbean;

public class CardList implements Iterable<Card> {

	private final List<StoryCardEbean> internalCollection;

	public CardList(List<StoryCardEbean> cardEbeanList){
		this.internalCollection = cardEbeanList;
	}

	public CardList add(Card card){

		this.internalCollection.add(card.ebean);

		return this;
	}

	public Integer getTotalPoint(){

		int cnt = 0;
		for(StoryCardEbean ebean : this.internalCollection){
			cnt += ebean.points.intValue();
		}

		return Integer.valueOf(cnt);
	}

	public Integer getCompletedPoint(){

		int cnt = 0;
		for(StoryCardEbean ebean : this.internalCollection){
			if(ebean.done){
				cnt += ebean.points.intValue();
			}
		}

		return Integer.valueOf(cnt);
	}

	public Integer getIncompletedPoint(){

		int cnt = 0;
		for(StoryCardEbean ebean : this.internalCollection){
			if(!ebean.done){
				cnt += ebean.points.intValue();
			}
		}

		return Integer.valueOf(cnt);
	}

	public boolean isEmpty(){
		return this.internalCollection.isEmpty();
	}

	@Override
	public Iterator<Card> iterator() {

		return this.toDomainList().iterator();
	}

	private List<Card> toDomainList(){

		List<Card> cards = new ArrayList<>();
		for(StoryCardEbean ebean : this.internalCollection){
			cards.add(Card.Builder.fromEbean(ebean));
		}

		return cards;
	}
}

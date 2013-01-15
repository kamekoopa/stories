package models.domain.model.boxes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.infra.ebean.entity.BoxEbean;

public class BoxList implements Iterable<Box> {

	private final List<BoxEbean> internalCollection;

	public BoxList(List<BoxEbean> boxEbeanList){
		this.internalCollection = boxEbeanList;
	}

	public BoxList add(Box box){
		this.internalCollection.add(box.ebean);

		return this;
	}

	@Override
	public Iterator<Box> iterator() {
		return this.toDomainList().iterator();
	}

	private List<Box> toDomainList(){

		List<Box> boxes = new ArrayList<>();
		for(BoxEbean boxEbean : this.internalCollection){
			boxes.add(Box.Builder.fromEbean(boxEbean));
		}

		return boxes;
	}
}

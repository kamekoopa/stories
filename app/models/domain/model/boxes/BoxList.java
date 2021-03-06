package models.domain.model.boxes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.exception.NotFoundException;
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

	public Box get(Long identifier) throws NotFoundException {

		for(BoxEbean ebean : this.internalCollection){
			if(ebean.id.equals(identifier)){
				return Box.Builder.fromEbean(ebean);
			}
		}

		throw new NotFoundException(identifier);
	}

	public boolean isEmpty(){
		return this.internalCollection.isEmpty();
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

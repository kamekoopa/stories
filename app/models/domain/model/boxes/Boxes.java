package models.domain.model.boxes;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Boxes implements Iterable<Box>{

	private Set<Box> internalCollection;

	public void add(final Box box){
		this.internalCollection.add(box);
	}

	@Override
	public Iterator<Box> iterator() {
		return this.internalCollection.iterator();
	}

	public static class Builder {

		public static Boxes empty(){

			Boxes boxes = new Boxes();
			boxes.internalCollection = new HashSet<>();

			return boxes;
		}

		public static Boxes fromOrmEntity(final List<models.infra.ebean.entity.Boxes> boxesList){

			Boxes boxes = empty();

			for(models.infra.ebean.entity.Boxes e : boxesList){
				Box box = Box.Builder.fromOrmEntity(e);
				boxes.internalCollection.add(box);
			}

			return boxes;
		}
	}
}

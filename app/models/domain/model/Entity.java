package models.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;


public abstract class Entity<ID, ME extends Entity<ID, ME>> {

	public abstract ID getIdentifier();

	public boolean isSameIdentity(ME that) {
		return new EqualsBuilder()
			.append(this.getIdentifier(), that.getIdentifier())
			.isEquals();
	}
}

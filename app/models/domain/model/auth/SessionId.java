package models.domain.model.auth;

import models.domain.model.user.User;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

import scala.collection.mutable.StringBuilder;

public final class SessionId {

	private final String sessionId;

	public SessionId(final String sessionId){
		this.sessionId = sessionId;
	}

	public SessionId(final User user){

		String identifier = user.getIdentifier();
		Long now = Long.valueOf(System.currentTimeMillis());

		String concat = new StringBuilder(identifier).append(":").append(now).toString();

		this.sessionId = DigestUtils.sha1Hex(concat);
	}

	@Override
	public int hashCode(){
		return new HashCodeBuilder(17, 31)
			.append(this.sessionId)
			.toHashCode();
	}

	@Override
	public boolean equals(Object object){
		if( object instanceof SessionId){

			SessionId that = (SessionId)object;

			return new EqualsBuilder()
				.append(this.sessionId, that.sessionId)
				.isEquals();

		}else{
			return false;
		}
	}

	@Override
	public String toString(){
		return this.sessionId;
	}
}

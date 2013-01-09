package models.domain.model.auth;

import models.domain.model.user.Password;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

public final class AuthenticationInfo {

	private final String username;

	private final Password password;

	public AuthenticationInfo(final String username, final Password password) {
		this.username = username;
		this.password = password;
	}

	public String username(){
		return this.username;
	}

	public Password password(){
		return this.password;
	}

	@Override
	public int hashCode(){
		return new HashCodeBuilder(17, 31)
		.append(this.username)
		.append(this.password)
		.toHashCode();
	}

	@Override
	public boolean equals(Object object){
		if( object instanceof AuthenticationInfo ){

			AuthenticationInfo that = (AuthenticationInfo)object;

			return new EqualsBuilder()
				.append(this.username, that.username)
				.append(this.password, that.password)
				.isEquals();

		}else{
			return false;
		}
	}
}

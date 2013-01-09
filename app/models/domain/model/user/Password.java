package models.domain.model.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Password {

	private final String digestedPassword;

	public Password(final String digestedPassword){
		this.digestedPassword = digestedPassword;
	}

	public static Password fromRaw(final String password){
		return new Password(DigestUtils.sha1Hex(password));
	}

	@Override
	public int hashCode(){
		return new HashCodeBuilder(17, 31)
			.append(this.digestedPassword).toHashCode();
	}

	@Override
	public boolean equals(Object obj){

		if(obj instanceof Password){

			Password that = (Password)obj;
			return new EqualsBuilder()
				.append(this.digestedPassword, that.digestedPassword)
				.isEquals();
		}else{
			return false;
		}

	}

	@Override
	public String toString(){
		return this.digestedPassword;
	}
}

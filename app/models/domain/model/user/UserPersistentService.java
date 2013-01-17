package models.domain.model.user;

import javax.persistence.PersistenceException;

import models.domain.model.user.formvalue.UserRegistration;
import models.exception.DuplicateException;
import models.utils.ExceptionConverter;

public class UserPersistentService {

	public void registerNewface(final UserRegistration registration) throws DuplicateException {

		User newface = User.Builder.newface(
			registration.username,
			registration.password
		);

		try {

			newface.ebean.save();

		}catch (PersistenceException e) {
			try{
				ExceptionConverter.convertAndThrow(e);
			}catch(DuplicateException d){
				throw new UserDupulicateRagistrationException("error.conflict.duplicate_id");
			}catch(Throwable t){
				throw e;
			}
		}
	}

	public void update(User user){
		user.ebean.update();
	}
}

package models.applications;

import models.domain.model.user.UserPersistentService;
import models.domain.model.user.formvalue.UserRegistration;
import models.exception.DuplicateException;
import play.data.Form;

public class UserService {

	public void registerNewface(final Form<UserRegistration> form) throws DuplicateException {

		new UserPersistentService().registerNewface(form.get());
	}
}

package models.applications;

import models.domain.model.user.User;
import models.domain.model.user.UserRepository;
import models.domain.model.user.formvalue.UserRegistration;
import play.data.Form;

import com.google.inject.Inject;

public class UserService {

	@Inject
	private UserRepository userRepository;

	public void registerNewface(final Form<UserRegistration> form){

		UserRegistration formValue = form.get();
		User newface = User.Builder.newface(formValue.username, formValue.password);

		this.userRepository.register(newface);
	}
}

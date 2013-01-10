package plugins;

import models.domain.model.auth.SessionRepository;
import models.domain.model.auth.SessionRepositoryInMemory;
import models.domain.model.user.UserRepository;
import models.domain.model.user.UserRepositoryInH2;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public final class DefaultModule extends AbstractModule {

	@Override
	protected void configure() {

		this.bind(UserRepository.class)   .to(UserRepositoryInH2.class)       .in(Scopes.SINGLETON);
		this.bind(SessionRepository.class).to(SessionRepositoryInMemory.class).in(Scopes.SINGLETON);
	}
}
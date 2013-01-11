package plugins;

import models.domain.model.auth.SessionPersistentService.SessionPersistentServiceBuilder;
import models.domain.model.auth.SessionRepository;
import models.domain.model.auth.SessionRepositoryInMemory;
import models.domain.model.auth.SessionRetrieveService.SessionRetrieveServiceBuilder;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public final class DefaultModule extends AbstractModule {

	@Override
	protected void configure() {

		this.bind(SessionRepository.class).to(SessionRepositoryInMemory.class).in(Scopes.SINGLETON);

		this.install(new FactoryModuleBuilder().build(SessionPersistentServiceBuilder.class));
		this.install(new FactoryModuleBuilder().build(SessionRetrieveServiceBuilder.class));
	}
}
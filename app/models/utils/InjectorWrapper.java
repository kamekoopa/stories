package models.utils;
import models.domain.model.auth.SessionRepository;
import models.domain.model.auth.SessionRepositoryInMemory;
import models.domain.model.user.UserRepository;
import models.domain.model.user.UserRepositoryInH2;
import play.Configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.Scopes;


public final class InjectorWrapper {

	private static InjectorWrapper instance;

	private final com.google.inject.Injector guiceInjector;

	private InjectorWrapper() throws ReflectiveOperationException {

		String moduleClass = Configuration.root().getString("stories.di.module");

		Module module;
		if(moduleClass == null){

			module = new AbstractModule() {
				@Override protected void configure() {

					this.bind(UserRepository.class)   .to(UserRepositoryInH2.class)       .in(Scopes.SINGLETON);
					this.bind(SessionRepository.class).to(SessionRepositoryInMemory.class).in(Scopes.SINGLETON);
				}
			};

		}else{
			module = (Module) Class.forName(moduleClass).newInstance();
		}

		this.guiceInjector = Guice.createInjector(module);
	}

	public static void init() throws ReflectiveOperationException {
		if(instance == null){
			instance = new InjectorWrapper();
		}
	}

	public static <T> T  get(Class<T> clazz){
		return instance.guiceInjector.getInstance(clazz);
	}
}

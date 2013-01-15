package models.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import models.applications.AuthService;
import models.domain.model.auth.UnAuthorizedIdentityException;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.With;
import controllers.auth.routes;

@With(LoggedIn.LoggedInAction.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggedIn {

	static class LoggedInAction extends Action<LoggedIn>{

		@Override
		public Result call(Context ctx) throws Throwable {

			try {

				new AuthService(ctx).getSessionOwnerWithRegenerateSessionId();

				return this.delegate.call(ctx);

			} catch (UnAuthorizedIdentityException e) {
				return redirect(routes.AuthController.login());
			}
		}
	}
}

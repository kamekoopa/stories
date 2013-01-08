package models.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import models.domain.user.AuthService;
import models.domain.user.UnAuthorizedIdentityException;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.With;
import controllers.routes;

@With(LoggedIn.LoggedInAction.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggedIn {

	static class LoggedInAction extends Action<LoggedIn>{

		static final AuthService authService = new AuthService();

		@Override
		public Result call(Context ctx) throws Throwable {

			try {

				authService.getIdentityWithSessionIdRegenerate(ctx.session());

				return this.delegate.call(ctx);

			} catch (UnAuthorizedIdentityException e) {
				return redirect(routes.Application.index());
			}
		}
	}
}

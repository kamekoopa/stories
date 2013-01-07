package models.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import models.domain.cache.OnMemoryCache;
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

		@Override
		public Result call(Context ctx) throws Throwable {

			String token =ctx.session().get("login_token");

			if(token == null || token.equals("")){
				return redirect(routes.Application.index());

			}else{

				String auth = OnMemoryCache.instance.get(token);
				System.out.println(auth);
				if(auth == null || auth.equals("")){
					return redirect(routes.Application.index());

				}else{
					return this.delegate.call(ctx);
				}
			}
		}
	}
}

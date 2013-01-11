package models.applications;

import models.domain.model.auth.AuthenticationInfo;
import models.domain.model.auth.ClientSession;
import models.domain.model.auth.SessionPersistentService;
import models.domain.model.auth.SessionPersistentService.SessionPersistentServiceBuilder;
import models.domain.model.auth.SessionRetrieveService;
import models.domain.model.auth.SessionRetrieveService.SessionRetrieveServiceBuilder;
import models.domain.model.auth.UnAuthorizedIdentityException;
import models.domain.model.auth.formvalue.Login;
import models.domain.model.user.User;
import play.Play;
import play.data.Form;
import play.mvc.Http.Context;
import play.mvc.Http.Session;
import plugins.GuicePlugin;

public class AuthService {

	private final SessionPersistentService sessionPersistentService;

	private final SessionRetrieveService sessionRetrieveService;

	private final ClientSession clientSession;


	public AuthService(final Context ctx){

		this.clientSession = ClientSession.get(ctx);

		SessionPersistentServiceBuilder pbuilder =
			Play.application().plugin(GuicePlugin.class).get(SessionPersistentServiceBuilder.class);
		this.sessionPersistentService = pbuilder.create(this.clientSession);

		SessionRetrieveServiceBuilder rbuilder =
			Play.application().plugin(GuicePlugin.class).get(SessionRetrieveServiceBuilder.class);
		this.sessionRetrieveService = rbuilder.create(this.clientSession);
	}

	public void authenticate(final Form<Login> form, final Session sessionCookie) throws UnAuthorizedIdentityException {

		AuthenticationInfo authInfo = form.get().getAuthInfo();
		this.sessionPersistentService.createSession(authInfo);
	}


	public void deauthenticate(){
		this.sessionPersistentService.destroySession();
	}


	public User getSessionOwner() throws UnAuthorizedIdentityException {
		return this.sessionRetrieveService.getSessionOwner();
	}


	public User getSessionOwnerWithRegenerateSessionId() throws UnAuthorizedIdentityException {
		return this.sessionRetrieveService.getSessionOwnerWithSessIdRegenerate();
	}
}

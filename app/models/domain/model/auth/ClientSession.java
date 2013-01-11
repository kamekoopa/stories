package models.domain.model.auth;

import play.mvc.Http.Context;

public class ClientSession {

	private static ClientSession me = null;
	public static ClientSession get(Context ctx){
		if(me == null){
			me = new ClientSession(ctx);
		}

		return me;
	}

	private final Context ctx;
	private ClientSession(Context ctx){
		this.ctx = ctx;
	}

	public void markAuthenticated(ServerSession serverSession){
		this.ctx.session().put(
			"sessid",
			serverSession.getIdentifier().toString()
		);
	}

	public SessionId getSessionId(){
		return new SessionId(this.ctx.session().get("sessid"));
	}

	public void removeAuthenticated(){

		this.ctx.session().remove("sessid");
	}
}

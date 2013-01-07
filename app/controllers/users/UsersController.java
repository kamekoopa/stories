package controllers.users;

import models.domain.user.AccountService;
import models.exception.ApplicationException;
import models.utils.ReturnableAsRestJsonResult;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;

import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class UsersController extends Controller {

	private static final AccountService accountService = new AccountService();

	@BodyParser.Of(BodyParser.Json.class)
	public static Result authenticate(){
		final JsonNode payload = request().body().asJson();

		return execute(new Execution(){
			@Override public ImmutablePair<Integer, ? extends JsonNode> invoke() throws Throwable {

				if(payload == null){
					throw new ApplicationException(BAD_REQUEST, "requested content is not json");

				}else if(payload.get("name").asText() == null){
					throw new ApplicationException(BAD_REQUEST, "name undefined");

				}else{

					String token = accountService.authenticate(payload.get("name").asText());
					session("login_token", token);

					return ImmutablePair.of(
						OK,
						JsonNodeFactory.instance.objectNode()
					);
				}
			}
		});
	}

	public static Result logout(){
		return execute(new Execution(){
			@Override public ImmutablePair<Integer, ? extends JsonNode> invoke() throws Throwable {

				String token = session("login_token");
				accountService.logout(token);

				return ImmutablePair.of(
					OK,
					JsonNodeFactory.instance.objectNode()
				);
			}
		});
	}

	public static Result get(final Long id) {

		return execute(new Execution(){
			@Override public ImmutablePair<Integer, ? extends JsonNode> invoke() throws Throwable {
				return ImmutablePair.of(
					OK,
					accountService.obtainById(id).toJson()
				);
			}
		});
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result create() {
		final JsonNode payload = request().body().asJson();

		return execute(new Execution(){
			@Override public ImmutablePair<Integer, ? extends JsonNode> invoke() throws Throwable {

				if(payload == null){
					throw new ApplicationException(BAD_REQUEST, "requested content is not json");

				}else if(payload.get("name").asText() == null){
					throw new ApplicationException(BAD_REQUEST, "name undefined");

				}else{
					return ImmutablePair.of(
						CREATED,
						accountService.create(payload.get("name").asText()).toJson()
					);
				}
			}
		});
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result update(final Long id) {
		final JsonNode payload = request().body().asJson();

		return execute(new Execution(){
			@Override public ImmutablePair<Integer, ? extends JsonNode> invoke() throws Throwable {

				if(payload == null){
					throw new ApplicationException(BAD_REQUEST, "requested content is not json");

				}else if(payload.get("name").asText() == null){
					throw new ApplicationException(BAD_REQUEST, "name undefined");

				}else{
					return ImmutablePair.of(
						CREATED,
						accountService.modifyByJson(id, payload).toJson()
					);
				}
			}
		});
	}

	public static Result remove(final Long id) {
		return execute(new Execution(){
			@Override public ImmutablePair<Integer, ? extends JsonNode> invoke() throws Throwable {
				accountService.removeAccount(id);
				return ImmutablePair.of(
					OK,
					JsonNodeFactory.instance.objectNode()
				);
			}
		});
	}

	private static Result execute(Execution execution){

		ReturnableAsRestJsonResult result;
		try {
			final ImmutablePair<Integer, ? extends JsonNode> tupple = execution.invoke();
			result = new ReturnableAsRestJsonResult() {
				@Override public int getStatusCode() { return tupple.left; }
				@Override public JsonNode toResultJson() { return tupple.right; }
			};
		} catch(ApplicationException e){
			result = e;
		} catch (Throwable e) {
			result = new ApplicationException(Http.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}

		return status(result.getStatusCode(), result.toResultJson());
	}

	private static interface Execution {
		public ImmutablePair<Integer, ? extends JsonNode> invoke() throws Throwable;
	}
}
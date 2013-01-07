package models.domain.user;

import models.orm.ebeans.Users;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

public class User {

	final Users users;

	User(Users users){
		this.users = users;
	}

	protected User setFromJson(JsonNode json){

		return this;
	}


	public JsonNode toJson(){

		ObjectNode object = JsonNodeFactory.instance.objectNode();
		object.put("id"  , this.users.id);
		object.put("name", this.users.name);

		return object;
	}
}

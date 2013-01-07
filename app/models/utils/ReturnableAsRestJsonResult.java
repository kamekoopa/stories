package models.utils;

import org.codehaus.jackson.JsonNode;

public interface ReturnableAsRestJsonResult {

	public int getStatusCode();

	public JsonNode toResultJson();
}

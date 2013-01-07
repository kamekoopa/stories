package models.exception;

import models.utils.ReturnableAsRestJsonResult;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

public class ApplicationException extends BusinessException implements ReturnableAsRestJsonResult {
	private static final long serialVersionUID = -7314076663074131527L;

	private final int httpStatus;

	public ApplicationException(final int httpStatus, final String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

	@Override
	public int getStatusCode() {
		return this.httpStatus;
	}

	@Override
	public JsonNode toResultJson() {

		ObjectNode error = JsonNodeFactory.instance.objectNode();
		error.put("message", this.getMessage());

		ObjectNode result = JsonNodeFactory.instance.objectNode();
		result.put("status", this.getStatusCode());
		result.put("error" , error);

		return result;
	}
}

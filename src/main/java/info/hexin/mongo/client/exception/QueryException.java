package info.hexin.mongo.client.exception;

public class QueryException extends RuntimeException {

	private static final long serialVersionUID = -283709104191354100L;

	public QueryException(String message, Throwable cause) {
		super(message, cause);
	}

	public QueryException(String message) {
		super(message);
	}

	public QueryException(Throwable cause) {
		super(cause);
	}

	public QueryException() {
	}
}

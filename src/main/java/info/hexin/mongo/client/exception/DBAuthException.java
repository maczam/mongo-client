package info.hexin.mongo.client.exception;

/**
 * 
 * @author hexin
 * 
 */
public class DBAuthException extends RuntimeException {

	private static final long serialVersionUID = 2182903056988873085L;

	public DBAuthException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBAuthException(String message) {
		super(message);
	}

	public DBAuthException(Throwable cause) {
		super(cause);
	}

	public DBAuthException() {
	}
}

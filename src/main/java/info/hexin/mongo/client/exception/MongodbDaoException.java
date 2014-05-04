package info.hexin.mongo.client.exception;

/**
 * 
 * @author hexin
 * 
 */
public class MongodbDaoException extends RuntimeException {

	private static final long serialVersionUID = 4278849035327849014L;

	public MongodbDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public MongodbDaoException(String message) {
		super(message);
	}

	public MongodbDaoException(Throwable cause) {
		super(cause);
	}

	public MongodbDaoException() {
	}
}

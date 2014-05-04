package info.hexin.mongo.client.exception;

/**
 * 索引部分 异常
 * 
 * @author hexin
 * 
 */
public class IndexException extends RuntimeException {

	private static final long serialVersionUID = -1386824261504580073L;

	public IndexException(String message, Throwable cause) {
		super(message, cause);
	}

	public IndexException(String message) {
		super(message);
	}

	public IndexException(Throwable cause) {
		super(cause);
	}

	public IndexException() {
	}
}

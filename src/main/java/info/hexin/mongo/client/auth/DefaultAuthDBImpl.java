package info.hexin.mongo.client.auth;

/**
 * 默认
 * 
 * @author hexin
 * 
 */
public class DefaultAuthDBImpl implements AuthDB {
	public boolean authCollection(String collectionName) {
		return true;
	}
}

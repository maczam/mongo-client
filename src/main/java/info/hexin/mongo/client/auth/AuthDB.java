package info.hexin.mongo.client.auth;

/**
 * mongodb默认没有对表级别的控制,所以增加该回调接口.使用给AbstractMongoDao注入。
 * 
 * @author hexin
 * 
 */
public interface AuthDB {
	
	boolean authCollection(String collectionName);
}

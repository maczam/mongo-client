package info.hexin.mongo.client.callback;

import com.mongodb.DBCollection;

/**
 * Collection回调
 * 
 * @author hexin
 * 
 */
public interface CollectionCallback<T> {

	public T doInCollection(DBCollection collection);
}

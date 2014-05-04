package info.hexin.mongo.client.callback;

import com.mongodb.DB;

public interface DBCallBack<T> {
	public T doInDB(DB db);
}

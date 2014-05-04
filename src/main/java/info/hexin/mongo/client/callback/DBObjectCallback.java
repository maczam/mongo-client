package info.hexin.mongo.client.callback;

import com.mongodb.DBObject;

/**
 * 
 * DBObject 封装回调，
 * 
 * @see MapDBObjectCallbackImpl
 * 
 * @author hexin
 * 
 */
public interface DBObjectCallback<T> {

	/**
	 * 
	 * 将mongodb默认提供的DBObject封装成不通类型的pojo类
	 * 
	 * 
	 * @param dbObject
	 *            mongo 中的DBObject
	 * @return 封装后自定义的对象
	 */
	T processDBObject(DBObject dbObject);
}

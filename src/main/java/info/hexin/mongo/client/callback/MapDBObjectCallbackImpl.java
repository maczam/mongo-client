package info.hexin.mongo.client.callback;

import com.mongodb.DBObject;

import java.util.Map;
/**
 * 
 * @author hexin
 *
 */
public class MapDBObjectCallbackImpl implements DBObjectCallback<Map<String,Object>> {

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> processDBObject(DBObject dbObject) {
		if(dbObject == null){
			return null;
		}
		return dbObject.toMap();
	}
}

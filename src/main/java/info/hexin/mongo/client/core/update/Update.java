package info.hexin.mongo.client.core.update;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import info.hexin.mongo.client.core.mapper.OP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * update条件封装
 * @author hexin
 *
 */
public class Update {

	/**
	 * 单实例会保存上次的状态
	 */
	// public static final Update $ = $();
	private Map<String, DBObject> u = null;

	public Update() {
		u = new HashMap<String, DBObject>();
	}

	public static Update $() {
		return new Update();
	}

	// /// set ////////
	/**
	 * set 更新
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Update set(String key, Object value) {
		return put(OP.SET, key, value);
	}

	public Update set(Map<String, Object> map) {
		return put(OP.SET, map);
	}

	// /// unset //////////
	/**
	 * 删除字段
	 * 
	 * @param key
	 * @return
	 */
	public Update unset(String key) {
		return put(OP.UNSET, key, 1);
	}

	public Update unset(Map<String, Object> map) {
		return put(OP.UNSET, map);
	}

	// /// inc /////
	public Update inc(String key) {
		return put(OP.INC, key, 1);
	}

	public Update inc(String key, int n) {
		return put(OP.INC, key, n);
	}

	// / push ///
	public Update push(String key, Object value) {
		return put(OP.PUSH, key, value);
	}

	// / pushAll ///
	public Update pushAll(String key, Object... value) {
		return put(OP.PUSHALL, key, value);
	}

	public Update pushAll(String key, List<Object> values) {
		return put(OP.PUSHALL, key, values.toArray());
	}

	// / addToSet //
	public Update addToSet(String key, Object... value) {
		return putEatch(OP.ADDTOSET, key, value);
	}
	public Update addToSet(String key, List<Object> values) {
		return put(OP.ADDTOSET, key, values.toArray());
	}
	
	/////pull ////
	public Update pull(String key, Object value) {
		return put(OP.PULL, key, value);
	}
	
	/////pullAll ////
	public Update pullAll(String key, Object... value) {
		return put(OP.PULLAll, key, value);
	}
	public Update pullAll(String key, List<Object> values) {
		return put(OP.PULLAll, key, values.toArray());
	}
	

	private Update put(String op, String k, Object v) {
		DBObject setDbObject = null;
		if (u.containsKey(op)) {
			setDbObject = u.get(op);
		} else {
			setDbObject = new BasicDBObject();
		}
		setDbObject.put(k, v);
		u.put(op, setDbObject);
		return this;
	}

	private Update put(String op, Map<String, Object> map) {
		DBObject setDbObject = null;
		if (u.containsKey(op)) {
			setDbObject = u.get(op);
		} else {
			setDbObject = new BasicDBObject();
		}

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			setDbObject.put(entry.getKey(), entry.getValue());
		}
		u.put(op, setDbObject);
		return this;
	}

	/**
	 * 只为addtoset
	 * 
	 * @param addtoset
	 * @param key
	 * @param value
	 * @return
	 */
	private Update putEatch(String addtoset, String key, Object[] value) {
		DBObject setDbObject = null;
		if (u.containsKey(addtoset)) {
			setDbObject = u.get(addtoset);
		} else {
			setDbObject = new BasicDBObject();
		}
		DBObject eachDbObject = new BasicDBObject(OP.EACH, value);
		setDbObject.put(key, eachDbObject);
		u.put(addtoset, setDbObject);
		return this;
	}

	public DBObject getUdateObject() {
		return new BasicDBObject(u);
	}
}

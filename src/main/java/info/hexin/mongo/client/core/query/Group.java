package info.hexin.mongo.client.core.query;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.HashMap;
import java.util.Map;

public class Group {

	private DBObject dboKeys;
	private String keyFunction;
	private Map<String, Object> initial = new HashMap<String, Object>();
	private String reduce;
	private String finalize;

	private Group(String... keys) {
		DBObject dbo = new BasicDBObject();
		for (String key : keys) {
			dbo.put(key, 1);
		}
		dboKeys = dbo;
	}

	private Group(String key, boolean isKeyFunction) {
		DBObject dbo = new BasicDBObject();
		if (isKeyFunction) {
			keyFunction = key;
		} else {
			dbo.put(key, 1);
			dboKeys = dbo;
		}
	}

	public static Group keyFunction(String key) {
		return new Group(key, true);
	}

	public static Group key(String... keys) {
		return new Group(keys);
	}

	public Group initial(String key, Object value) {
		initial.put(key, value);
		return this;
	}

	public Group reduce(String reduceFunction) {
		reduce = reduceFunction;
		return this;
	}

	public Group finalizeFunction(String finalizeFunction) {
		finalize = finalizeFunction;
		return this;
	}

	public DBObject getGroupObject() {
		BasicDBObject dbo = new BasicDBObject();
		if (dboKeys != null) {
			dbo.put("key", dboKeys);
		}
		if (keyFunction != null) {
			dbo.put("$keyf", keyFunction);
		}
		dbo.put("$reduce", reduce);
		dbo.put("initial", initial);
		if (finalize != null) {
			dbo.put("finalize", finalize);
		}
		return dbo;
	}
	
}

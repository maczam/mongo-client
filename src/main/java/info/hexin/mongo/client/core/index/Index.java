package info.hexin.mongo.client.core.index;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import info.hexin.mongo.client.core.mapper.OP;
import info.hexin.mongo.client.exception.IndexException;
import info.hexin.mongo.client.util.Strings;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 索引操作工具
 * 
 * @author hexin
 * 
 */
public class Index {
	private final Map<String, Integer> fieldMap = new LinkedHashMap<String, Integer>();

	public enum Duplicates {
		RETAIN, DROP
	}

	private String name;

	private boolean unique = false;

	private boolean dropDuplicates = false;
	private String dropName;

	/**
	 * 是否为稀疏索引
	 * <p>
	 * 使用稀疏索引进行查询的时候，某些由于缺失了字段的文档记录可能不会被返回，这是由于稀疏索引子返回被索引了的字段
	 */
	private boolean sparse = false;

	private Index() {
	}

	public static Index $() {
		return new Index();
	}

	public Index on(String key) {
		return onAsc(key, OP.ASC);
	}

	public Index drop(String key) {
		this.dropName = key;
		return this;
	}

	public Index onAsc(String key, Integer order) {
		fieldMap.put(key, order);
		return this;
	}

	public Index onDesc(String key) {
		fieldMap.put(key, OP.DESC);
		return this;
	}

	public Index named(String name) {
		if (Strings.isNotBlank(this.name)) {
			throw new IndexException("已经创建别名 name >>> " + this.name + "，不能在创建 name >>>> " + name + "!");
		}
		this.name = name;
		return this;
	}

	public Index unique() {
		this.unique = true;
		return this;
	}

	public Index sparse() {
		this.sparse = true;
		return this;
	}

	public Index unique(Duplicates duplicates) {
		if (duplicates == Duplicates.DROP) {
			this.dropDuplicates = true;
		}
		return unique();
	}

	public DBObject getIndexKeys() {
		DBObject dbo = new BasicDBObject();
		for (String k : fieldMap.keySet()) {
			dbo.put(k, (fieldMap.get(k) == 1 ? 1 : -1));
		}
		return dbo;
	}

	public String dropName() {
		return dropName;
	}

	public DBObject getIndexOptions() {
		if (name == null && !unique) {
			return null;
		}
		DBObject dbo = new BasicDBObject();
		if (name != null) {
			dbo.put("name", name);
		}
		if (unique) {
			dbo.put("unique", true);
		}
		if (dropDuplicates) {
			dbo.put("dropDups", true);
		}
		if (sparse) {
			dbo.put("sparse", true);
		}
		return dbo;
	}

	@Override
	public String toString() {
		return String.format("Index: %s - Options: %s", getIndexKeys(), getIndexOptions());
	}
}

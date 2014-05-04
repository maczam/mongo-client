package info.hexin.mongo.client.auth;

import info.hexin.mongo.client.util.Conf;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 客户使用
 * 
 * @author hexin
 * 
 */
public class ClientAuthDBImpl implements AuthDB {

	private static final Set<String> collectNameSet = init();

	private static Set<String> init() {
		Conf conf = Conf.sysConf();
		String tables = conf.getString("tables");
		Set<String> temp = new HashSet<String>();
		for (String table : tables.split(",")) {
			temp.add(table);
		}
		Set<String> collectNameSet = Collections.unmodifiableSet(temp);
		return collectNameSet;
	}

	public boolean authCollection(String collectionName) {
		if (collectNameSet.contains(collectionName)) {
			return true;
		} else {
			return false;
		}
	}
}

package info.hexin.mongo.client.core.query;

import java.util.HashMap;
import java.util.Map;

/**
 * 排序相关
 * 
 * @author hexin
 * 
 */
public class Sort {
	private Map<String, Integer> orderMap = new HashMap<String, Integer>();

	public void on(String key, Integer order) {
		orderMap.put(key, order);
	}
	
	public Map<String, Integer> orderMap(){
		return this.orderMap;
	}
}

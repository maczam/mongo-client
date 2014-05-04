package info.hexin.mongo.client.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author hexin
 * 
 */
public class Maps {
	/**
	 * 经String封装成Map
	 * @param s
	 * @return
	 */
	public static Map<String, String> of(String... s) {
		Map<String, String> map = new HashMap<String, String>();
		if (s.length % 2 == 0) {
			for (int i = 0; i < s.length; i = i + 2) {
				map.put(s[i], s[i + 1]);
			}
		} else {
			throw new RuntimeException("args counts is be even number ");
		}
		return map;
	}

	/**
	 * 经Ojbect封装成Map
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, Object> ofObject(Object... o) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (o.length % 2 == 0) {
			for (int i = 0; i < o.length; i = i + 2) {
				map.put(o[i].toString(), o[i + 1]);
			}
		} else {
			throw new RuntimeException("args counts is be even number ");
		}
		return map;
	}

	public static String get(Map<String, Object> map, String key) {
		if (map == null) {
			return "";
		}
		return String.valueOf(map.get(key));
	}

	public static String get(List<Map<String, Object>> l, String key) {
		if (l != null && l.size() > 0) {
			return get(l.get(0), key);
		} else {
			return "";
		}
	}
}

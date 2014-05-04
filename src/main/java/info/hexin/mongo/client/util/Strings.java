package info.hexin.mongo.client.util;

public class Strings {

	/**
	 * <p>
	 * <li>null === true
	 * <li>"" === true
	 * <li>" " === false
	 * </p>
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNull(String s) {
		return null == s || s.length() == 0;
	}

	public static String notNull(String s) {
		return (null == s || "null".equalsIgnoreCase(s)) ? "" : s;
	}

	/**
	 * <p>
	 * <li>null === false
	 * <li>"" === false
	 * <li>" " === true
	 * </p>
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotNull(String s) {
		return null != s && s.length() > 0;
	}

	public static boolean isBlank(String s) {
		return null == s || "null".equalsIgnoreCase(s) || "\nnull".equals(s) || s.trim().length() == 0;
	}

	public static boolean isBlank(Object s) {
		return null == s || s.toString().trim().length() == 0;
	}

	public static boolean isNotBlank(String s) {
		return !isBlank(s);
	}

	public static boolean isNotBlankJson(String s) {
		return isNotBlank(s) && !s.replaceAll(" ", "").equalsIgnoreCase("{}");
	}

	public static boolean isNotBlank(Object s) {
		return null != s && s.toString().trim().length() > 0;
	}

	public static String of(Object o) {
		return isBlank(o) ? "" : o.toString();
	}

	public static int toInt(String i) {
		return Integer.valueOf(i);
	}

	public static int toInt(Object i) {
		return Integer.valueOf(i.toString());
	}

	public static long toLong(Object object) {
		return Long.valueOf(object.toString());
	}
}

package info.hexin.mongo.client.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Dates {

	private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date now() {
		return new Date();
	}

	public static String nowTime() {
		return timeFormat.format(new Date());
	}

	public static String nowTime(Date date) {
		return timeFormat.format(date);
	}

	/**
	 * 格式化 时间字符串
	 * 
	 * @param timeStr
	 * @return
	 */
	public static String formatDate(String timeStr, String format) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return sdf.format(date);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.US);
		String result = "";
		try {
			Date date = sdf.parse(timeStr);
			sdf.applyPattern(format);
			result = sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Date yestoday() {
		Calendar yestoday = Calendar.getInstance();
		// cal1.set(2000,1,29);
		yestoday.add(Calendar.DATE, -1);
//		yestoday.set(Calendar.HOUR_OF_DAY, 0);
//		yestoday.set(Calendar.MINUTE, 0);
//		yestoday.add(Calendar.SECOND, 0);
		return yestoday.getTime();
	}
}

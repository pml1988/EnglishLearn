package com.android.english.learn.utils;

import java.util.Calendar;

/**
 *
 * 作用：时间日历类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class TimeUtils {
	// 获得当天0点时间
	public static long getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return  (cal.getTimeInMillis() );
	}

	// 获得当天24点时间
	public static long getTimesnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (cal.getTimeInMillis() );
	}
}

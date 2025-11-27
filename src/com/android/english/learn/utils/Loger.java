package com.android.english.learn.utils;

import com.android.english.learn.ConfigProperty;

import android.util.Log;

/**
 *
 * 作用：日志类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class Loger {
	private static boolean CLOSE = ConfigProperty.LOGER_CLOSE;

	public static void v(String msg) {
		if (CLOSE)
			return;

		final StackTraceElement[] stack = new Throwable().getStackTrace();
		final int i = 1;
		final StackTraceElement ste = stack[i];

		Log.v(ste.getClassName(),
				String.format("[%s][%d]%s", ste.getMethodName(),
						ste.getLineNumber(), msg));
	}

	public static void d(String msg) {
		if (CLOSE) {
			return;
		}

		final StackTraceElement[] stack = new Throwable().getStackTrace();
		final int i = 1;
		final StackTraceElement ste = stack[i];
//		System.out.println(msg);
		Log.d(ste.getClassName(),
				String.format("[%s][%d]%s", ste.getMethodName(),
						ste.getLineNumber(), msg));
	}

	public static void i(String msg) {
		if (CLOSE)
			return;

		final StackTraceElement[] stack = new Throwable().getStackTrace();
		final int i = 1;
		final StackTraceElement ste = stack[i];

		Log.i(ste.getClassName(),
				String.format("[%s][%d]%s", ste.getMethodName(),
						ste.getLineNumber(), msg));
	}

	public static void w(String msg) {
		if (CLOSE)
			return;

		final StackTraceElement[] stack = new Throwable().getStackTrace();
		final int i = 1;
		final StackTraceElement ste = stack[i];

		Log.w(ste.getClassName(),
				String.format("[%s][%d]%s", ste.getMethodName(),
						ste.getLineNumber(), msg));
	}

	public static void e(String msg) {
		if (CLOSE)
			return;

		final StackTraceElement[] stack = new Throwable().getStackTrace();
		final int i = 1;
		final StackTraceElement ste = stack[i];

		Log.e(ste.getClassName(),
				String.format("[%s][%d]%s", ste.getMethodName(),
						ste.getLineNumber(), msg));
	}

}

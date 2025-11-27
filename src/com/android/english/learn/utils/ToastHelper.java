package com.android.english.learn.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.Toast;

/**
 *
 * 作用：Toast帮助类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class ToastHelper {
	Toast toast;
	static List<Context> ctxList=new ArrayList<Context>();
	public static void showToast(Context context, int resId) {
		showToast(context, resId, false);
	}

	public static void showToast(Context context, int resId,
			boolean durationLong) {
		int duration;
		if (durationLong) {
			duration = Toast.LENGTH_LONG;
		} else {
			duration = Toast.LENGTH_SHORT;
		}
		Toast toast= Toast.makeText(context, "", duration);
		toast.setText(context.getResources().getString(resId));
		toast.show();
	}

	public static void showToast(Context context, String msg) {
		showToast(context, msg, false);
	}

	public static void showToast(Context context, String msg,
			boolean durationLong) {
		int duration;
		if (durationLong) {
			duration = Toast.LENGTH_LONG;
		} else {
			duration = Toast.LENGTH_SHORT;
		}
		Toast toast= Toast.makeText(context, "", duration);
		toast.setText(msg);
		toast.show();
	}
}

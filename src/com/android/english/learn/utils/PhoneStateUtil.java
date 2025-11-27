package com.android.english.learn.utils;

import android.os.Environment;

/**
 *
 * 作用：手机状态常用类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class PhoneStateUtil {

	/**
	 * Whether the memory card in writing form
	 *
	 * @Title: exterStorageReady
	 * @return true,false
	 */
	public static boolean exterStorageReady() {
		// Get SdCard state
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				&& Environment.getExternalStorageDirectory().canWrite()) {
			return true;

		}
		return false;
	}
}

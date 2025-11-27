package com.android.english.learn.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * 作用：字符串操作常用类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class StringHelper {

	/**
	 * 输入流读取字符串
	 * @param inputStream
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String readTextFile(InputStream inputStream) throws UnsupportedEncodingException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
			return "";
		}
		return outputStream.toString("gbk");

	}
}

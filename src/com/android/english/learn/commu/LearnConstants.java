package com.android.english.learn.commu;
/**
 *
 * 作用：常用常量类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class LearnConstants {
	/**
	 * 存储方式：
	 * 1.一些简单的数据存储使用SharedPreferences
	 * 2.词库使用Sqlite 存储
	 */
	public final static String WORD_LIBRARY_SWITCH="word_library_switch";//词库选择

	/**
	 *单词学习模式：
	 *1.查看模式为返回
	 *2.学习模式为连续的模式
	 **/
	public final static int VIEW_WORD = 1;
	public final static int LEARN_WORD = 2;

	/**
	 * 单词学习方式：
	 * 1.顺序
	 * 2.乱序
	 */
	public final static int SEQ_ORDER=1;
	public final static int RANDOM_ORDER=2;

	/**
	 * 是否加载单词
	 */
	public final static String IS_LOAIDNG_WORD="is_loading_word";
	/**
	 * 最后打开程序的时间
	 */
	public final static String LAST_OPEN_APP_TIME="last_open_app_time";
}

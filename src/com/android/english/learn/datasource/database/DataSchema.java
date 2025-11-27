package com.android.english.learn.datasource.database;

import android.provider.BaseColumns;

/**
 *
 * 作用：数据库字段的建立，建表语句
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public interface  DataSchema {
	/**
	 * 数据库版本号
	 */
	public static final int DATABASE_VERSION = 3;
	public static final int DATABASE_VERSION_1 = 1;
	/**
	 * 删除语句
	 */
	public static final String DROP_TABLE = "drop table if exists ";
	/**
	 * 数据库名
	 */
	public static final String DATABASE_NAME = "learn_english.db ";

	/**
	 *
	 * 作用：建立单词表字段，建表语句
	 * @author 顾美琴
	 * @Email:1132654532@qq.com
	 * @version 创建时间：2014年4月22日 上午11:16:38
	 */
	public interface EnglishWordTable extends BaseColumns {
		String WORD="word";//单词
		String PHONETIC="phonetic";//语音
		String BASE="base";//词义
		String HELPTXT="helptxt";//助记

		String TABLE_NAME="word";//表名
		String DROP_TABLE_SQL = DROP_TABLE + TABLE_NAME;
		String CREATE_WORD_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + "("
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+WORD+" text not null,"
				+PHONETIC+" text,"
				+BASE+" text,"
				+HELPTXT+" text,"
				+"unique (" +WORD+ ")"
				+");";
	}

	/**
	 *
	 * 作用：单词例句的字段，建表
	 * @author 顾美琴
	 * @Email:1132654532@qq.com
	 * @version 创建时间：2014年4月22日 上午11:17:36
	 */
	public interface EnglishExampleTable extends BaseColumns {
		String WORD="word";//单词
		String ENGLISH="english";//例句英语
		String CHINESE="chinese";//例句中文

		String TABLE_NAME="wordexample";
		String DROP_TABLE_SQL=DROP_TABLE+TABLE_NAME;
		String CREATE_EXAMPLE_TABLE_SQL= "CREATE TABLE " + TABLE_NAME + "("
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+WORD+" text not null,"
				+ENGLISH+" text,"
				+CHINESE+" text,"
				+"unique (" +WORD+ ")"
				+");";
	}

	/**
	 *
	 * 作用：单词学习状态，譬如是否已学过，字段，建表
	 * @author 顾美琴
	 * @Email:1132654532@qq.com
	 * @version 创建时间：2014年4月22日 上午11:18:01
	 */
	public interface EnglishCompareWordTable extends BaseColumns {
		String CID="cid";//单词词汇表ID
		String STUDYSTATUS="studystatus";//单词状态,是否已学习过
		String WORD="word";//单词

		String TABLE_NAME="compareword";
		String DROP_TABLE_SQL=DROP_TABLE+TABLE_NAME;
		String CREATE_COMPARE_WORD_TABLE_SQL="CREATE TABLE " + TABLE_NAME + "("
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+CID+" INTEGER,"
				+STUDYSTATUS+" BLOB,"
				+WORD+" text"
				+");";
	}

	/**
	 *
	 * 作用：单词学习记录，与EnglishCompareWordTable区别是：它记录学习时间，字段，建表
	 * @author 顾美琴
	 * @Email:1132654532@qq.com
	 * @version 创建时间：2014年4月22日 上午11:19:04
	 */
	public interface EnglishLearningRecordTable extends BaseColumns {
		String STIME="stime";//时间戳
		String WORD="word";//单词

		String TABLE_NAME="learning_recode";
		String CREATE_LEARNING_RECORD_TABLE_SQL="CREATE TABLE " + TABLE_NAME + "("
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+STIME+" INTEGER,"
				+WORD+" text not null,"
				+"unique (" +WORD+ ")"
				+");";
	}


	/**
	 *
	 * 作用：单词的信息，还包括了分数
	 * @author 顾美琴
	 * @Email:1132654532@qq.com
	 * @version 创建时间：2014年4月22日 上午11:20:01
	 */
	public interface EnglishUserWordTable extends BaseColumns {
		String STIME="stime";//时间戳
		String WORD="word";//单词
		String TIMES="times";//学习次数
		String DEGRESS="degree";//分数
		String ERROR_TIMES="error_times";//错误次数

		String TABLE_NAME="userword";
		String CREATE_USER_WORD_TABLE_SQL="CREATE TABLE " + TABLE_NAME + "("
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TIMES+" INTEGER,"
				+STIME+" INTEGER,"
				+ERROR_TIMES+" INTEGER,"
				+DEGRESS+" INTEGER,"
				+WORD+" text not null,"
				+"unique (" +WORD+ ")"
				+");";
	}

	/**
	 *
	 * 作用：单词词汇表
	 * @author 顾美琴
	 * @Email:1132654532@qq.com
	 * @version 创建时间：2014年4月22日 上午11:22:02
	 */
	public interface EnglishWordBookTable extends BaseColumns {
		String TITLE="title";//标题
		String CID="cid";//所属词汇表ID
		String POSITION="position";//词汇表是否选中

		String TABLE_NAME="wordbook";
		String CREATE_WORD_BOOK_TABLE_SQL="CREATE TABLE " + TABLE_NAME + "("
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+TITLE+" text not null,"
				+CID+" integer not null,"
				+POSITION+" integer,"
				+"unique (" +TITLE+ ")"
				+");";
	}

	/**
	 *
	 * 作用：学习计划表
	 * @author 顾美琴
	 * @Email:1132654532@qq.com
	 * @version 创建时间：2014年4月22日 上午11:22:37
	 */
	public interface EnglishPlanTable extends BaseColumns {
		String TIME="time";//时间戳
		String CID="cid";//词汇表ID


		String TABLE_NAME="plan";
		String CREATE_WORD_PLAN_TABLE_SQL="CREATE TABLE " + TABLE_NAME + "("
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+CID+" integer not null,"
				+TIME+" integer ,"
				+"unique (" +CID+ ")"
				+");";
	}

}

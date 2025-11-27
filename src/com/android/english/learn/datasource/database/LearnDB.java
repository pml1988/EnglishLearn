package com.android.english.learn.datasource.database;

import java.util.ArrayList;
import java.util.List;

import com.android.english.learn.model.ComparedWordData;
import com.android.english.learn.model.ExampleData;
import com.android.english.learn.model.LearingWordData;
import com.android.english.learn.model.UserWordData;
import com.android.english.learn.model.WordBookData;
import com.android.english.learn.model.WordData;
import com.android.english.learn.utils.TimeUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.WorkSource;
/**
 *
 * 作用：数据库操作类，包括增删改查，以及数据库的更新
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class LearnDB extends SQLiteOpenHelper implements DataSchema {

	public LearnDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(EnglishWordTable.CREATE_WORD_TABLE_SQL);
		db.execSQL(EnglishExampleTable.CREATE_EXAMPLE_TABLE_SQL);
		db.execSQL(EnglishCompareWordTable.CREATE_COMPARE_WORD_TABLE_SQL);
		db.execSQL(EnglishUserWordTable.CREATE_USER_WORD_TABLE_SQL);
		db.execSQL(EnglishLearningRecordTable.CREATE_LEARNING_RECORD_TABLE_SQL);
		db.execSQL(EnglishWordBookTable.CREATE_WORD_BOOK_TABLE_SQL);
		db.execSQL(EnglishPlanTable.CREATE_WORD_PLAN_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		deleteAllTable(db);
		onCreate(db);
	}
	/**
	 * 删除表
	 * @param db
	 */
	private void deleteAllTable(SQLiteDatabase db) {

		db.execSQL("DROP TABLE IF EXISTS " + EnglishWordTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + EnglishExampleTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + EnglishCompareWordTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + EnglishLearningRecordTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + EnglishUserWordTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + EnglishWordBookTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + EnglishPlanTable.TABLE_NAME);
	}

	public void beginTransaction() {
		getWritableDatabase().beginTransaction();
	}

	public void endTransaction() {
		getWritableDatabase().endTransaction();
	}

	public void setTransactionSuccessful() {
		getWritableDatabase().setTransactionSuccessful();
	}

	protected int update(String table, ContentValues values, String where, String[] whereArgs) {
		int numRows = 0;
		try {
			beginTransaction();
			numRows = getWritableDatabase().update(table, values, where, whereArgs);
			setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			endTransaction();
		}
		return numRows;
	}

	protected long insert(String table, ContentValues values) {
		long rowId = 0;
		try {
			beginTransaction();
			rowId = getWritableDatabase().insert(table, null, values);
			setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			endTransaction();
		}

		return rowId;
	}

	protected Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
		return getReadableDatabase().query(table, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);
	}

	protected int delete(String table, String where, String[] whereArgs) {
		int numRows = 0;
		try {
			beginTransaction();
			numRows = getWritableDatabase().delete(table, where, whereArgs);
			setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			endTransaction();
		}

		return numRows;
	}

	// public int updateWorkbookPosition(List<WordBookData> data) {
	// ContentValues values = new ContentValues();
	// values.put("", value);
	// }
	//
	/**
	 * 更新单词库的状态
	 * @param cid
	 * @param position
	 * @return
	 */
	public int updateWordbookPositioin(int cid, int position) {
		ContentValues values = new ContentValues();
		values.put("position", position);
		return update(EnglishWordBookTable.TABLE_NAME, values, EnglishWordBookTable.CID + "=?",
				new String[] { String.valueOf(cid) });
	}

	/**
	 * 更新单词
	 * @param data
	 * @return
	 */
	public int updateUserWordData(UserWordData data) {
		ContentValues values = new ContentValues();
		values.put("times", data.getTimes());
		values.put("stime", data.getStime());
		values.put("error_times", data.getError_times());
		return update(EnglishUserWordTable.TABLE_NAME, values, EnglishUserWordTable.WORD + "=?",
				new String[] { data.getWord() });
	}
	/**
	 * 更新计划时间
	 * @param cid
	 * @param ctime
	 * @return
	 */
	public int updatePlandData(int cid, long ctime) {
		ContentValues values = new ContentValues();
//		values.put("cid", cid);
		values.put("time", ctime);
		return update(EnglishPlanTable.TABLE_NAME, values, EnglishPlanTable.CID + "=?",
				new String[] { String.valueOf(cid) });
	}

	public void updateWordBook(long cid) {

	}

	/**
	 * 插入单词
	 * @param data
	 * @return
	 */
	public boolean InsertWord(List<WordData> data) {
		// String word, String phonetic, String base, String helptxt
		// getWritableDatabase().execSQL("DROP TABLE IF EXISTS " +
		// EnglishWordTable.TABLE_NAME);
		boolean succed = false;
		try {
			beginTransaction();
			String sql = "insert into " + EnglishWordTable.TABLE_NAME + "(" + EnglishWordTable.WORD
					+ "," + EnglishWordTable.PHONETIC + "," + EnglishWordTable.BASE + ","
					+ EnglishWordTable.HELPTXT + ") values (?,?,?,?)";
			SQLiteStatement insert = getWritableDatabase().compileStatement(sql);
			int size = data.size();
			int index;
			for (int i = 0;i < size;i++) {
				index = 1;
				WordData item = data.get(i);
				insert.bindString(index++, item.getWord());
				insert.bindString(index++, item.getPhonetic());
				insert.bindString(index++, item.getBase());
				insert.bindString(index++, item.getHelptxt());
				insert.execute();
			}
			insert.close();
			setTransactionSuccessful();
			succed = true;
		} catch (SQLException e) {
			e.printStackTrace();
			succed = false;
		} finally {
			endTransaction();
		}
		return succed;
	}

	/**
	 * 插入单词学习记录
	 * @param data
	 * @return
	 */
	public boolean InsertLearingRecode(LearingWordData data) {
		// String word, String phonetic, String base, String helptxt
		// getWritableDatabase().execSQL("DROP TABLE IF EXISTS " +
		// EnglishWordTable.TABLE_NAME);
		boolean succed = false;
		try {
			beginTransaction();
			String sql = "insert into " + EnglishLearningRecordTable.TABLE_NAME + "("
					+ EnglishLearningRecordTable.STIME + "," + EnglishLearningRecordTable.WORD
					+ ") values (?,?)";
			SQLiteStatement insert = getWritableDatabase().compileStatement(sql);
			int index = 1;
			insert.bindLong(index++, data.getStime());
			insert.bindString(index++, data.getWord());
			insert.execute();
			insert.close();
			setTransactionSuccessful();
			succed = true;
		} catch (SQLException e) {
			e.printStackTrace();
			succed = false;
		} finally {
			endTransaction();
		}
		return succed;
	}

	/**
	 * 插入计划
	 * @param ctime
	 * @param cid
	 * @return
	 */
	public boolean InsertPlanData(long ctime, int cid) {
		boolean succed = false;
		try {
			beginTransaction();
			String sql = "insert into " + EnglishPlanTable.TABLE_NAME + "(" + EnglishPlanTable.CID
					+ "," + EnglishPlanTable.TIME + ") values (?,?)";
			SQLiteStatement insert = getWritableDatabase().compileStatement(sql);
			int index = 1;
			insert.bindLong(index++, cid);
			insert.bindLong(index++, ctime);
			insert.execute();
			insert.close();
			setTransactionSuccessful();
			succed = true;
		} catch (SQLException e) {
			e.printStackTrace();
			succed = false;
		} finally {
			endTransaction();
		}
		return succed;
	}

	/**
	 * 插入用户单词
	 * @param data
	 * @return
	 */
	public boolean InserUserWordData(UserWordData data) {
		boolean succed = false;
		try {
			beginTransaction();
			String sql = "insert into " + EnglishUserWordTable.TABLE_NAME + "("
					+ EnglishUserWordTable.TIMES + "," + EnglishUserWordTable.STIME + ","
					+ EnglishUserWordTable.ERROR_TIMES + "," + EnglishUserWordTable.DEGRESS + ","
					+ EnglishUserWordTable.WORD + ") values (?,?,?,?,?)";
			SQLiteStatement insert = getWritableDatabase().compileStatement(sql);
			int index = 1;
			insert.bindLong(index++, data.getTimes());
			insert.bindLong(index++, data.getStime());
			insert.bindLong(index++, data.getError_times());
			insert.bindLong(index++, data.getDegree());
			insert.bindString(index++, data.getWord());
			insert.execute();

			insert.close();
			setTransactionSuccessful();
			succed = true;
		} catch (SQLException e) {
			e.printStackTrace();
			succed = false;
		} finally {
			endTransaction();
		}
		return succed;
	}

	/**
	 * 插入单词状态
	 * @param data
	 * @param type
	 * @return
	 */
	public boolean InsertComparedWord(List<String> data, long type) {
		boolean succed = false;
		try {
			beginTransaction();
			String sql = "insert into " + EnglishCompareWordTable.TABLE_NAME + "("
					+ EnglishCompareWordTable.CID + "," + EnglishCompareWordTable.STUDYSTATUS + ","
					+ EnglishWordTable.WORD + ") values (?,?,?)";
			SQLiteStatement insert = getWritableDatabase().compileStatement(sql);
			int size = data.size();
			int index;
			for (int i = 0;i < size;i++) {
				index = 1;
				insert.bindLong(index++, type);
				insert.bindLong(index++, -1);
				insert.bindString(index++, data.get(i));
				insert.execute();
			}
			insert.close();
			setTransactionSuccessful();
			succed = true;
		} catch (SQLException e) {
			e.printStackTrace();
			succed = false;
		} finally {
			endTransaction();
		}
		return succed;
	}

	/**
	 * 插入单词状态
	 * @param data
	 * @return
	 */
	public boolean InsertComparedWord(List<ComparedWordData> data) {
		boolean succed = false;
		try {
			beginTransaction();
			String sql = "insert into " + EnglishCompareWordTable.TABLE_NAME + "("
					+ EnglishCompareWordTable.CID + "," + EnglishCompareWordTable.STUDYSTATUS + ","
					+ EnglishWordTable.WORD + ") values (?,?,?)";
			SQLiteStatement insert = getWritableDatabase().compileStatement(sql);
			int size = data.size();
			int index;
			for (int i = 0;i < size;i++) {
				index = 1;
				ComparedWordData mdata = data.get(i);
				insert.bindLong(index++, mdata.getCid());
				insert.bindLong(index++, -1);
				insert.bindString(index++, mdata.getWord());
				insert.execute();
			}
			insert.close();
			setTransactionSuccessful();
			succed = true;
		} catch (SQLException e) {
			e.printStackTrace();
			succed = false;
		} finally {
			endTransaction();
		}
		return succed;
	}

	/**
	 * 插入单词例句
	 * @param data
	 * @return
	 */
	public boolean InsertWordExample(List<ExampleData> data) {
		boolean succed = false;
		try {
			beginTransaction();
			String sql = "insert into " + EnglishExampleTable.TABLE_NAME + "("
					+ EnglishExampleTable.WORD + "," + EnglishExampleTable.ENGLISH + ","
					+ EnglishExampleTable.CHINESE + ") values (?,?,?)";
			SQLiteStatement insert = getWritableDatabase().compileStatement(sql);
			int size = data.size();
			int index;
			for (int i = 0;i < size;i++) {
				index = 1;
				ExampleData item = data.get(i);
				insert.bindString(index++, item.getWord());
				insert.bindString(index++, item.getEnglish());
				insert.bindString(index++, item.getChinese());
				insert.execute();
			}
			insert.close();
			setTransactionSuccessful();
			succed = true;
		} catch (SQLException e) {
			e.printStackTrace();
			succed = false;
		} finally {
			endTransaction();
		}
		return succed;
	}

	/**
	 * 插入词汇表
	 * @param data
	 * @return
	 */
	public boolean InsertWordBook(WordBookData data) {
		boolean succed = false;
		try {
			beginTransaction();
			String sql = "insert into " + EnglishWordBookTable.TABLE_NAME + "("
					+ EnglishWordBookTable.TITLE + "," + EnglishWordBookTable.CID + ","
					+ EnglishWordBookTable.POSITION + ") values (?,?,?)";
			SQLiteStatement insert = getWritableDatabase().compileStatement(sql);
			int index = 1;
			insert.bindString(index++, data.getTitle());
			insert.bindLong(index++, data.getCid());
			insert.bindLong(index++, data.getPosition());
			insert.execute();

			insert.close();
			setTransactionSuccessful();
			succed = true;

		} catch (SQLException e) {
			e.printStackTrace();
			succed = false;
		} finally {
			endTransaction();
		}
		return succed;
	}

	/**
	 * 插入词汇表
	 * @param data
	 * @return
	 */
	public boolean InsertWordBook(List<WordBookData> data) {
		boolean succed = false;
		try {
			beginTransaction();
			String sql = "insert into " + EnglishWordBookTable.TABLE_NAME + "("
					+ EnglishWordBookTable.TITLE + "," + EnglishWordBookTable.CID + ","
					+ EnglishWordBookTable.POSITION + ") values (?,?,?)";
			SQLiteStatement insert = getWritableDatabase().compileStatement(sql);
			int num = data.size();
			for (int i = 0;i < num;i++) {
				WordBookData subData = data.get(i);
				int index = 1;
				insert.bindString(index++, subData.getTitle());
				insert.bindLong(index++, subData.getCid());
				insert.bindLong(index++, subData.getPosition());
				insert.execute();
			}
			insert.close();
			setTransactionSuccessful();
			succed = true;

		} catch (SQLException e) {
			e.printStackTrace();
			succed = false;
		} finally {
			endTransaction();
		}
		return succed;
	}

	/**
	 * 获取单词
	 * @param word
	 * @return
	 */
	public WordData getWord(String word) {
		WordData data = new WordData();
		// Cursor cursor = getReadableDatabase().query(
		// EnglishWordTable.TABLE_NAME,
		// new String[] { EnglishWordTable.WORD, EnglishWordTable.HELPTXT,
		// EnglishWordTable.BASE, EnglishWordTable.HELPTXT },
		// EnglishWordTable.WORD + " =?", new String[] { word }, null, null,
		// null);
		Cursor cursor = getReadableDatabase()
				.rawQuery(
						"select word.word,word.phonetic,word.base,word.helptxt,wordexample.english,wordexample.chinese from word inner join wordexample on word.word=wordexample.word where word.word=?",
						new String[] { word });
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			data.setWord(word);
			data.setBase(cursor.getString(2));
			data.setHelptxt(cursor.getString(1));
			data.setPhonetic(cursor.getString(3));
			data.setEnglish_sentance(cursor.getString(4));
			data.setChinese_sentance(cursor.getString(5));
		}
		cursor.close();
		return data;
	}

	/**
	 * 获取单词词汇库
	 * @return
	 */
	public List<WordBookData> getWordBookLibraryData() {
		List<WordBookData> dataList = new ArrayList<WordBookData>();
		Cursor cursor = getReadableDatabase().query(
				EnglishWordBookTable.TABLE_NAME,
				new String[] { EnglishWordBookTable.CID, EnglishWordBookTable.TITLE,
						EnglishWordBookTable.POSITION }, null, null, null, null, null);

		int num = cursor.getCount();
		cursor.moveToFirst();
		for (int i = 0;i < num;i++) {
			WordBookData data = new WordBookData();
			data.setCid(cursor.getLong(0));
			data.setTitle(cursor.getString(1));
			data.setPosition(cursor.getInt(2));
			dataList.add(data);
			cursor.moveToNext();
		}
		cursor.close();
		return dataList;
	}

	/**
	 * 获取词汇库位置状态
	 * @return
	 */
	public WordBookData getWordBookPosition() {
		WordBookData data = null;
		Cursor cursor = getReadableDatabase().query(EnglishWordBookTable.TABLE_NAME,
				new String[] { EnglishWordBookTable.CID, EnglishWordBookTable.TITLE },
				EnglishWordBookTable.POSITION + " =?", new String[] { "1" }, null, null, null);

		if (cursor.getCount() > 0) {
			data = new WordBookData();
			cursor.moveToFirst();
			data.setCid(cursor.getInt(0));
			data.setTitle(cursor.getString(1));
			data.setPosition(1);
			cursor.close();
		}
		// } else {
		// updateWordbookPositioin(1, 1);
		// data = getWordBookPosition();
		// }
		return data;
	}

	/**
	 * 获取某个词汇库的单词
	 * @param cid
	 * @return
	 */
	public List<String> getCompareWord(long cid) {
		List<String> data = new ArrayList<String>();
		Cursor cursor = getReadableDatabase().query(EnglishCompareWordTable.TABLE_NAME,
				new String[] { EnglishCompareWordTable.WORD }, EnglishCompareWordTable.CID + " =?",
				new String[] { String.valueOf(cid) }, null, null, null);
		int num = cursor.getCount();
		cursor.moveToFirst();
		for (int i = 0;i < num;i++) {
			data.add(cursor.getString(0));
			cursor.moveToNext();
		}
		cursor.close();
		return data;
	}

	/**
	 * 获取某个词汇表的所有单词
	 * @param cid
	 * @return
	 */
	public List<WordData> getAllWordDataList(long cid) {
		List<WordData> data = new ArrayList<WordData>();
		// Cursor cursor =
		// getReadableDatabase().query(EnglishCompareWordTable.TABLE_NAME,
		// new String[] { EnglishCompareWordTable.WORD },
		// EnglishCompareWordTable.CID + " =?",
		// new String[] { String.valueOf(cid) }, null, null, null);
		Cursor cursor = getReadableDatabase()
				.rawQuery(
						"select word.word,word.phonetic,word.base,word.helptxt from word inner join compareword on compareword.word=word.word where compareword.cid=?",
						new String[] { String.valueOf(cid) });
		int num = cursor.getCount();
		cursor.moveToFirst();
		for (int i = 0;i < num;i++) {
			WordData wd = new WordData();
			wd.setWord(cursor.getString(0));
			wd.setPhonetic(cursor.getString(1));
			wd.setBase(cursor.getString(2));
			wd.setHelptxt(cursor.getString(3));
			cursor.moveToNext();
			data.add(wd);
		}
		cursor.close();
		return data;
	}

	/**
	 * 获取学习记录中某个单词词汇表的所有单词
	 * @param cid
	 * @return
	 */
	public List<WordData> getLearningRecordWordData(long cid) {
		Cursor cursor = getReadableDatabase()
				.rawQuery(
						"select learning_recode.word from learning_recode inner join compareword on compareword.word=learning_recode.word where compareword.cid=?",
						new String[] { String.valueOf(cid) });
		// if(cursor.moveToFirst()) {
		//
		// }
		int num = cursor.getCount();
		List<WordData> data = null;
		if (num > 0) {
			cursor.moveToFirst();
			data = new ArrayList<WordData>();
			for (int i = 0;i < num;i++) {
				WordData wd = getWord(cursor.getString(0));
				cursor.moveToNext();
				data.add(wd);
			}
			cursor.close();
		}
		return data;
	}

	/**
	 * 判断某个单词是否已经学习过
	 * @param word
	 * @return
	 */
	public boolean getWordIsNew(String word) {
		Cursor cursor = getReadableDatabase().query(EnglishLearningRecordTable.TABLE_NAME,
				new String[] { EnglishLearningRecordTable._ID },
				EnglishLearningRecordTable.WORD + " =?", new String[] { word }, null, null, null);
		return cursor.moveToFirst();
	}

	/**
	 * 获取某个单词的详细的信息
	 * @param word
	 * @return
	 */
	public UserWordData getUserWordData(String word) {
		Cursor cursor = getReadableDatabase().query(
				EnglishUserWordTable.TABLE_NAME,
				new String[] { EnglishUserWordTable.TIMES, EnglishUserWordTable.STIME,
						EnglishUserWordTable.ERROR_TIMES, EnglishUserWordTable.DEGRESS,
						EnglishUserWordTable.WORD }, EnglishLearningRecordTable.WORD + " =?",
				new String[] { word }, null, null, null);
		if (cursor.moveToFirst()) {
			UserWordData data = new UserWordData();
			data.setTimes(cursor.getInt(0));
			data.setStime(cursor.getInt(1));
			data.setError_times(cursor.getInt(2));
			data.setDegree(cursor.getInt(3));
			data.setWord(cursor.getString(4));
			cursor.close();
			return data;
		} else {
			return null;
		}
	}

	/**
	 * 获取用户使用过的单词
	 * @return
	 */
	public List<UserWordData> getUseWordListData() {
		Cursor cursor = getReadableDatabase().query(
				EnglishUserWordTable.TABLE_NAME,
				new String[] { EnglishUserWordTable.TIMES, EnglishUserWordTable.STIME,
						EnglishUserWordTable.ERROR_TIMES, EnglishUserWordTable.DEGRESS,
						EnglishUserWordTable.WORD }, null, null, null, null, null);
		List<UserWordData> listData = null;
		int size = cursor.getCount();
		if (size > 0) {
			listData = new ArrayList<UserWordData>();
			cursor.moveToFirst();
			for (int i = 0;i < size;i++) {
				UserWordData data = new UserWordData();
				data.setTimes(cursor.getInt(0));
				data.setStime(cursor.getInt(1));
				data.setError_times(cursor.getInt(2));
				data.setDegree(cursor.getInt(3));
				data.setWord(cursor.getString(4));
				listData.add(data);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return listData;
	}

	/**
	 * 获取某个词汇表的时间戳
	 * @param cid
	 * @return
	 */
	public long getPlanData(int cid) {
		long time = -1;
		Cursor cursor = getReadableDatabase().query(EnglishPlanTable.TABLE_NAME,
				new String[] { EnglishPlanTable.TIME }, EnglishPlanTable.CID + " =?",
				new String[] { String.valueOf(cid) }, null, null, null);
		if (cursor.moveToFirst()) {
			time = cursor.getLong(0);
			cursor.close();
		}
		return time;
	}

	/**
	 * 获取某个词汇表的学习记录
	 * @param cid
	 * @return
	 */
	public int getLearningRecordWordNum(long cid) {
		Cursor cursor = getReadableDatabase()
				.rawQuery(
						"select learning_recode.word from learning_recode inner join compareword on compareword.word=learning_recode.word where compareword.cid=?",
						new String[] { String.valueOf(cid) });
		int num = cursor.getCount();
		return num;
	}

	/**
	 * 获取某个词汇表的今天学习单词个数
	 * @param cid
	 * @return
	 */
	public int getTodayWordDataNum(long cid) {
		long beginDate = TimeUtils.getTimesmorning();
		long endDate = TimeUtils.getTimesnight();
		Cursor cursor = getReadableDatabase()
				.rawQuery(
						"select learning_recode.word from learning_recode inner join compareword on compareword.word=learning_recode.word where stime > ? and stime < ? and compareword.cid=?",
						new String[] { String.valueOf(beginDate), String.valueOf(endDate),
								String.valueOf(cid) });
		int num = cursor.getCount();
		return num;
	}

	// 获取特定词汇的个数
	public int getSpecialCidCount(int cid) {
		Cursor cursor = getReadableDatabase().query(EnglishCompareWordTable.TABLE_NAME,
				new String[] { EnglishCompareWordTable.WORD }, EnglishPlanTable.CID + " =?",
				new String[] { String.valueOf(cid) }, null, null, null);
		int num = cursor.getCount();
		return num;
	}

	//
	// public int getLearningRecordCidCount(int cid) {
	// Cursor cursor =
	// getReadableDatabase().query(EnglishLearningRecordTable.TABLE_NAME,
	// new String[] { EnglishLearningRecordTable.WORD },
	// EnglishLearningRecordTable. + " =?",
	// new String[] { String.valueOf(cid) }, null, null, null);
	// int num = cursor.getCount();
	// return num;
	// }
	/**
	 * 删除某个单词
	 * @param word
	 * @return
	 */
	public int deleteUserWordData(String word) {
		return getReadableDatabase().delete(EnglishUserWordTable.TABLE_NAME,
				EnglishUserWordTable.WORD + "=?", new String[] { word });
	}

}

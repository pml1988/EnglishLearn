package com.android.english.learn.datasource;

import java.util.ArrayList;
import java.util.List;

import com.android.english.learn.datasource.database.LearnDB;
import com.android.english.learn.model.ComparedWordData;
import com.android.english.learn.model.ExampleData;
import com.android.english.learn.model.UserWordData;
import com.android.english.learn.model.WordBookData;
import com.android.english.learn.model.WordData;
import com.android.english.learn.model.WordScoreData;
import com.android.english.learn.utils.Loger;

import android.content.Context;
/**
 *
 * 作用：数据库的外部调用类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class DataSource {
	private Context mAppContext;
	private LearnDB mLearnDB;

	public DataSource(Context context) {
		// TODO Auto-generated constructor stub
		mAppContext = context;

	}

	/**
	 * 获取对LearnDB 单例
	 * @return
	 */
	public LearnDB getDB() {
		if (mLearnDB == null) {
			mLearnDB = new LearnDB(mAppContext);
		}
		return mLearnDB;
	}

	/**
	 * 获取某个单词的详细信息
	 * @param word
	 * @return
	 */
	public WordData getWordData(String word) {
		return getDB().getWord(word);
	}

	/**
	 * 获取某个词汇表的所有单词
	 * @param type
	 * @return
	 */
	public List<String> getComparedWord(long type) {
		return getDB().getCompareWord(type);
	}

	/**
	 * 获取词汇表
	 * @return
	 */
	public List<WordBookData> getWordBookLibraryData() {
		return getDB().getWordBookLibraryData();
	}

	/**
	 * 获取当前的词汇表的信息
	 * @return
	 */
	public WordBookData getWordBookPositionData() {
		return getDB().getWordBookPosition();
	}

	/**
	 * 获取某个词汇表的单词
	 * @param cid
	 * @return
	 */
	public List<WordData> getAllWordDataList(long cid) {
		return getDB().getAllWordDataList(cid);
	}

	/**
	 * 获取某个词汇表的所有学习记录单词
	 * @param cid
	 * @return
	 */
	public List<WordData> getLearningRecordWordDataList(long cid) {
		return getDB().getLearningRecordWordData(cid);
	}

	/**
	 * 获取某个词汇表的学习记录单词个数
	 * @param cid
	 * @return
	 */
	public int getLearingRecordWordNum(long cid) {
		return getDB().getLearningRecordWordNum(cid);
	}

	/**
	 * 获取某个单词是否是新的
	 * @param word
	 * @return
	 */
	public boolean getWordIsNew(String word) {
		return getDB().getWordIsNew(word);
	}

	/**
	 * 获取某个单词的使用信息，
	 * @param word
	 * @return
	 */
	public UserWordData getUserWordData(String word) {
		return getDB().getUserWordData(word);
	}

	/**
	 * 获取用户使用单词
	 * @return
	 */
	public List<UserWordData> getUserWordListData() {
		return getDB().getUseWordListData();
	}

	/**
	 * 获取单词列表的分数
	 * @param userdataList
	 * @return
	 */
	public List<WordScoreData> getWordScoreData(List<UserWordData> userdataList) {
		int size=userdataList.size();
		List<WordScoreData> scoreListData=new ArrayList<WordScoreData>();
		WordScoreData scoreitem=null;
		UserWordData useritem=null;
		for(int i=0;i<size;i++) {
			scoreitem=new WordScoreData();
			 useritem= userdataList.get(i);
			 double d=((double)useritem.getDegree()/(double)(useritem.getTimes()*10+20))*100;
			 Loger.d("Teh d :"+d);
			 int score=(int)d;
			 Loger.d("The score is:"+score);
			 scoreitem.setScore(score);
			 scoreitem.setWordData(getWordData(useritem.getWord()));
			 scoreListData.add(scoreitem);
		}
		return scoreListData;
	}

	/**
	 * 获取某个词汇表的计划时间戳
	 * @param cid
	 * @return
	 */
	public long getPlanData(int cid) {
		return getDB().getPlanData(cid);
	}

	/**
	 * 获取某个词汇表的今天单词数
	 * @param cid
	 * @return
	 */
	public int getTodayWordDataNum(int cid) {
		return getDB().getTodayWordDataNum(cid);
	}

	/**
	 * 设置词汇表的计划时间戳
	 * @param cid
	 * @param time
	 */
	public void setPlanData(int cid,long time) {
		if(getPlanData(cid)==-1) {
			getDB().InsertPlanData(time, cid);
		}else {
			getDB().updatePlandData(cid, time);
		}
	}

	/**
	 * 更新词汇表的状态（是否已经选中）
	 * @param cid
	 * @param position
	 * @return
	 */
	public int updateWordbookPositioin(int cid, int position) {
		return getDB().updateWordbookPositioin(cid, position);
	}

	/**
	 * 更新学习使用的单词
	 * @param data
	 * @return
	 */
	public int updateUserWordData(UserWordData data) {
		return getDB().updateUserWordData(data);
	}

	/**
	 * 插入单词
	 * @param data
	 */
	public void InsertWordData(List<WordData> data) {
		getDB().InsertWord(data);
	}

	/**
	 * 插入词汇表列表
	 * @param data
	 */
	public void InsertWordBookData(List<WordBookData> data) {
		getDB().InsertWordBook(data);
	}

	/**
	 * 插入某个词汇表
	 * @param data
	 */
	public void InsertWordBookData(WordBookData data) {
		getDB().InsertWordBook(data);
	}

	/**
	 * 插入某个词汇表的单词
	 * @param data
	 * @param cid
	 */
	public void InsertComparedWordData(List<String> data, long cid) {
		getDB().InsertComparedWord(data, cid);
	}

	/**
	 * 插入单词的使用信息
	 * @param rightnum
	 * @param errornum
	 * @param word
	 * @param stime
	 */
	public void InsertOrUpdateUserWordData(int rightnum,int errornum,String word,long stime) {
		boolean isNeedInsert=false;
		UserWordData data= getUserWordData(word);
		if(data==null) {
			isNeedInsert=true;
			data=new UserWordData();
		}
		data.setTimes(data.getTimes()+1);
		data.setStime(stime);
		data.setError_times(data.getError_times()+errornum);
		data.setDegree(data.getTimes()*10+20-data.getError_times()*5);
		data.setWord(word);
		if(isNeedInsert) {
			InsertUserWordData(data);
		}else {
			updateUserWordData(data);
		}
	}

	/**
	 * 插入某个使用单词
	 * @param data
	 */
	public void InsertUserWordData(UserWordData data) {
		getDB().InserUserWordData(data);
	}

	/**
	 * 插入使用单词列表
	 * @param data
	 */
	public void InsertComparedWordData(List<ComparedWordData> data) {
		getDB().InsertComparedWord(data);
	}

	/**
	 * 插入单词的例句列表
	 * @param data
	 */
	public void InsertWordExampleData(List<ExampleData> data) {
		getDB().InsertWordExample(data);
	}

	public int deleteUserWordData(String word) {
		return getDB().deleteUserWordData(word);
	}
}

package com.android.english.learn.model;

/**
 *
 * 作用：单词模型
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class ComparedWordData extends BaseData{

	/**
	 *
	 */
	private static final long serialVersionUID = 1795417724092291975L;
	private String word;
	private long cid;
	public void setWord(String word) {
		this.word = word;
	}
	public String getWord() {
		return word;
	}
	public void setCid(long cid) {
		this.cid = cid;
	}
	public long getCid() {
		return cid;
	}
}

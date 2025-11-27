package com.android.english.learn.model;

/**
 *
 * 作用：词汇表模型
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class WordBookData extends BaseData{

	/**
	 *
	 */
	private static final long serialVersionUID = 4828152737326260090L;

	private String title;
	private long cid;
	private int position;
	public void setCid(long cid) {
		this.cid = cid;
	}
	public long getCid() {
		return cid;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getPosition() {
		return position;
	}
}

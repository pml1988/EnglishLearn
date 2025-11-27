package com.android.english.learn.base;

import com.android.english.learn.R;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;

/**
 *
 * 作用：主机面的基础视图类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class BaseMainActivity extends FragmentActivity {

	private Toast toast;
	private ProgressDialog mProgressDialog;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle(R.string.tip);
		mProgressDialog.setIcon(R.drawable.icon);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setMessage(getResources().getString(R.string.loading_words));
		/**
		 * 设计思想：Toast可以覆盖掉前面的Toast
		 */
		if (toast == null) {
			toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		}
	}

	/**
	 * 显示对话框
	 */
	public void showLoadingDialog() {

		if (!mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}

	/**
	 * 是否显示对话框
	 * @return
	 */
	public boolean isDialogLoading() {
		return mProgressDialog.isShowing();
	}

	/**
	 * 隐藏对话框
	 */
	public void dismissLoadingDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	/**
	 * 显示Toast
	 * @param msg String
	 */
	public void showMsg(String msg) {
		toast.setText(msg);
		toast.show();
	}

	/**
	 * 显示Toast
	 * @param res 资源
	 */
	public void showMsg(int res) {
		toast.setText(res);
		toast.show();
	}
}

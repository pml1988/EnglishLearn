package com.android.english.learn.base;

import com.android.english.learn.R;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * 作用：普通的基础视图类，简化工作
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public abstract class BaseActivity extends FragmentActivity {
	/**
	 * 返回
	 */
	private ImageView mBackIv;
	/**
	 * 标题
	 */
	private TextView mTitleTv;
	/**
	 * 显示提示，还有可以覆盖前面的消息作用
	 */
	private Toast toast;
	/**
	 * 对话框
	 */
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setActivityContentView();
		initView();
		initData();
		setListener();
	}

	private void initView() {
		mBackIv = (ImageView) findViewById(R.id.top_back_iv);
		mTitleTv = (TextView) findViewById(R.id.top_title_tv);
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle(R.string.tip);
		mProgressDialog.setIcon(R.drawable.icon);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setMessage(getResources().getString(R.string.loading_words));
	}

	/**
	 * 显示对话框
	 */
	protected void showLoadingDialog() {

		if (!mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}

	/**
	 *
	 * @return 是否已经显示对话框
	 */
	protected boolean isDialogLoading() {
		return mProgressDialog.isShowing();
	}

	/**
	 * 隐藏对话框
	 */
	protected void dismissLoadingDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		if (mTitleTv != null) {
			mTitleTv.setText(getBaseTitle());
		}
		if (toast == null) {
			toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		}
	}

	/**
	 * 设置事件
	 */
	private void setListener() {
		if (mBackIv != null) {
			mBackIv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					BaseActivity.this.finish();
					overridePendingTransition(R.anim.new_dync_no_animation,
							R.anim.new_dync_out_to_right);
				}
			});
		}
	}

	/**
	 * 显示消息
	 * @param msg
	 */
	protected void showMsg(String msg) {
		toast.setText(msg);
		toast.show();
	}

	protected void showMsg(int res) {
		toast.setText(res);
		toast.show();
	}

	/**
	 * 返回时的动画效果
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			BaseActivity.this.finish();
			overridePendingTransition(R.anim.new_dync_no_animation, R.anim.new_dync_out_to_right);
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 设置布局
	 */
	public abstract void setActivityContentView();

	/**
	 * 设置标题
	 * @return
	 */
	public abstract String getBaseTitle();

}

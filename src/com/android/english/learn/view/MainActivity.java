package com.android.english.learn.view;

import java.util.Date;

import com.android.english.learn.LearnApp;
import com.android.english.learn.R;
import com.android.english.learn.adapter.MainPagerAdapter;
import com.android.english.learn.base.BaseMainActivity;
import com.android.english.learn.fragment.HomeFragment;
import com.android.english.learn.fragment.PersonFragment;
import com.android.english.learn.utils.Loger;
import com.android.english.learn.widget.Chooser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 *
 * 作用：首页 视图类
 * @author 顾美琴
 * @Email:1132654532@qq.com
 * @version 创建时间：2014年04月
 */
public class MainActivity extends BaseMainActivity {
	private Chooser mChooser;//左右选择
	private TextView mHomeTv;//
	private TextView mPersonTv;
	private ViewPager mViewPager;//滑动
	private ImageView page_main_menu_iv;//

	private View mPopUpView;//弹出的那3个点
	private TextView mMemoryTv;//记忆
	private TextView mAboutTv;//关于

	private PopupWindow mPopupWindow;//弹出框
	private Dialog mMemoryDialog;//记忆对话框
	private MainPagerAdapter mMainAdapter;//主页适配器

	private changeHome cHome;//.....................
	private boolean main;//是否是主页

	public static final int REQUEST_HOME_CODE = 1;
	public static final int REQUEST_FAIL_CODE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//
		// ActionBar actionBar = getActionBar();
		// actionBar.setDisplayHomeAsUpEnabled(true);

		initView();
		initData();
		setListener();
	}

	private void initView() {
		mChooser = (Chooser) findViewById(R.id.chooser);
		mHomeTv = (TextView) findViewById(R.id.main_home_tv);
		mPersonTv = (TextView) findViewById(R.id.main_person_tv);
		mViewPager = (ViewPager) findViewById(R.id.main_viewPager);
		page_main_menu_iv = (ImageView) findViewById(R.id.page_main_menu_iv);
		mPopUpView = getLayoutInflater().inflate(R.layout.main_popup_layout, null);
		mMemoryTv = (TextView) mPopUpView.findViewById(R.id.page_main_memory);
		mAboutTv = (TextView) mPopUpView.findViewById(R.id.page_main_about);

	}

	private void initData() {
		main = getIntent().getBooleanExtra("main", false);
		if (main) {
			main = false;
			long lastTime = LearnApp.getInstance().getSharePrefsHelper().getLastOpenAppTime();
			long currentTime = (new Date()).getTime();
			LearnApp.getInstance().getSharePrefsHelper().setLastOpenAppTime(currentTime);
			if (lastTime > 0) {
				int minutes = (int) ((currentTime - lastTime) / (1000 * 60));
				if (minutes >= 5) {
					AlertDialog.Builder builder = new Builder(MainActivity.this,
							R.style.CustomDialog);
					builder.setTitle(R.string.memory_tip);
					// builder.setCanceledOnTouchOutside(false);
					builder.setMessage(getResources().getString(R.string.time_no_review));
					builder.setPositiveButton(R.string.check_memory_rule,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									Intent it = new Intent(MainActivity.this,
											CheckMemoryActivity.class);
									startActivity(it);
									overridePendingTransition(R.anim.new_dync_in_from_right,
											R.anim.new_dync_no_animation);
								}
							});
					builder.setNegativeButton(android.R.string.cancel,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
								}
							});
					mMemoryDialog = builder.create();
					mMemoryDialog.show();

				}
			} else {

			}
		}
		mMainAdapter = new MainPagerAdapter(this);
		mMainAdapter.addTab(HomeFragment.class, null);
		mMainAdapter.addTab(PersonFragment.class, null);
		mViewPager.setAdapter(mMainAdapter);
		mPopupWindow = new PopupWindow(mPopUpView, getWindowManager().getDefaultDisplay()
				.getWidth() / 2, LayoutParams.WRAP_CONTENT, false);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setFocusable(true);
		// mPopupWindow.showAsDropDown(findViewById(R.id.main_top_rl));
		// mPopupWindow.showAtLocation(findViewById(R.id.main_top_rl),
		// Gravity.BOTTOM|Gravity.RIGHT, x, y);
	}

	public void setHome(changeHome ch) {
		this.cHome = ch;
	}

	public interface changeHome {
		public void setHomeActivityResult(int requestCode, int resultCode, Intent data);
	}

	private void setListener() {
		mViewPager.setOnPageChangeListener(mOnPageChangeListener);
		mHomeTv.setOnClickListener(mOnClickListener);
		mPersonTv.setOnClickListener(mOnClickListener);
		page_main_menu_iv.setOnClickListener(mOnClickListener);
		mMemoryTv.setOnClickListener(mOnClickListener);
		mAboutTv.setOnClickListener(mOnClickListener);
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // TODO Auto-generated method stub
	// MenuInflater inflater = getMenuInflater();
	//
	// inflater.inflate(R.menu.main_menu, menu);
	//
	// return super.onCreateOptionsMenu(menu);
	//
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // TODO Auto-generated method stub
	//
	// return super.onOptionsItemSelected(item);
	// }

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Loger.d("PML" + requestCode + ";resultcode is:" + resultCode);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == MainActivity.REQUEST_HOME_CODE) {
				Loger.d("The Main Activity result code is:" + data.getLongExtra("cid", 1));
				// HomeFragment hf=(HomeFragment) mMainAdapter.getItem(0);
				// hf.setHomeActivityResult(requestCode, resultCode, data);

				// this.cHome.setHomeActivityResult(requestCode, resultCode,
				// data);
			}
		}
	}

	OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			if (arg0 == 0) {
				mChooser.onClick(mHomeTv);
			} else if (arg0 == 1) {
				mChooser.onClick(mPersonTv);
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// TODO Auto-generated method stub
			mViewPager.setTag(R.id.pager_position, position);
			mViewPager.setTag(R.id.pager_position_offset_pixels, positionOffsetPixels);
		}
	};

	OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_home_tv:
				mViewPager.setCurrentItem(0);
				break;
			case R.id.main_person_tv:
				mViewPager.setCurrentItem(1);
				break;
			case R.id.page_main_menu_iv:
				if (!mPopupWindow.isShowing()) {
					Loger.d("the pop is:");
					mPopupWindow.showAsDropDown(page_main_menu_iv);
				} else {
					mPopupWindow.dismiss();
				}
				break;
			case R.id.page_main_memory:
				mPopupWindow.dismiss();
				Intent it=new Intent(MainActivity.this, CheckMemoryActivity.class);

				startActivity(it);

				overridePendingTransition(R.anim.new_dync_in_from_right,
						R.anim.new_dync_no_animation);
				break;
			case R.id.page_main_about:
				mPopupWindow.dismiss();
				Intent itabout=new Intent(MainActivity.this, AboutActivity.class);
				startActivity(itabout);
				overridePendingTransition(R.anim.new_dync_in_from_right,
						R.anim.new_dync_no_animation);
				break;
			}
		}
	};
}

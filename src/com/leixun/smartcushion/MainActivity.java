package com.leixun.smartcushion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ab.view.sliding.AbBottomTabView;
import com.ab.view.titlebar.AbTitleBar;
import com.leixun.smartcushion.Sdk.BleConnector;
import com.leixun.smartcushion.Sdk.Db.DbManger;
import com.leixun.smartcushion.Sdk.bean.Alarm;
import com.leixun.smartcushion.Sdk.bean.ErrDataBean;
import com.leixun.smartcushion.Sdk.perference.CushionPreferences;
import com.leixun.smartcushion.Sdk.util.L;
import com.leixun.smartcushion.alertData.AlertDataFragment;
import com.leixun.smartcushion.control.ControlFragment;
import com.leixun.smartcushion.setting.SettingFragment;

public class MainActivity extends BaseActivity implements OnPageChangeListener {
	
	private AbBottomTabView mAbBottomBar;
	private AbTitleBar mAbTitleBar;
	
	private List<Fragment> mFragments;
	private List<String> tabTexts = new ArrayList<String>();
	private List<Drawable> tabDrawables = null;
	private long mExitTime;
	private final int REFRESH_SETTING = 0;
	private final int REFRESH_ERRDATA= 1;

	Handler mhHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_SETTING:
				int battery = msg.arg1;
				int temperature = msg.arg2;
				((ControlFragment)mFragments.get(0)).RefreshSetting(battery);
				break;
			case REFRESH_ERRDATA:

				((AlertDataFragment)mFragments.get(1)).RefreshErrDataList();
				break;
				
			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_main);
		initView();
		initData();
		initTitlebar();
		initBottomBar();
		initListener();
	}
	
	/**
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		
		mAbBottomBar = (AbBottomTabView) findViewById(R.id.mBottomTabView);
	}
	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
		String preaddress = CushionPreferences.getInstance(
			this).getCurrectConnectorCushion(
				null);
		if(preaddress != null){
			BleConnector.getInstance().setBluetoothDeviceAddress(preaddress);//等待连接，回连
		}
	}

	
	/**
	 * 
	 */
	private void initTitlebar() {
		// TODO Auto-generated method stub
		mAbTitleBar = this.getTitleBar();
		mAbTitleBar.getTitleTextButton().setPadding(0, 35, 0, 35);
		mAbTitleBar.setTitleText(getResources().getString(
				R.string.CONTROL_DEVICE));
//		mAbTitleBar.setLogo2(R.drawable.logo_translate);
		mAbTitleBar.setTitleBarBackground(R.drawable.nav_bar);
//		View rightViewMore = mInflater.inflate(R.layout.share_btn, null);
//		shareButton = (Button) rightViewMore.findViewById(R.id.ShareBtn);
		mAbTitleBar.clearRightView();
//		mAbTitleBar.addRightView(rightViewMore);
		mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);
	}
	
	/**
	 * 
	 */
	private void initBottomBar() {
		// TODO Auto-generated method stub
		Fragment page1 = new ControlFragment();
		Fragment page2 = new AlertDataFragment();
		Fragment page3 = new SettingFragment();

		mFragments = new ArrayList<Fragment>();
		mFragments.add(page1);
		mFragments.add(page2);
		mFragments.add(page3);

		tabTexts.add(getResources().getString(R.string.CONTROL_DEVICE));
		tabTexts.add(getResources().getString(R.string.ALERT_DATA));
		tabTexts.add(getResources().getString(R.string.SETTING));

		// 设置样式
		mAbBottomBar.setTabTextColor(getResources().getColor(
				R.color.bottom_bar_text_color));
		mAbBottomBar.setTabSelectColor(getResources().getColor(
				R.color.bottom_bar_text_select_color));
		mAbBottomBar.setTabBackgroundResource(R.drawable.tab_bg);
		mAbBottomBar.setTabLayoutBackgroundResource(R.drawable.tab_bg);

//		mAbBottomBar.setTabTextSize((int) getResources().getDimension(
//				R.dimen.activity_tabtext_size));

		// mBottomTabView.setLayoutParams(new
		// LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

		// 注意图片的顺序
		tabDrawables = new ArrayList<Drawable>();
		tabDrawables
				.add(getResources().getDrawable(R.drawable.ic_tab_control));
		tabDrawables
				.add(getResources().getDrawable(R.drawable.ic_tab_control_sele));
		tabDrawables.add(getResources().getDrawable(R.drawable.ic_tab_data));
		tabDrawables.add(getResources().getDrawable(R.drawable.ic_tab_data_sele));
		tabDrawables.add(getResources().getDrawable(R.drawable.ic_tab_set));
		tabDrawables.add(getResources().getDrawable(R.drawable.ic_tab_set_sele));

		// 演示增加一组
		mAbBottomBar.addItemViews(tabTexts, mFragments, tabDrawables);
		mAbBottomBar.setTabPadding(2, 10, 2, 2);
	}
	
	private void initListener() {
		// TODO Auto-generated method stub
		mAbBottomBar.setOnPageChangeListener(this);
	}
	
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			
			break;
		case 1:
			((AlertDataFragment)mFragments.get(1)).RefreshErrDataList();
			break;

		default:
			break;
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		mAbTitleBar.setTitleText(tabTexts.get(arg0));
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, getString(R.string.main_activity_exit),
						Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		getApplication().onTerminate();
		super.onDestroy();		

	}
	
	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.BaseActivity#UpdateCurrectDataInfo(com.leixun.smartcushion.Sdk.bean.Alarm, int, int)
	 */
	@Override
	public void UpdateCurrectDataInfo(Alarm alarm, int battery, int temperature) {
		// TODO Auto-generated method stub
		super.UpdateCurrectDataInfo(alarm, battery, temperature);
		L.e("battery============"+battery+"-----------temperature====================="+temperature);
		Message message = new Message();
		message.what = REFRESH_SETTING;
		message.arg1 = battery;
		message.arg2 = temperature;
		mhHandler.sendMessage(message);
		
	}
	
	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.BaseActivity#UpdateHistoryDataInfo(com.leixun.smartcushion.Sdk.bean.ErrDataBean, int, int)
	 */
	@Override
	public void UpdateHistoryDataInfo(ErrDataBean errDataBean, int tootleCount,
			int currectCount) {
		// TODO Auto-generated method stub
		super.UpdateHistoryDataInfo(errDataBean, tootleCount, currectCount);
		DbManger.getInstance(this).saveDbErrData(errDataBean);
		if(tootleCount==(currectCount+1)){			
			Message message = new Message();
			message.what = REFRESH_ERRDATA;
			mhHandler.sendMessage(message);
		}
		Log.e("历史数据数量=======", DbManger.getInstance(this).queryErrDataCount()+"");
	}
	
	

}

package com.leixun.smartcushion;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.leixun.smartcushion.Guide.GuideActivity;
import com.leixun.smartcushion.Sdk.BleConnector;
import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.Db.DbManger;
import com.leixun.smartcushion.Sdk.bean.UserBean;
import com.leixun.smartcushion.Sdk.perference.CushionPreferences;
import com.leixun.smartcushion.Sdk.perference.XMLPreferences;
import com.leixun.smartcushion.Sdk.util.L;
import com.leixun.smartcushion.setting.DeviceListActivity;
import com.leixun.smartcushion.view.CustomDialog;
import com.leixun.smartcushion.view.CustomDialog.Builder;

public class LauncherActivity extends Activity {

	private RelativeLayout mLaunchLayout;
	private Animation mFadeIn;
	private Animation mFadeInScale;
	private BluetoothAdapter mBluetoothAdapter;
	private Handler mHandler;
//	private List<DeviceInfoBean> mDeviceInfoBeans = new LinkedList<DeviceInfoBean>();
	private List<String> imeis = new LinkedList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.launcher);
		mLaunchLayout = (RelativeLayout) findViewById(R.id.launch);
		init();
		initBle();
		setListener();
		String id = CushionPreferences.getInstance(this).getCurrectUser(null);
//		List<UserBean> beans = DbManger.getInstance(mContext).queryData("0");
		//		if(beans.size()>0){
//			setmCurrectUserBean(beans.get(0));
//		}

	}

	private void initBle() {
		mHandler = new Handler();
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();
			// Checks if Bluetooth is supported on the device.
		if (mBluetoothAdapter == null) {
			finish();
		}
		
		if(DbManger.getInstance(this).queryDbUserInfoData().size()>0){
			CushionBeanManager.getInstance().setBeans(DbManger.getInstance(this).queryDbUserInfoData());
		}
		
		String userID = CushionPreferences.getInstance(this).getCurrectUser(null);
		
		if(userID!=null){
			
			L.e("userID================================="+userID);
			List<UserBean>  bean = DbManger.getInstance(this).queryData(userID);
			L.e("beans=====================size-============="+bean.size());

			if(bean.size()>0){
			CushionBeanManager.getInstance().setmCurrectUserBean(bean.get(0));
			}
		}
		// TODO Auto-generated method stub
	}

	private void setListener() {

		mFadeIn.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				mLaunchLayout.startAnimation(mFadeInScale);
			}
		});

		mFadeInScale.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				BleConnector mTclBleConnector;
				mTclBleConnector = BleConnector.getInstance();
				goActivity(mTclBleConnector);
			}
		});

	}

	private void goActivity(BleConnector mTclBleConnector) {
		Intent intent = new Intent();

		if (!XMLPreferences.getInstance(this).getIsFirstStartApplication(false)) {
			intent.setClass(LauncherActivity.this, GuideActivity.class);
			XMLPreferences.getInstance(this).setIsFirstStartApplication(false);
		} else {
			String preaddress = CushionPreferences.getInstance(
					this).getCurrectConnectorCushion(
						null);
			if (preaddress==null){
				intent.setClass(LauncherActivity.this,
						DeviceListActivity.class);
				intent.putExtra("FIRST_TIME_START", true);
			}else {
				intent.setClass(LauncherActivity.this, MainActivity.class);
				intent.putExtra("FIRST_TIME_START", false);

			}

		}
//		CheckImei();
		startActivity(intent);
		this.finish();

	}

	private void init() {
		initAnim();
		mLaunchLayout.startAnimation(mFadeIn);
	}

	private void initAnim() {
		mFadeIn = AnimationUtils.loadAnimation(LauncherActivity.this,
				R.anim.welcome_fade_in);
		mFadeIn.setDuration(500);
		mFadeIn.setFillAfter(true);
		mFadeInScale = AnimationUtils.loadAnimation(LauncherActivity.this,
				R.anim.welcome_fade_in_scale);
		mFadeInScale.setDuration(2000);
		mFadeInScale.setFillAfter(true);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
	
//	/**
//	 * 
//	 */
//	private void CheckImei() {
//		// TODO Auto-generated method stub
//		imeis.add("860076031428492");
//		String Imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
//				.getDeviceId();
//		if(!imeis.contains(Imei)){
//			CustomDialog.Builder builder = new Builder(this);
//			builder.setMessage(R.string.YOU_ARE_NOT_LEGAL_USER);
//			CustomDialog customDialog = builder.customCreate();
//			customDialog.show();
//		}
//	}
	

}

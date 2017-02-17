/**   
 * @Title:ControlFragment.java
 * @Package com.leixun.smartcushion.control
 * @Description: 
 * @author 姚海军  
 * @date 2016年11月15日上午11:26:55
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年11月15日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.control;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.fragment.AbFragment;
import com.ab.view.sliding.AbSlidingPlayView;
import com.leixun.smartcushion.R;
import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.Db.DbManger;
import com.leixun.smartcushion.Sdk.bean.CushionBean;
import com.leixun.smartcushion.Sdk.bean.UserBean;
import com.leixun.smartcushion.view.CheckPreciseControlPop;
import com.leixun.smartcushion.view.DeviceStateControlPop;

/**
 * @author 姚海军
 *
 */
public class ControlFragment extends AbFragment implements OnClickListener{

	AbSlidingPlayView mSlidingPlayView = null;
	private CheckPreciseControlPop mCheckOercisePop;
	private DeviceStateControlPop mDeviceStatePop;
	LinearLayout mBtnDeviceStatus;
	LinearLayout mBtnCheckPreciseCountrol;
	LinearLayout mBtnTemperatureCountrolState;
	private TextView mTvDevicebattery;
	private TextView mTvDeviceState;
	private TextView mTvCheckPreciseCountrol;
	private TextView mTvTemperatureCountrolState;	
	private CheckBox mSwitchPostureCheck,mSwitchFanCheck;
	private UserBean mUserBean= new UserBean();
	private CushionBean mcushion= new CushionBean();
	private View mRootView;
	@Override
	public View onCreateContentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		mRootView = inflater.inflate(R.layout.control_fragment_layout,
				container, false);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// 显示内容
				showContentView();
			}

		}, 0);
		// item被点击事件
		initView(mRootView);
//		initIocView();
		iniSlidingImage(inflater);
		initData();
		initListener();
		// ((TabBottomActivity) mActivity).DeviceConnectingTimeOut();
		return mRootView;
	}
	
	/**
	 * 
	 */
	private void initView(View view) {
		// TODO Auto-generated method stub
		mSlidingPlayView = (AbSlidingPlayView)view.findViewById(R.id.mAbSlidingPlayView);
		mBtnDeviceStatus = (LinearLayout) mRootView.findViewById(R.id.btn_device_state);
		mBtnCheckPreciseCountrol = (LinearLayout) mRootView.findViewById(R.id.btn_check_precise_countrol);
		mBtnTemperatureCountrolState= (LinearLayout) mRootView.findViewById(R.id.btn_temperature_countrol_state);
		
		mTvDevicebattery = (TextView) mRootView.findViewById(R.id.tv_device_battery);
		mTvDeviceState = (TextView) mRootView.findViewById(R.id.tv_device_state);
		mTvCheckPreciseCountrol= (TextView) mRootView.findViewById(R.id.tv_check_precise_countrol);
		mTvTemperatureCountrolState= (TextView) mRootView.findViewById(R.id.tv_temperature_countrol_state);
		mSwitchFanCheck = (CheckBox) mRootView.findViewById(R.id.switch_fan_switch);

		mSwitchPostureCheck = (CheckBox) mRootView.findViewById(R.id.switch_device_posture_check);
	}
	
	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
	
		updateCushionData();
	}
	
	/**
	 * 
	 */
	private void initListener() {
		// TODO Auto-generated method stub
		mBtnDeviceStatus.setOnClickListener(this);
		mBtnCheckPreciseCountrol.setOnClickListener(this);
		mBtnTemperatureCountrolState.setOnClickListener(this);
		mSwitchPostureCheck.setOnClickListener(this);
		mSwitchFanCheck.setOnClickListener(this);
	}
	
	
	/**
	 * 
	 */
	private void iniSlidingImage(LayoutInflater inflater) {
		// TODO Auto-generated method stub
	    final View mPlayView = inflater.inflate(R.layout.play_view_item, null);
		ImageView mPlayImage = (ImageView) mPlayView.findViewById(R.id.mPlayImage);
//		TextView mPlayText = (TextView) mPlayView.findViewById(R.id.mPlayText);
//		mPlayText.setText("1111111111111");
		mPlayImage.setBackgroundResource(R.drawable.home_banner);
		
		final View mPlayView1 = inflater.inflate(R.layout.play_view_item, null);
		ImageView mPlayImage1 = (ImageView) mPlayView1.findViewById(R.id.mPlayImage);
//		TextView mPlayText1 = (TextView) mPlayView1.findViewById(R.id.mPlayText);
//		mPlayText1.setText("2222222222222");
		mPlayImage1.setBackgroundResource(R.drawable.home_banner);
		mSlidingPlayView.setNavHorizontalGravity(Gravity.CENTER);
		mSlidingPlayView.addView(mPlayView);
		mSlidingPlayView.addView(mPlayView1);
		mSlidingPlayView.startPlay();
	}
	
	
	/**
	 * 更新设备电量
	 */
	private void updateCushionBattery(CushionBean cushion) {
		// TODO Auto-generated method stub
		if(cushion!=null)
		mTvDevicebattery.setText(cushion.getBattery()*20+"");
	}
	
	/**
	 * 更新设备状态
	 */
	private void updateCushionState(UserBean userBean) {
		// TODO Auto-generated method stub
		switch (userBean.getState()) {
		case UserBean.STATE_NORMAL:
			mTvDeviceState.setText(getString(R.string.NORMAL));
			break;
		case UserBean.STATE_VEHICLE:
			mTvDeviceState.setText(getString(R.string.VEHICLE));
			break;
		case UserBean.STATE_MUTE:
			mTvDeviceState.setText(getString(R.string.MUTE));
			break;

		default:
			break;
		}
	}
	
	/**
	 * 更新设备坐姿检查开关
	 */
	private void updatePostureCheck(UserBean userBean) {
		// TODO Auto-generated method stub
		if(userBean.getPosture_check_switch()==UserBean.POSTURE_CHECK_ON){
			mSwitchPostureCheck.setChecked(true);
			
		}else{
			mSwitchPostureCheck.setChecked(false);
		}

	}
	
	/**
	 * 更新设备检查精度控制方式
	 */
	private void updateCushionCheckPrecise(UserBean userBean) {
		// TODO Auto-generated method stub
		switch (userBean.getCheck_precise()) {
		case UserBean.CHECK_PRECISE_NORMAL:
			mTvCheckPreciseCountrol.setText(getString(R.string.NORMAL));
			break;
		case UserBean.CHECK_PRECISE_HIGHT_LEVEL_1:
			mTvCheckPreciseCountrol.setText(getString(R.string.HIGHT_LEVEL_1));
			break;
		case UserBean.CHECK_PRECISE_HIGHT_LEVEL_2:
			mTvCheckPreciseCountrol.setText(getString(R.string.HIGHT_LEVEL_2));
			break;
			
		case UserBean.CHECK_PRECISE_LOW_LEVEL_1:
			mTvCheckPreciseCountrol.setText(getString(R.string.LOW_LEVEL_1));
			break;

		case UserBean.CHECK_PRECISE_LOW_LEVEL_2:
			mTvCheckPreciseCountrol.setText(getString(R.string.LOW_LEVEL_2));
			break;
		default:
			break;
		}
	}
	
	/**
	 * 更新设备温控状态
	 */
	private void updateCushionTemperatureControlState(UserBean userBean) {
		// TODO Auto-generated method stub
		switch (userBean.getTemperature_country_state()) {
		case UserBean.TEMPERATURE_TYPE_AUTO:
			mTvTemperatureCountrolState.setText(getString(R.string.TEMPERATURE_AUTO));
			break;
		case UserBean.TEMPERATURE_TYPE_MATULE:
			mTvTemperatureCountrolState.setText(getString(R.string.TEMPERATURE_MANUAL));
			break;
		default:
			break;
		}
	}
	
	/**
	 * 
	 */
	private void updateCushionData() {
		// TODO Auto-generated method stub
		mUserBean = CushionBeanManager.getInstance().getmCurrectUserBean();
		mcushion = CushionBeanManager.getInstance().getDefaultCushionBean();
		if(mUserBean!=null){
			updateCushionState(mUserBean);
			updatePostureCheck(mUserBean);
			updateCushionCheckPrecise(mUserBean);
			updateCushionTemperatureControlState(mUserBean);
		}else{

			
		}
		updateCushionBattery(mcushion);

	}
	
	/**
	 * 
	 */
	private void showDeviceStatePopWindow() {
		// TODO Auto-generated method stub
		mDeviceStatePop = new DeviceStateControlPop(getActivity(),this);
		mDeviceStatePop.showAtLocation(
				mRootView, Gravity.BOTTOM
						| Gravity.CENTER_HORIZONTAL, 0, 0);
	}
	
	
	/**
	 * 
	 */
	private void showCheckOercisePopWindow() {
		// TODO Auto-generated method stub
		mCheckOercisePop = new CheckPreciseControlPop(getActivity(),this);
		mCheckOercisePop.showAtLocation(
				mRootView, Gravity.BOTTOM
						| Gravity.CENTER_HORIZONTAL, 0, 0);
	}
	
	/**
	 * 
	 */
	private void showTemperatureCountrol() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(getActivity(), TemperatureCountrolStateActivity.class);
		startActivity(intent);
	}
	
	

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_device_state:
			showDeviceStatePopWindow();
			break;
		case R.id.btn_check_precise_countrol:
			showCheckOercisePopWindow();
			break;
		case R.id.btn_temperature_countrol_state:
			showTemperatureCountrol();
			break;
		case R.id.btn_normal:
			dismissPop();
			mTvCheckPreciseCountrol.setText(R.string.NORMAL);
			mUserBean.setCheck_precise(UserBean.CHECK_PRECISE_NORMAL);
			updataCushionSetting(mUserBean);
			break;
		case R.id.btn_hight_level_1:
			mTvCheckPreciseCountrol.setText(R.string.HIGHT_LEVEL_1);
			mUserBean.setCheck_precise(UserBean.CHECK_PRECISE_HIGHT_LEVEL_1);
			dismissPop();
			updataCushionSetting(mUserBean);
			break;
			
		case R.id.btn_hight_level_2:
			mTvCheckPreciseCountrol.setText(R.string.HIGHT_LEVEL_2);
			mUserBean.setCheck_precise(UserBean.CHECK_PRECISE_HIGHT_LEVEL_2);
			dismissPop();
			updataCushionSetting(mUserBean);
			break;		
		case R.id.btn_low_level_1:
			mTvCheckPreciseCountrol.setText(R.string.LOW_LEVEL_1);
			mUserBean.setCheck_precise(UserBean.CHECK_PRECISE_LOW_LEVEL_1);
			dismissPop();
			updataCushionSetting(mUserBean);
			break;
		case R.id.btn_low_level_2:
			mTvCheckPreciseCountrol.setText(R.string.LOW_LEVEL_2);
			mUserBean.setCheck_precise(UserBean.CHECK_PRECISE_LOW_LEVEL_2);
			dismissPop();
			updataCushionSetting(mUserBean);
			break;
		case R.id.btn_cancel:
			dismissPop();
			break;
		case R.id.btn_device_state_normal:
			mTvDeviceState.setText(R.string.NORMAL);
			mUserBean.setState(UserBean.STATE_NORMAL);
			dismissPop();
			updataCushionSetting(mUserBean);
			break;
		case R.id.btn_device_state_vehicle:
			mTvDeviceState.setText(R.string.VEHICLE);
			mUserBean.setState(UserBean.STATE_VEHICLE);
			dismissPop();
			updataCushionSetting(mUserBean);
			break;
		case R.id.btn_device_state_mute:
			mTvDeviceState.setText(R.string.MUTE);
			mUserBean.setState(UserBean.STATE_MUTE);
			dismissPop();
			updataCushionSetting(mUserBean);
			break;
		case R.id.btn_device_state_cancel:
			dismissPop();
			break;
		case R.id.switch_device_posture_check:
			if(mSwitchPostureCheck.isChecked()){
				mSwitchPostureCheck.setChecked(true);
				if(mUserBean!=null){
					mUserBean.setPosture_check_switch(UserBean.POSTURE_CHECK_ON);
				}
			}else{		
				mSwitchPostureCheck.setChecked(false);
				mUserBean.setPosture_check_switch(UserBean.POSTURE_CHECK_OFF);
				if(mUserBean!=null){
					mUserBean.setPosture_check_switch(UserBean.POSTURE_CHECK_OFF);
				}
			}
			updataCushionSetting(mUserBean);
			break;
		case R.id.switch_fan_switch:
			if(mSwitchFanCheck.isChecked()){
				mSwitchFanCheck.setChecked(true);
				if(mUserBean!=null){
					mUserBean.setFan_status(UserBean.FAN_STATUS_ON);

				}
			}else{		
				mSwitchFanCheck.setChecked(false);
				if(mUserBean!=null){
					mUserBean.setFan_status(UserBean.FAN_STATUS_OFF);
				}
			}
			updataFanStatus(mUserBean);
			break;	
			
			
		default:
			break;
		}
		
		
	}

	/**
	 * 
	 */
	private void dismissPop() {
		// TODO Auto-generated method stub
		if(mCheckOercisePop!=null){
			mCheckOercisePop.dismiss();
		}
		if(mDeviceStatePop!=null){
			mDeviceStatePop.dismiss();
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateCushionData();
	}
	
	
	/**
	 * 
	 */
	public void RefreshSetting(int battery) {
		// TODO Auto-generated method stub
		mcushion = CushionBeanManager.getInstance().getDefaultCushionBean();
		mcushion.setBattery(battery);
		updateCushionBattery(mcushion);
	}
	
	
	/**
	 * 
	 */
	private void updataCushionSetting(UserBean bean) {
		// TODO Auto-generated method stub
		if(mcushion!=null){
		mcushion.updateCurrectUserData(bean);
		DbManger.getInstance(getActivity()).updateDbUserInfoData(bean);
		}
	}
	/**
	 * 
	 */
	private void updataFanStatus(UserBean bean) {
		// TODO Auto-generated method stub
		if(mcushion!=null){
		mcushion.OpenFanData(bean);
		DbManger.getInstance(getActivity()).updateDbUserInfoData(bean);
		}
	}

}

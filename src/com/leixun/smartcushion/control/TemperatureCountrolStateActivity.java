/**   
 * @Title:UserInfoActivity.java
 * @Package com.leixun.smartcushion.setting
 * @Description: 
 * @author 姚海军  
 * @date 2016年11月16日上午11:19:11
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年11月16日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.control;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.view.titlebar.AbTitleBar;
import com.leixun.smartcushion.BaseActivity;
import com.leixun.smartcushion.R;
import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.Db.DbManger;
import com.leixun.smartcushion.Sdk.bean.CushionBean;
import com.leixun.smartcushion.Sdk.bean.UserBean;
import com.leixun.smartcushion.view.CustomDialog;
import com.leixun.smartcushion.view.TemperateDialog;

/**
 * @author 姚海军
 *
 */
public class TemperatureCountrolStateActivity extends BaseActivity implements OnClickListener{

	private AbTitleBar mAbTitleBar;
	private LinearLayout mbtnAutoTemperatureCountrol,mbtnManualTemperatureCountrol ;
	private ImageView mSwitchAutoTemperatureCountrol,mSwitchManualTemperatureCountrol;
	private TextView mTemperatureValue;
	private CushionBean mcushion= new CushionBean();
	private UserBean mUserBean= new UserBean();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.temperature_countrol_state_layout);
		initView();
		initData();
		initTitlebar();
		initListener();
	}
	
	/**
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		mbtnAutoTemperatureCountrol = (LinearLayout) findViewById(R.id.btn_auto_temperature_countrol);
		mbtnManualTemperatureCountrol = (LinearLayout) findViewById(R.id.btn_manual_temperature_countrol);
		mSwitchAutoTemperatureCountrol = (ImageView) findViewById(R.id.switch_auto_temperature_countrol);
		mSwitchManualTemperatureCountrol = (ImageView) findViewById(R.id.switch_manual_temperature_countrol);
		mTemperatureValue = (TextView) findViewById(R.id.tv_temperature_value);
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
	private void updateCushionData() {
		// TODO Auto-generated method stub
		mUserBean = CushionBeanManager.getInstance().getmCurrectUserBean();
		mcushion = CushionBeanManager.getInstance().getDefaultCushionBean();
		if(mUserBean!=null){
			updateCushionTemperatureControlState(mUserBean);
			
		}else{
			
			
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
				R.string.TEMPERATURE_CONTROL_STATE));
		mAbTitleBar.setLogo(R.drawable.back_arrow);
		mAbTitleBar.setTitleBarBackground(R.drawable.nav_bar);
//		View rightViewMore = mInflater.inflate(R.layout.share_btn, null);
//		shareButton = (Button) rightViewMore.findViewById(R.id.ShareBtn);
		mAbTitleBar.clearRightView();
//		mAbTitleBar.addRightView(rightViewMore);
		mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);
		mAbTitleBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	/**
	 * 
	 */
	private void initListener() {
		// TODO Auto-generated method stub
		mbtnAutoTemperatureCountrol.setOnClickListener(this);
		mbtnManualTemperatureCountrol.setOnClickListener(this);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_auto_temperature_countrol:
			mSwitchAutoTemperatureCountrol.setImageResource(R.drawable.checkmark);
			mSwitchManualTemperatureCountrol.setImageResource(R.drawable.checkmark_normal);
			mUserBean.setTemperature_country_state(UserBean.TEMPERATURE_TYPE_AUTO);
			updataCushionSetting(mUserBean);
			break;
		case R.id.btn_manual_temperature_countrol:

			final TemperateDialog.Builder builder = new TemperateDialog.Builder(this,Integer.parseInt(mTemperatureValue.getText().toString()));  
			builder.setNegativeButton(new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.setPositiveButton(new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					mTemperatureValue.setText(builder.mtemperatureValue+"");
					mUserBean.setTemperature_country_state(UserBean.TEMPERATURE_TYPE_MATULE);
					mUserBean.setTemperature_country_value(builder.mtemperatureValue);
					updataCushionSetting(mUserBean);
					mSwitchAutoTemperatureCountrol.setImageResource(R.drawable.checkmark_normal);
					mSwitchManualTemperatureCountrol.setImageResource(R.drawable.checkmark);
				}
			});
	        builder.create().show();
			break;

		default:
			break;
		}
		
	}
	
	
	/**
	 * 
	 */
	private void updataCushionSetting(UserBean bean) {
		// TODO Auto-generated method stub
		if(mcushion!=null){
		mcushion.updateCurrectUserData(bean);
		CushionBeanManager.getInstance().setmCurrectUserBean(bean);
		DbManger.getInstance(this).updateDbUserInfoData(bean);
		}
	}
	
	/**
	 * 更新设备温控状态
	 */
	private void updateCushionTemperatureControlState(UserBean userBean) {
		// TODO Auto-generated method stub
		switch (userBean.getTemperature_country_state()) {
		case UserBean.TEMPERATURE_TYPE_AUTO:
			mSwitchAutoTemperatureCountrol.setImageResource(R.drawable.checkmark);
			mSwitchManualTemperatureCountrol.setImageResource(R.drawable.checkmark_normal);
			break;
		case UserBean.TEMPERATURE_TYPE_MATULE:
			mSwitchAutoTemperatureCountrol.setImageResource(R.drawable.checkmark_normal);
			mSwitchManualTemperatureCountrol.setImageResource(R.drawable.checkmark);
			break;
		default:
			break;
		}
	}
	
}

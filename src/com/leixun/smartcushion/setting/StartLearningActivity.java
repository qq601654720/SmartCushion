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
package com.leixun.smartcushion.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.ab.view.titlebar.AbTitleBar;
import com.leixun.smartcushion.BaseActivity;
import com.leixun.smartcushion.LearningActivity;
import com.leixun.smartcushion.R;
import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.Db.DbManger;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateLearnStatus;
import com.leixun.smartcushion.Sdk.bean.CushionBean;
import com.leixun.smartcushion.Sdk.bean.UserBean;
import com.leixun.smartcushion.Sdk.perference.CushionPreferences;

/**
 * @author 姚海军
 *
 */
public class StartLearningActivity extends BaseActivity implements OnClickListener,UpdateLearnStatus{

	private AbTitleBar mAbTitleBar;
	private Button mButtonStartLearn;
	private CushionBean mcushionBean;
	UserBean mbean = new UserBean();
	private final int SHOW_LEARNING_DIALOG = 0X01;
	Toast toast;
	
	private Handler mhHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(toast!=null){
				toast.cancel();
			}
			switch (msg.what) {
				case SHOW_LEARNING_DIALOG:
//					final CustomDialog.Builder builder = new CustomDialog.Builder(StartLearningActivity.this);
//					builder.setTitle("正在学习...");
//					builder.loadindDialog().show();
					Intent intent = new Intent();
					intent.setClass(StartLearningActivity.this, LearningActivity.class);
					startActivity(intent);
					finish();
					break;
				
			}
		
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.start_posture_learning_layout);
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
		mButtonStartLearn = (Button) findViewById(R.id.btn_start_learning);
	}
	
	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
		CushionBeanManager.getInstance().setmUpdateLearnStatus(this);
		
		mcushionBean = CushionBeanManager.getInstance().getDefaultCushionBean();
		mbean = CushionBeanManager.getInstance().getmEditUserBean();
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
		mAbTitleBar.setTitleBarBackground(R.drawable.nav_bar);
		mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);
	}
	
	/**
	 * 
	 */
	private void initListener() {
		// TODO Auto-generated method stub
		mButtonStartLearn.setOnClickListener(this);
	}


	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(mcushionBean!=null){
			mcushionBean.IntoPostureLearning(mbean);
			if(DbManger.getInstance(StartLearningActivity.this).queryData(String.valueOf(mbean.getUserId())).size()>0){
				DbManger.getInstance(StartLearningActivity.this).updateDbUserInfoData(mbean);
			}else{
				DbManger.getInstance(StartLearningActivity.this).saveDbUserInfoData(mbean);
			}
			CushionBeanManager.getInstance().setmCurrectUserBean(mbean);
			CushionPreferences.getInstance(StartLearningActivity.this).setCurrectUser(String.valueOf(mbean.getUserId()));
			CushionBeanManager.getInstance().AddBeanToBeans(mbean);

			
		}
	}

	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateLearnStatus#UpdateIntoLearnSuccess()
	 */
	@Override
	public void UpdateIntoLearnSuccess() {
		// TODO Auto-generated method stub
		mhHandler.sendEmptyMessage(SHOW_LEARNING_DIALOG);
	}

	
	
}

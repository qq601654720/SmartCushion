/**   
 * @Title:LearningActivity.java
 * @Package com.leixun.smartcushion
 * @Description: 
 * @author 姚海军  
 * @date 2017年1月12日上午9:27:47
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2017年1月12日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.Db.DbManger;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateLearnResult;
import com.leixun.smartcushion.Sdk.bean.LearnBean;
import com.leixun.smartcushion.Sdk.bean.UserBean;
import com.leixun.smartcushion.Sdk.perference.CushionPreferences;

/**
 * @author 姚海军
 *
 */
public class LearningActivity extends BaseActivity implements UpdateLearnResult {
	private AnimationDrawable animationDrawable; 
	UserBean mbean = new UserBean();
//	private final int SHOW_LEARNING_DIALOG = 0X01;
	private final int LEARN_POSTURE_NO_PERSON = 0X02;
	private final int LEARN_POSTURE_RIGHT = 0X03;
	private final int LEARN_POSTURE_WRONG = 0X04;
	Toast toast;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.learning_activity_layout);
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
		ImageView imageView = (ImageView)this.findViewById(R.id.img_learning);   
		//开始执行动画  
		imageView.setBackgroundResource(R.anim.learning_anim);
		animationDrawable = (AnimationDrawable) imageView.getBackground(); 
		animationDrawable.start();  
	}
	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
		CushionBeanManager.getInstance().setmUpdateLearnResult(this);
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
				R.string.POSTURE_LEARNING));
		mAbTitleBar.setTitleBarBackground(R.drawable.nav_bar);
		mAbTitleBar.clearRightView();
		mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);
	}
	
	
	private void initListener() {
		// TODO Auto-generated method stub

	}


	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateLearnResult#UpdateLearnResultSuccess(com.leixun.smartcushion.Sdk.bean.LearnBean)
	 */
	@Override
	public void UpdateLearnResultSuccess(LearnBean learnBean) {
		// TODO Auto-generated method stub
		
		switch (learnBean.getPosture()) {
		case LearnBean.POSTURE_NO_PERSON:
			mhHandler.sendEmptyMessage(LEARN_POSTURE_NO_PERSON);
			break;
		case LearnBean.POSTURE_RIGHT:
			mhHandler
			.sendEmptyMessage(LEARN_POSTURE_RIGHT);
			break;
		case LearnBean.POSTURE_WRONG:
			Message message = new Message();
			message.what = LEARN_POSTURE_WRONG;
			
			switch (learnBean.getPosture_status()) {
			case LearnBean.POSTURE_STATUS_LHT:
				message.arg1 = LearnBean.POSTURE_STATUS_LHT;
				break;
			case LearnBean.POSTURE_STATUS_RHT:
				message.arg1 = LearnBean.POSTURE_STATUS_RHT;
				break;
			case LearnBean.POSTURE_STATUS_FOHT:
				message.arg1 = LearnBean.POSTURE_STATUS_FOHT;
				break;
			case LearnBean.POSTURE_STATUS_AFHT:
				message.arg1 = LearnBean.POSTURE_STATUS_AFHT;
				break;
			case LearnBean.POSTURE_STATUS_OTHER:
				
				break;
			default:
				break;
			}
			
			mhHandler.sendMessage(message);
			break;

		default:
			break;
		}
		
	}
	
	private Handler mhHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(toast!=null){
				toast.cancel();
			}
			switch (msg.what) {
				case LEARN_POSTURE_NO_PERSON:
//					toast = Toast.makeText(LearningActivity.this, "当前坐垫未坐人", Toast.LENGTH_SHORT);
//					toast.show();
					break;
				case LEARN_POSTURE_RIGHT:
					toast =Toast.makeText(LearningActivity.this, "当前坐姿正确", Toast.LENGTH_SHORT);
					toast.show();
					Intent intent = new Intent();
					intent.setClass(LearningActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
					break;
				case LEARN_POSTURE_WRONG:
					switch (msg.arg1) {
					case LearnBean.POSTURE_STATUS_LHT:
						toast =Toast.makeText(LearningActivity.this, "当前坐姿状态左偏", Toast.LENGTH_SHORT);
						break;
					case LearnBean.POSTURE_STATUS_RHT:
						toast =Toast.makeText(LearningActivity.this, "当前坐姿状态右偏", Toast.LENGTH_SHORT);
						break;
					case LearnBean.POSTURE_STATUS_FOHT:
						toast =Toast.makeText(LearningActivity.this, "当前坐姿状态后偏", Toast.LENGTH_SHORT);
						break;
					case LearnBean.POSTURE_STATUS_AFHT:
						toast =Toast.makeText(LearningActivity.this, "当前坐姿状态前偏", Toast.LENGTH_SHORT);
						break;
					case LearnBean.POSTURE_STATUS_OTHER:
						
						break;
					default:
						break;
						
					}
					toast.show();
					break;
					
					
			}
		
		}
	};
	
	@Override
	public void onBackPressed() {
		
//		super.onBackPressed();
		Intent intent = new Intent();
		intent.setClass(LearningActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
		
	};

}

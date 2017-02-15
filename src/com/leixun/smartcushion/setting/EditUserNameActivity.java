/**   
 * @Title:EditUserNameActivity.java
 * @Package com.leixun.smartcushion.setting
 * @Description: 
 * @author 姚海军  
 * @date 2016年11月18日下午3:47:05
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年11月18日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.leixun.smartcushion.BaseActivity;
import com.leixun.smartcushion.R;
import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.bean.UserBean;

/**
 * @author 姚海军
 *
 */
public class EditUserNameActivity extends BaseActivity implements OnClickListener,OnCheckedChangeListener{
	
	private EditText meditUserName,meditUserAge,meditUserWeight,meditUserStsture;
	private RadioGroup  mradioGroupSex;
	private Button mBtnNext;
	RadioButton rb ;
	UserBean mbean = new UserBean();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.edit_user_info_layout);
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
		meditUserName = (EditText) findViewById(R.id.editText_user_name);
		meditUserAge = (EditText) findViewById(R.id.editText_user_age);
		meditUserStsture = (EditText) findViewById(R.id.editText_user_ststure);
		meditUserWeight = (EditText) findViewById(R.id.editText_user_weight);
		mradioGroupSex = (RadioGroup) findViewById(R.id.radioGroupSex);
		mBtnNext = (Button) findViewById(R.id.btn_next);
		
	}
	
	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		mbean = (UserBean) intent.getSerializableExtra("CURRECT_USER");
	
		if(mbean==null){
			mbean = new UserBean();
			long time = System.currentTimeMillis() / 1000;
			// CRC16 crc16 = new CRC16();
			mbean.setUserId(String.valueOf(time));
		}else{
			meditUserName.setText(mbean.getUserName()+"");
			meditUserAge.setText(mbean.getUserAge()+"");
			meditUserWeight.setText(mbean.getUserWeight()+"");
			meditUserStsture.setText(mbean.getUserStature()+"");
		}
		
		if (mbean.getUserSex().equals("男")) {
				mradioGroupSex.check(R.id.radio_man); 
			}else{
				mradioGroupSex.check(R.id.radio_women); 	
		}

	}
	
	/**
	 * 
	 */
	private void initTitlebar() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 */
	private void initListener() {
		// TODO Auto-generated method stub
		mBtnNext.setOnClickListener(this);
		mradioGroupSex.setOnCheckedChangeListener(this);
		
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(meditUserName.getText().toString().equals("")||meditUserAge.getText().toString().equals("")||meditUserWeight.getText().toString().equals("")||meditUserStsture.getText().toString().equals("")){
			Toast.makeText(this, "请完整填写用户信息", Toast.LENGTH_LONG).show();
			return;
		}
		updateUserData();
		Intent it = new Intent();
		it.setClass(this, StartLearningActivity.class);
		startActivity(it);
		
	}

	/* (non-Javadoc)
	 * @see android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android.widget.RadioGroup, int)
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		int radioButtonId = group.getCheckedRadioButtonId();
		rb = (RadioButton)this.findViewById(radioButtonId);
		Toast.makeText(this, "性别是"+rb.getText(), 100).show();
		mbean.setUserSex(rb.getText().toString());
	}
	
	
	/**
	 * 
	 */
	private void updateUserData() {
		// TODO Auto-generated method stub
		mbean.setUserName(meditUserName.getText().toString()+"");
		mbean.setUserAge(Integer.parseInt(meditUserAge.getText().toString()));
		mbean.setUserWeight(Integer.parseInt(meditUserWeight.getText().toString()));
		mbean.setUserStature(Integer.parseInt(meditUserStsture.getText().toString()));
		CushionBeanManager.getInstance().setmEditUserBean(mbean);
	}

}

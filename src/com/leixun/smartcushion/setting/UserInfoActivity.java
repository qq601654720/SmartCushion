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
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.view.titlebar.AbTitleBar;
import com.leixun.smartcushion.BaseActivity;
import com.leixun.smartcushion.R;
import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.Db.DbManger;
import com.leixun.smartcushion.Sdk.bean.UserBean;
import com.leixun.smartcushion.setting.adapter.UserInfoAdapter;

/**
 * @author 姚海军
 *
 */
public class UserInfoActivity extends BaseActivity implements OnItemClickListener{

	private AbTitleBar mAbTitleBar;
	UserInfoAdapter mUserInfoAdapter;
	ListView mlistviewUserInfo;
	private TextView editButton;
	private UserBean mUserBean;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.setting_user_info_layout);
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
		mlistviewUserInfo = (ListView) findViewById(R.id.listviewUserInfo);
	}
	
	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
		mUserBean = CushionBeanManager.getInstance().getmCurrectUserBean();
		mUserInfoAdapter = new UserInfoAdapter(this, mUserBean); 
		mlistviewUserInfo.setAdapter(mUserInfoAdapter);
	}
	
	/**
	 * 
	 */
	private void initTitlebar() {
		// TODO Auto-generated method stub
		mAbTitleBar = this.getTitleBar();
		mAbTitleBar.getTitleTextButton().setPadding(0, 35, 0, 35);
		mAbTitleBar.setTitleText(getResources().getString(
				R.string.USER_INFO));
		mAbTitleBar.setLogo(R.drawable.back_arrow);
		mAbTitleBar.setTitleBarBackground(R.drawable.nav_bar);
		View rightViewMore = mInflater.inflate(R.layout.user_info_edit_button_layout, null);
		editButton = (TextView) rightViewMore.findViewById(R.id.btn_edit);
		mAbTitleBar.clearRightView();
		mAbTitleBar.addRightView(rightViewMore);
		mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);
		
		editButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UserInfoActivity.this, EditUserNameActivity.class);
				Bundle mBundle = new Bundle();  
				mBundle.putSerializable("CURRECT_USER", mUserBean);
		        intent.putExtras(mBundle); 
				startActivity(intent);
			}
		});
		
		mAbTitleBar.getLogoView().setOnClickListener(new OnClickListener() {
			
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
		mlistviewUserInfo.setOnItemClickListener(this);
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		DbManger.getInstance(this);
	}

	
	
}

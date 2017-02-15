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
package com.leixun.smartcushion.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ab.fragment.AbFragment;
import com.ab.view.sliding.AbSlidingPlayView;
import com.leixun.smartcushion.R;
import com.leixun.smartcushion.Sdk.CushionBeanManager;

/**
 * @author 姚海军
 *
 */
public class SettingFragment extends AbFragment implements OnClickListener{
	private View mRootView;
	AbSlidingPlayView mSlidingPlayView = null;
	LinearLayout mBtnUserIno,mBtnUserSwitch;
	@Override
	public View onCreateContentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		mRootView = inflater.inflate(R.layout.setting_fragment_layout,
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
		initData();
		initListener();
		return mRootView;
	}
	
	/**
	 * 
	 */
	private void initView(View view) {
		// TODO Auto-generated method stub
		mBtnUserSwitch = (LinearLayout) mRootView.findViewById(R.id.btn_user_switch);
		mBtnUserIno = (LinearLayout) mRootView.findViewById(R.id.btn_user_user_info);
	}
	
	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 
	 */
	private void initListener() {
		// TODO Auto-generated method stub
		mBtnUserSwitch.setOnClickListener(this);
		mBtnUserIno.setOnClickListener(this);
	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {

		case R.id.btn_user_switch:
			intent.setClass(getActivity(), UserListActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_user_user_info:
//			intent.setClass(getActivity(), DeviceListActivity.class);
//			startActivity(intent);
			intent.setClass(getActivity(), UserInfoActivity.class);
			Bundle mBundle = new Bundle();  
			mBundle.putSerializable("CURRECT_USER", CushionBeanManager.getInstance().getmCurrectUserBean());
	        intent.putExtras(mBundle); 
			startActivity(intent);
			break;

		default:
			break;
		}
		
		
		

	}
	

}

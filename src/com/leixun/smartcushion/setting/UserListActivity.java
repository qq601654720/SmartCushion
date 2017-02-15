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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ab.view.titlebar.AbTitleBar;
import com.leixun.smartcushion.BaseActivity;
import com.leixun.smartcushion.R;
import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.Db.DbManger;
import com.leixun.smartcushion.Sdk.bean.CushionBean;
import com.leixun.smartcushion.Sdk.bean.UserBean;

/**
 * @author 姚海军
 *
 */
public class UserListActivity extends BaseActivity implements OnItemClickListener{

	private AbTitleBar mAbTitleBar;
	UserListAdapter mUserListAdapter;
	ListView mlistviewUserInfo;
	private TextView addButton;
	private UserBean mUserBean;
	private List<UserBean> mUserBeans;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.user_list_layout);
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
		mlistviewUserInfo = (ListView) findViewById(R.id.lv_users);
	}
	
	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
		mUserBean = CushionBeanManager.getInstance().getmCurrectUserBean();
		mUserBeans= CushionBeanManager.getInstance().getBeans();
		mUserListAdapter = new UserListAdapter(this,mUserBeans, mUserBean); 
		mlistviewUserInfo.setAdapter(mUserListAdapter);
	}
	
	/**
	 * 
	 */
	private void initTitlebar() {
		// TODO Auto-generated method stub
		mAbTitleBar = this.getTitleBar();
		mAbTitleBar.getTitleTextButton().setPadding(0, 35, 0, 35);
		mAbTitleBar.setTitleText(getResources().getString(
				R.string.SWITCH_USER));
		mAbTitleBar.setLogo(R.drawable.back_arrow);
		mAbTitleBar.setTitleBarBackground(R.drawable.nav_bar);
		View rightViewMore = mInflater.inflate(R.layout.user_info_add_button_layout, null);
		addButton = (TextView) rightViewMore.findViewById(R.id.btn_add);
		mAbTitleBar.clearRightView();
		mAbTitleBar.addRightView(rightViewMore);
		mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);
		
		addButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UserListActivity.this, EditUserNameActivity.class);
				Bundle mBundle = new Bundle();  
				mBundle.putSerializable("CURRECT_USER", null);
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
		mUserBean = mUserBeans.get(position);
		CushionBeanManager.getInstance().setmCurrectUserBean(mUserBean);
		mUserListAdapter.updateConnectingSelect(mUserBean);
		updateCushionSetting(mUserBean);
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
	
	
	
	// Adapter for holding devices found through scanning.
		public class UserListAdapter extends BaseAdapter {
			private List<UserBean> mUserBeans = new ArrayList<UserBean>();
			private UserBean mUserBean;
			private LayoutInflater mInflator;

			public UserListAdapter(Context context,List<UserBean> userBeans,UserBean UserBean) {
				super();
				mUserBeans = userBeans;
				mInflator = LayoutInflater.from(context);
				mUserBean = UserBean;
			}

			@Override
			public int getCount() {
				return mUserBeans.size();
			}

			@Override
			public Object getItem(int i) {
				return mUserBeans.get(i);
			}

			@Override
			public long getItemId(int i) {
				return i;
			}
			

			@Override
			public View getView(int position, View convertView, ViewGroup viewGroup) {
				ViewHolder viewHolder;
				// General ListView optimization code.
				if (convertView == null) {
					convertView = mInflator.inflate(R.layout.device_manger_item,
							viewGroup, false);
					viewHolder = new ViewHolder();
					viewHolder.tvItem = (TextView) convertView
							.findViewById(R.id.custom_tv_setting);
					viewHolder.imgSelected = (ImageView) convertView
							.findViewById(R.id.custom_img_check);
					viewHolder.progressBar = (ProgressBar) convertView
							.findViewById(R.id.progress_loading);
					convertView.setTag(viewHolder);
				} else {
					viewHolder = (ViewHolder) convertView.getTag();
				}

				UserBean userBean = mUserBeans.get(position);
				final String userName = userBean.getUserName();
				
//				if (selectPos == position) {
//					viewHolder.progressBar.setVisibility(View.VISIBLE);
//				} else {
//					viewHolder.progressBar.setVisibility(View.GONE);
//				}
				
				if(mUserBean.getUserId().equals(userBean.getUserId())){
					viewHolder.imgSelected.setImageResource(R.drawable.checkmark);
				}else{
					viewHolder.imgSelected.setImageResource(R.drawable.checkmark_normal);
				}
				if (userName != null && userName.length() > 0)
					viewHolder.tvItem.setText(userName);
				else
					viewHolder.tvItem.setText(R.string.unknown_device);

				return convertView;
			}

			/**
			 * 
			 */
			public void updateConnectingSelect(UserBean bean) {
				// TODO Auto-generated method stub
				mUserBean = bean;
				notifyDataSetChanged();
			}
			
			/**
			 * 刷新当前连接的设备
			 */
			public void updateConnectedSelect(int position) {
				// TODO Auto-generated method stub
				notifyDataSetChanged();
			}
		}

		class ViewHolder {
			public TextView tvItem;
			public ImageView imgSelected;
			public ProgressBar progressBar;
		}
		
		
		/**
		 * 
		 */
		private void updateCushionSetting(UserBean userBean) {
			// TODO Auto-generated method stub
			CushionBean bean = CushionBeanManager.getInstance().getDefaultCushionBean();
			if(bean != null){
				bean.updateCurrectUserData(userBean);
			}
		}
	
	
}

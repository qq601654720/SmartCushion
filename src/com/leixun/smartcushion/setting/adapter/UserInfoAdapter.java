/**   
 * @Title:adapter.java
 * @Package com.leixun.smartcushion.setting
 * @Description: 
 * @author 姚海军  
 * @date 2016年11月16日下午12:19:21
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年11月16日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.setting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leixun.smartcushion.R;
import com.leixun.smartcushion.Sdk.bean.UserBean;

/**
 * @author 姚海军
 *
 */
public class UserInfoAdapter extends BaseAdapter{
	private UserBean mUserBean = null;
	private Context mContext = null;
	
	
	/**
	 * 
	 */
	public UserInfoAdapter(Context context,UserBean bean) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mUserBean = bean;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.list_view_item_layout, parent, false);
			holder.userTitle = (TextView) convertView.findViewById(R.id.tv_item_title);
			holder.userValue = (TextView) convertView.findViewById(R.id.tv_item_value);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		switch (position) {
		case 0:
			holder.userTitle.setText(mContext.getString(R.string.USER_NAME));
			holder.userValue.setText(mUserBean.getUserName());
			break;
		case 1:
			holder.userTitle.setText(mContext.getString(R.string.USER_SEX));
			if(mUserBean.getUserSex().equals("男")){
				holder.userValue.setText(mContext.getString(R.string.MAN));
			}else{
				holder.userValue.setText(mContext.getString(R.string.WOMAN));
			}			
			break;
		case 2:
			holder.userTitle.setText(mContext.getString(R.string.USER_AGE));
			holder.userValue.setText(mUserBean.getUserAge()+mContext.getString(R.string.USER_AGE_UNIT));
			break;
		case 3:
			holder.userTitle.setText(mContext.getString(R.string.USER_STSTURE));
			holder.userValue.setText(mUserBean.getUserStature()+mContext.getString(R.string.USER_STSTURE_UNIT));
			break;
		case 4:
			holder.userTitle.setText(mContext.getString(R.string.USER_WEIGHT));
			holder.userValue.setText(mUserBean.getUserWeight()+mContext.getString(R.string.USER_WEIGHT_UNIT));
			break;

		default:
			break;
		}
		return convertView;
	}

	
	class ViewHolder {
		TextView userTitle;
		TextView userValue;
	}
}

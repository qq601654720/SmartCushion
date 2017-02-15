/******************************************************************************
 * File: CushionPreferences.java
 * Author: yaohaijun
 * Create Date : 2016-03-16
 * JDK version used:      <JDK1.6> 
 * Version : V1.0
 * Description :  
 *  
 *  
 *  
 * History :
 *  1. yaohaijun  add for the first release ,  2016-03-16
 *
 * 
 * Copyright (C), 2001-2011, Xi'an TCL Software Development Co.,Ltd
 * All rights reserved
 ******************************************************************************/

package com.leixun.smartcushion.Sdk.perference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @description
 * @author yaohaijun
 * @date  2016-03-16
 */
public class CushionPreferences {

	private static CushionPreferences mCushionPreferences = null;
	private Context mContext;
	private final String TAG = "CushionPreferences";
	private final String CURRECTCONNECTORCUSHION = "CURRECTCONNECTORCUSHION";
	private final String CURRECTUSER = "CURRECTUSER";
	public static final CushionPreferences getInstance(Context context) {
		if (mCushionPreferences == null) {
			mCushionPreferences = new CushionPreferences(
					context.getApplicationContext());
		}

		return mCushionPreferences;
	}

	private CushionPreferences(Context context) {
		mContext = context;
	}

	private SharedPreferences getPreference() {
		return mContext.getSharedPreferences(mContext.getPackageName(),
				Context.MODE_PRIVATE);

	}

	public void setCurrectConnectorCushion(String value) {
		SharedPreferences pre = getPreference();
		Editor editor = pre.edit();
		editor.putString(CURRECTCONNECTORCUSHION, value);
		editor.commit();
	}

	public String getCurrectConnectorCushion(String defValue) {
		SharedPreferences pre = getPreference();
		String value = pre.getString(CURRECTCONNECTORCUSHION, defValue);
		return value;
	}
	
	
	public void setCurrectUser(String value) {
		SharedPreferences pre = getPreference();
		Editor editor = pre.edit();
		editor.putString(CURRECTUSER, value);
		editor.commit();
	}

	public String getCurrectUser(String defValue) {
		SharedPreferences pre = getPreference();
		String value = pre.getString(CURRECTUSER, defValue);
		return value;
	}

}

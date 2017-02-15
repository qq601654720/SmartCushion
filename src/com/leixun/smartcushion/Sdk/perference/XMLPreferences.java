/******************************************************************************
 * File: SettingPreferrences.java
 * Author: Administrator
 * Create Date : 2013-7-9
 * JDK version used:      <JDK1.6> 
 * Version : V1.0
 * Description :  
 *  
 *  
 *  
 * History :
 *  1. Administrator add for the first release , 2013-7-9  
 *
 * 
 * Copyright (C), 2001-2011, Xi'an TCL Software Development Co.,Ltd
 * All rights reserved
 ******************************************************************************/

package com.leixun.smartcushion.Sdk.perference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * 
 * @description
 * @author wuyj
 * @date 2014-9-24 下午3:32:28
 */
public class XMLPreferences {

    private static XMLPreferences mXMLPreferences = null;
    private Context mContext;
    private final String TAG_NAME = "XMLPreferences";
    public static final String IS_FIRST_START_APPLICATION = "isFirstStart";

    public static final XMLPreferences getInstance(Context context) {
        if (mXMLPreferences == null) {
            mXMLPreferences = new XMLPreferences(context.getApplicationContext());
        }
        
        return mXMLPreferences;
    }
    
    private XMLPreferences(Context context){
    	mContext = context;
    }
    
    private SharedPreferences getPreference(){
        return mContext.getSharedPreferences(TAG_NAME, Context.MODE_PRIVATE);
    }
    
    
    /**
     * 
     * @param defValue
     * @return
     */
    public boolean getIsFirstStartApplication(boolean defValue){
    	boolean isFirst = true;
    	SharedPreferences pre = getPreference();
    	isFirst = pre.getBoolean(IS_FIRST_START_APPLICATION,
				defValue);

		
		return isFirst;
    }
    
    /**
     * 
     * @param value
     */
    public void setIsFirstStartApplication(boolean value){
		SharedPreferences pre = getPreference();
    	Editor editor = pre.edit();
    	editor.putBoolean(IS_FIRST_START_APPLICATION, value);
    	editor.commit();
		
    }
  
  
    public void remaidaccount(String account){
    	 SharedPreferences pre = getPreference();
     	 Editor editor = pre.edit();
     	 editor.putString("account", account);
        
         editor.commit();
    }
    public void deleteaccount(){
   	 SharedPreferences pre = getPreference();
    	 Editor editor = pre.edit();
    	 editor.remove("account");
   
    	 editor.commit();
   
   }
    /**
     * 
     * @param defValue
     * @return
     */
    public Map<String, String> getLoginShareprefernces(){
    	Map<String, String> map  = new HashMap<String, String>();
    	SharedPreferences pre = getPreference();
    	map.put("userid", pre.getString("userid", ""));
    	map.put("token", pre.getString("token", ""));
    	map.put("username", pre.getString("username", ""));
    	map.put("state", pre.getString("state", ""));	
		return map;
    }
    
    public void setLoginShareprefernces(String username,String userid,String token,String state){
	   SharedPreferences pre = getPreference();
   	   Editor editor = pre.edit();
   	   editor.putString("userid", userid);
       editor.putString("token",token);
       editor.putString("username", username);
       editor.putString("state", state);
       editor.commit();
	 
   }
    public void setRadarNoticeState(boolean isopen){
    	SharedPreferences pre = getPreference();
    	Editor editor = pre.edit();
    	editor.putBoolean("noticeState", isopen);
    	editor.commit();
    }
    public boolean getRadarNoticeState(){
    	boolean isopen = true;
    	SharedPreferences pre = getPreference();
    	isopen = pre.getBoolean("noticeState", false);
    	return isopen;
    }
    
    public String getAccount(){
    	String Account = "";
    	SharedPreferences pre = getPreference();
    	Account = pre.getString("account", "");
    		
		return Account;
    }
    
    /**
     * 
     *@title: 
     *@description:
     *@param:@param array
     *@param:@return
     *@return:String
     *@throws
     */
	public String stringArrayToString(List<String> array){
		if(array != null){
			StringBuilder builder = new StringBuilder();
			final int len = array.size();
			for(int i=0; i<len; i++){
				builder.append(array.get(i)).append(File.separator);
			}
			return builder.toString();
		}
		return null;
	}
	
	/**
	 * 
	 *@title: 
	 *@description:
	 *@param:@param value
	 *@param:@return
	 *@return:List<String>
	 *@throws
	 */
	public List<String> stringToStringArray(String value){
		if(TextUtils.isEmpty(value)){
			return null;
		}
		
		String[] array = value.split(File.separator);
		ArrayList<String> list = new ArrayList<String>();
		final int len = array.length;
		for(int i=0; i<len; i++){
			list.add(array[i]);
		}
		
		return list;
	}
 
}

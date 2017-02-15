/******************************************************************************
 * File: ResourceManager.java
 * Author: wuyongjiang
 * Create Date : 2014年12月12日
 * JDK version used: <JDK1.6> 
 * Version : V1.0
 * Description : 
 * 
 * 
 * 
 * History :
 * 1. wuyj add for the first release , 2014年12月12日 
 *
 * 
 * Copyright (C), 2012-2013, Xi'an TCL Software Development Co.,Ltd
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.Sdk.util;

import android.content.Context;

/**
 * @description
 * @author wuyj
 * @date 2014年12月12日 上午10:59:32 
 */
public class ResourceManager {
    
    private static ResourceManager mResourceManager;
    private Context mContext;
    
    public static ResourceManager getInstance(){
        if(mResourceManager == null){
            mResourceManager = new ResourceManager();
        }
        return mResourceManager;
    }
    
    /**
     * 
     *@title: 
     *@description:
     *@param:@param context
     *@return:void
     *@throws
     */
    public void setApplicationContext(Context context){
        mContext = context;
    }
    /**
     * 
     *@title: 
     *@description:
     *@param:@return
     *@return:Context
     *@throws
     */
    public Context getApplicationContext(){
        return mContext;
    }
}

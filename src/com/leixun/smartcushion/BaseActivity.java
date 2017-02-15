/**   
 * @Title:BaseActivity.java
 * @Package com.leixun.smartcushion
 * @Description: 
 * @author 姚海军  
 * @date 2016年11月15日下午2:59:39
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年11月15日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion;

import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.bean.CushionBean;


/**
 * @author 姚海军
 *
 */
public class BaseActivity extends BaseActivity2 {
	

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		CushionBean mcushion = CushionBeanManager.getInstance().getDefaultCushionBean();
		if(mcushion == null){
			mHandler.sendEmptyMessage(SHOW_ERR_DATA_DIALOG);
		}

	}

	
}

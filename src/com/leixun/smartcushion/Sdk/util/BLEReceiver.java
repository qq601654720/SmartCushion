/**   
 * @Title:BLEReceiver.java
 * @Package com.huawei.smallRadar.Sdk.Util
 * @Description: 
 * @author 姚海军  
 * @date 2016年9月19日下午4:07:44
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年9月19日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.Sdk.util;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.leixun.smartcushion.Sdk.CushionBeanManager;

/**
 * @author 姚海军
 *
 */
public class BLEReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 接收广播：系统启动完成后运行程序
		CushionBeanManager manager = CushionBeanManager.getInstance();
		if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
			int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
					BluetoothAdapter.ERROR);
			switch (state) {
			case BluetoothAdapter.STATE_OFF:
				if (manager.getmBtSwitchInterface() != null) {
					manager.getmBtSwitchInterface().onBtSwitechOff();
				}
				break;
			case BluetoothAdapter.STATE_TURNING_OFF:
				Log.d("aaa", "STATE_TURNING_OFF 手机蓝牙正在关闭");
				break;
			case BluetoothAdapter.STATE_ON:
				Log.d("aaa", "STATE_ON 手机蓝牙开启");
				if (manager.getmBtSwitchInterface() != null) {
					manager.getmBtSwitchInterface().onBtSwitechOn();
				}
				break;
			case BluetoothAdapter.STATE_TURNING_ON:
				Log.d("aaa", "STATE_TURNING_ON 手机蓝牙正在开启");
				break;
			}
		}
	}
}
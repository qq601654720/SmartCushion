/******************************************************************************
 * File: BletoothLeInterface.java
 * Author: wuyongjiang
 * Create Date : 2015年6月29日
 * JDK version used: <JDK1.6> 
 * Version : V1.0
 * Description : 
 * 
 * 
 * 
 * History :
 * 1. wuyj add for the first release , 2015年6月29日 
 *
 * 
 * Copyright (C), 2012-2013, Xi'an TCL Software Development Co.,Ltd
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.Sdk.Interface;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import com.leixun.smartcushion.Sdk.bean.CushionBean;

/**
 * @description
 * @author wuyj
 * @date 2015年6月29日 下午3:58:23
 */
public class CushionInterface {

	
	public interface BleDeviceGattListener{
		public void onConnect(BluetoothGatt gatt);
		public void onDisconnect(BluetoothGatt gatt);
		public void onServiceDiscovered(BluetoothGatt gatt);
		public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status);
		public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status);
		public void onCharacteristicChanged(byte[] data);
		public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic);	
	}
	
	public interface DeviceChangeListener{
	    public void CushionBeanAdded(CushionBean cushionBean );
	    public void CushionBeanRemoved(CushionBean cushionBean);
	    public void onResetBtSwitch();
	}
	
	public interface BleDeviceDiscoverListener{
		public void onDeviceDiscovered(BluetoothDevice device);
	}
		
	
}

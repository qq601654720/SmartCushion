/**   
 * @Title:Cushion.java
 * @Package com.leixun.smartcushion
 * @Description: 
 * @author 姚海军  
 * @date 2016年11月17日下午3:04:16
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年11月17日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.Sdk.bean;

import com.leixun.smartcushion.Sdk.BleConnector;
import com.leixun.smartcushion.Sdk.Connector;
import com.leixun.smartcushion.Sdk.Ble.BlePrivateProtocolBuilder;

/**
 * @author 姚海军
 *
 */

public class CushionBean {
	
	public int BATTERY_LEVEL_1 =  0X01;
	public int BATTERY_LEVEL_2 =  0X02;
	public int BATTERY_LEVEL_3 =  0X03;
	public int BATTERY_LEVEL_4 =  0X04;
	public int BATTERY_LEVEL_5 =  0X05;
	
	
	private int battery = BATTERY_LEVEL_5;
	private DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
	private BleConnector mTclBleConnector;
	private Connector currectConnector = mTclBleConnector;

	/**
	 * @return the deviceInfoBean
	 */
	public DeviceInfoBean getDeviceInfoBean() {
		return deviceInfoBean;
	}

	/**
	 * @param deviceInfoBean
	 *            the deviceInfoBean to set
	 */
	public void setDeviceInfoBean(DeviceInfoBean deviceInfoBean) {
		this.deviceInfoBean = deviceInfoBean;
	}
	/**
	 * @return the battery
	 */
	public int getBattery() {
		return battery;
	}
	/**
	 * @param battery the battery to set
	 */
	public void setBattery(int battery) {
		this.battery = battery;
	}
	
	public void setTclBleConnector(BleConnector tclBleConnector) {
		mTclBleConnector = tclBleConnector;
	}

	public BleConnector getTclBleConnector() {
		return mTclBleConnector;
	}

	public void setCurrectConnector(Connector tclConnector) {

		currectConnector = tclConnector;

	}

	public Connector getCurrectConnector() {
		return currectConnector;
	}	
	
	
	public void SynchronousSystemTime(){
		if(currectConnector!=null){
			currectConnector.sendData(BlePrivateProtocolBuilder.buildSystemTimeData());
		}
	}
	
	public void IntoPostureLearning(UserBean bean){
		if(currectConnector!=null){
			currectConnector.sendData(BlePrivateProtocolBuilder.build_Posture_Learning(bean));
		}
	}
	
	public void GetHistoryWarming(UserBean bean){
		if(currectConnector!=null){
			currectConnector.sendData(BlePrivateProtocolBuilder.buildCurrectUsrIsExistData(bean));
		}
	}
	
	public void updateCurrectUserData(UserBean bean){
		if(currectConnector!=null){
			currectConnector.sendData(BlePrivateProtocolBuilder.buildCurrectUserData(bean));
		}
	}
	
	public void updateCurrectSettingData(UserBean bean){
		if(currectConnector!=null){
			currectConnector.sendData(BlePrivateProtocolBuilder.buildQueryDeviceStatusData(bean));
		}
	}
	
	public void OpenFanData(UserBean bean){
		if(currectConnector!=null){
			currectConnector.sendData(BlePrivateProtocolBuilder.buildOpenFanData(bean));
		}
	}
	
	
}

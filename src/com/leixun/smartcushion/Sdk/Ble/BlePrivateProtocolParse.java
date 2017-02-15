/******************************************************************************
X * File: BlePrivateProtocol.java
 * Author: wuyongjiang
 * Create Date : 2015年7月1日
 * JDK version used: <JDK1.6> 
 * Version : V1.0
 * Description : 
 * 
 * 
 * 
 * History :
 * 1. 姚海军 add for the first release , 2015年7月1日 
 *
 * 
 * Copyright (C), 2012-2013, Xi'an TCL Software Development Co.,Ltd
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.Sdk.Ble;

import java.text.SimpleDateFormat;

import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateCurrectData;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateCurrectUser;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateFanOpenStatus;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateHistoryData;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateLearnResult;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateLearnStatus;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateSystemTime;
import com.leixun.smartcushion.Sdk.bean.Alarm;
import com.leixun.smartcushion.Sdk.bean.ErrDataBean;
import com.leixun.smartcushion.Sdk.bean.LearnBean;
import com.leixun.smartcushion.Sdk.util.DataTypeUtils;
import com.leixun.smartcushion.Sdk.util.L;

/**
 * @description
 * @author 姚海军
 * @date 2015年7月1日 上午10:58:30
 */
public class BlePrivateProtocolParse {

	private final static String TAG = "BlePrivateProtocolParse";

	public static void bleResponseLackDataParse(byte[] data) {
		if (data == null) {
			L.e(TAG, "parseReponse data is null");
			return;
		}else{
			switch (data[0]& 0xff) {
			case 0x02:
				ParseSystemTimeData(data);
				break;
			case 0x03:
				ParseCurrectUserData(data);
				break;
			case 0xee:
				ParseErrHistoryData(data);
				break;
			case 0x05:
				ParseCurrextErrData(data);
				break;
			case 0x0f:
				ParseFanOpenData(data);
				break;
			case 0x99:
				ParseIntoLearnStatusData(data);
				break;
			case 0x88:
				ParseLearnResult(data);
				break;
			default:
				break;
			}
		}
		
	}
	
	/**
	 * 系统时间接收到回调
	 */
	public static void ParseSystemTimeData(byte[] data) {
		L.e("时间同步成功！");
		UpdateSystemTime callback = CushionBeanManager.getInstance().getmUpdateSystemTime();
		if(callback!=null){
			callback.UpdateSystemTimeSuccess();
		}
	}
	
	/**
	 * 设备端反馈是否存在此用户
	 */
	public static void ParseCurrectUserData(byte[] data) {
		UpdateCurrectUser callback = CushionBeanManager.getInstance().getmUpdateCurrectUser();
		if((data[1]&0xff)==0x00){
			L.e("此用户存在！");

			if(callback!=null){
				callback.currectUserExist();
			}

		}else if((data[1]&0xff)==0xff){
			L.e("此用户不存在！");
			if(callback!=null){
				callback.currectUserNotExist();
			}
		}
	}
	
	/**
	 * 设备端反馈错误历史数据
	 */
	public static void ParseErrHistoryData(byte[] data) {
		L.e("报警历史数据");
		int tootle = DataTypeUtils.bytesToInt(data, 5, 1);
		int currect = DataTypeUtils.bytesToInt(data, 5, 1);
		ErrDataBean errdata = new ErrDataBean();
		errdata.setErrTime(new SimpleDateFormat("HH:mm").format(DataTypeUtils.bytesToLong2(data, 7, 4)));
		errdata.setErrCount(DataTypeUtils.bytesToInt(data, 11,1));
		errdata.setErrType(DataTypeUtils.bytesToInt(data, 12,1));
		UpdateHistoryData callback = CushionBeanManager.getInstance().getmUpdateHistoryData();
		if(callback!=null){
			callback.UpdateHistoryDataInfo(errdata, tootle, currect);
		}

	}
	
	/**
	 * 设备端实时当前数据
	 */
	public static void ParseCurrextErrData(byte[] data) {
		L.e("当前报警数据！");
		int battery = 0;
		int temperature = 0;
		battery = DataTypeUtils.bytesToInt(data, 1,1);
		temperature = DataTypeUtils.bytesToInt(data, 3,1);
		Alarm alarm = new Alarm();
		alarm.setPosture(DataTypeUtils.bytesToInt(data, 2,1));
		alarm.setTemperature_control_alarm(DataTypeUtils.bytesToInt(data, 4,1));
		alarm.setSedentary_alarm(DataTypeUtils.bytesToInt(data, 5,1));
		UpdateCurrectData callback = CushionBeanManager.getInstance().getmUpdateCurrectData();
		if(callback!=null){
			callback.UpdateCurrectDataInfo(alarm, battery, temperature);
		}
	}
	
	/**
	 * 设备端反馈风扇打开结果
	 */
	public static void ParseFanOpenData(byte[] data) {
		L.e("风扇打开成功！");
		UpdateFanOpenStatus callback = CushionBeanManager.getInstance().getmUpdateFanOpenStatus();
		if(callback!=null){		
			if((data[1]&0xff)==0x00){
				L.e("风扇關閉成功！");
				callback.UpdateFanCloseSuccess();
			}else{
				L.e("风扇打开成功！");
				callback.UpdateFanOpenSuccess();	
			}
		}
	}
	
	/**
	 * 设备端反馈进入学习状态结果
	 */
	public static void ParseIntoLearnStatusData(byte[] data) {
		L.e("进入学习状态成功！");
		UpdateLearnStatus callback = CushionBeanManager.getInstance().getmUpdateLearnStatus();
		if(callback!=null){
			callback.UpdateIntoLearnSuccess();
		}
	}
	
	/**
	 * 设备端反馈进入学习結果
	 */
	public static void ParseLearnResult(byte[] data) {
		L.e("学习状态结果反馈！");
		LearnBean learnBean = new LearnBean();
		learnBean.setPosture(DataTypeUtils.bytesToInt(data, 1,1));
		learnBean.setPosture_status(DataTypeUtils.bytesToInt(data, 2,1));
		UpdateLearnResult callback = CushionBeanManager.getInstance().getmUpdateLearnResult();
		if(callback!=null){
			callback.UpdateLearnResultSuccess(learnBean);
		}
	}
	
	
	
}

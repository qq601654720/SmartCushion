/**   
 * @Title:TclConnector.java
 * @Package com.huawei.smallRadar.Sdk
 * @Description: 
 * @author 姚海军  
 * @date 2016年2月29日下午3:39:54
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年2月29日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.Sdk;

import com.leixun.smartcushion.Sdk.Interface.ReceiveInterface;


/**
 * @author 姚海军
 *
 */
public abstract class Connector implements ReceiveInterface {

	public static int remote_id_length = 16;
	public static int name_length = 60;

	public static final int SEND_BLE_DATA_OFFSET = 0;
//	/**
//	 * app发送给设备的BLE的长数据
//	 */
//	public static final int SEND_LONG_DATA_APP_TO_DEV = 0X81;
//	/**
//	 * DEV发送给app的BLE短数据
//	 */
//	public static final int SEND_LACK_DATA_APP_TO_DEV = 0X83;
	/**
	 * DEV发送给APP的BLE短数据
	 */
	public static final int SEND_LONG_DATA_DEV_TO_APP = 0X01;
	/**
	 * app发送给设备的BLE短数据
	 */
	public static final int SEND_LACK_DATA_DEV_TO_APP = 0X03;

	public static final int BLE_DATA_TYPE_OFFSET = 2;
	public static final int BLE_DATA_SUB_TYPE_OFFSET = 3;
	public static final int BLE_DATA_SUB_TYPE_CONTENT_OFFSET = 4;

	// public final static int BLE_RESPONSE_TYPE_ACK = 0xaa;// ACK
	// public final static int BLE_RESPONSE_TYPE_NACK = 0xbb;// NACK
	/**
	 * 传感器相关数据类型
	 * 
	 * <pre>
	 * <i>0xA*表示Dev到App的数据</i>
	 * <i>0x0*表示App到Dev的数据</i>
	 * </pre>
	 */
	public static final int BLE_DATA_TYPE_SENSOR = 0X01;

	/**
	 * 完整传感器即时数据查询 APP->DEV
	 */
	public static final int BLE_DATA_SUB_TYPE_SENSOR_TVOC_CURRECT_DATA_REQUEST = 0X01;
	/**
	 * 空气检测类传感器即时数据查询 APP->DEV
	 */
	public static final int BLE_DATA_SUB_TYPE_SENSOR_AIR_DETECTION_CURRECT_DATA_REQUEST = 0X02;
	/**
	 * 电量传感器即时数据查询 APP->DEV
	 */
	public static final int BLE_DATA_SUB_TYPE_SENSOR_BATTERY_CURRECT_DATA_REQUEST = 0X03;
	
	/**
	 * 酒精传感器即时数据查询 APP->DEV
	 */
	public static final int BLE_DATA_SUB_TYPE_SENSOR_ALOCHO_DATA_REQUEST = 0X04;
	/**
	 * 退出酒精监测 APP->DEV
	 */
	public static final int BLE_DATA_SUB_TYPE_SENSOR_ALOCHO_DATA_QUIT = 0X05;
	
	/**
	 *PM2.5传感器即时数据查询 APP->DEV
	 */
	public static final int BLE_DATA_SUB_TYPE_SENSOR_PM25_CURRECT_DATA_REQUEST = 0X06;

	/**
	 * 空气检测类传感器即时数据反馈 DEV--->App
	 */
	public static final int BLE_DATA_SUB_TYPE_SENSOR_FULL_CURRECT_DATA_RESPONSE = 0XA1;
	/**
	 * 空气检测类传感器即时数据反馈 DEV--->App
	 */
	public static final int BLE_DATA_SUB_TYPE_SENSOR_AIR_DETECTION_CURRECT_DATA_RESPONSE = 0XA2;
	/**
	 * 电量传感器即时数据反馈 DEV--->App
	 */
	public static final int BLE_DATA_SUB_TYPE_SENSOR_BATTERY_CURRECT_DATA_RESPONSE = 0XA3;
	/**
	 * 酒精传感器即时数据反馈 DEV--->App
	 */
	public static final int BLE_DATA_SUB_TYPE_SENSOR_ALOCHO_DATA_RESPONSE = 0XA4;

	/**
	 * 闹钟数据类型
	 * 
	 * <pre>
	 * <i>0xA*表示Dev到App的数据</i>
	 * <i>0x0*表示App到Dev的数据</i>
	 * </pre>
	 */
	public static final int BLE_DATA_TYPE_ALARM = 0X02;
	/**
	 * 查询已经添加的闹钟列表
	 */
	public static final int BLE_DATA_SUB_TYPE_ALARM_LIST_REQUEST = 0X01;
	/**
	 * 添加或修改一个闹钟
	 */
	public static final int BLE_DATA_SUB_TYPE_ALARM_EDIT = 0X02;
	/**
	 * 删除一个现有的闹钟
	 */
	public static final int BLE_DATA_SUB_TYPE_ALARM_DELETE = 0X03;

	/**
	 * 删除一个现有的闹钟
	 */
	public static final int BLE_DATA_SUB_TYPE_ALARM_DELETE_RESPONSE = 0XA1;

	/**
	 * SETTING数据类型
	 * 
	 * <pre>
	 * <i>0xA*表示Dev到App的数据</i>
	 * <i>0x0*表示App到Dev的数据</i>
	 * </pre>
	 */
	public static final int BLE_DATA_TYPE_SETTING = 0X03;
	/**
	 * 查询LED当前亮度
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_DEV_LED_BRIGHTNESS_REQUEST = 0X01;
	/**
	 * 设置LED当前亮度
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_DEV_LED_BRIGHTNESS_SETTING = 0X02;
	/**
	 * 查询DEV软件版本
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_DEV_VERSION_RESPONSE = 0X03;

	/**
	 * 查询DEV软件版本
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_DEV_RENAME = 0X04;

	/**
	 * 同步DEV时间
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_UPDATE_DEV_TIME = 0X05;

	/**
	 * 设备连接网络
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_CONNECTION_NETWORK = 0X07;
	/**
	 * 设备WIFI 升级
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_WIFI_UPGRADE = 0X06;

	/**
	 * 设备reset
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_FACTORRY_RESET = 0X08;
	
	/**
	 * 同步DEV时间
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_UPDATE_DEV_TIME_AND_MOBILE_TYPE = 0X09;

	/**
	 * 查询LED当前亮度
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_DEV_LED_BRIGHTNESS_RESPONSE = 0XA1;
	/**
	 * 查询DEV软件版本
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_DEV_VERSION_REQUEST = 0XA2;
	/**
	 * 升级结果反馈
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_WIFI_UPGRADE_REQUEST = 0XA3;
	/**
	 * 联网配置结果反馈
	 */
	public static final int BLE_DATA_SUB_TYPE_SETTING_WIFI_CONNECTION_REQUEST = 0XA4;

	/**
	 * 定时控制数据类型
	 * 
	 * <pre>
	 * <i>0xA*表示Dev到App的数据</i>
	 * <i>0x0*表示App到Dev的数据</i>
	 * </pre>
	 */
	public static final int BLE_DATA_TYPE_TIMER_CONTROL = 0X04;
	/**
	 * 添加或者编辑一个定时控制
	 */
	public static final int BLE_DATA_SUB_TYPE_TIMER_CONTROL_EDIT_TIMER_CONTROL = 0X01;
	/**
	 * 删除一个定时控制
	 */
	public static final int BLE_DATA_SUB_TYPE_TIMER_CONTROL_DEL_TIMER_CONTROL = 0X02;
	/**
	 * 查询定时控制
	 */
	public static final int BLE_DATA_SUB_TYPE_TIMER_CONTROL_QUERY_TIMER_CONTROL = 0X03;

	@Deprecated
	/**
	 * 返回定时控制列表
	 */
	public static final int BLE_DATA_SUB_TYPE_OLD_TIMER_CONTROL_TIMER_CONTROL_RESPONSE = 0XA1;
	/**
	 * 返回定时控制列表
	 */
	public static final int BLE_DATA_SUB_TYPE_TIMER_CONTROL_TIMER_CONTROL_RESPONSE = 0XA2;
	/**
	 * 
	 * 智能控制数据类型
	 * 
	 * <pre>
	 * <i>0xA*表示Dev到App的数据</i>
	 * <i>0x0*表示App到Dev的数据</i>
	 * </pre>
	 */
	public static final int BLE_DATA_TYPE_SMART_CONTROL = 0X05;
	/**
	 * 添加或者编辑一个定时控制
	 */
	public static final int BLE_DATA_SUB_TYPE_SMART_CONTROL_EDIT_SMART_CONTROL = 0X01;
	/**
	 * 删除一个定时控制
	 */
	public static final int BLE_DATA_SUB_TYPE_SMART_CONTROL_DEL_SMART_CONTROL = 0X02;
	/**
	 * 查询定时控制
	 */
	public static final int BLE_DATA_SUB_TYPE_SMART_CONTROL_QUERY_SMART_CONTROL = 0X03;
	/**
	 * 返回定时控制列表(已过时)
	 */
	public static final int BLE_DATA_SUB_TYPE_OLD_SMART_CONTROL_SMART_CONTROL_RESPONSE = 0XA1;
	/**
	 * 返回定时控制列表
	 */
	public static final int BLE_DATA_SUB_TYPE_SMART_CONTROL_SMART_CONTROL_RESPONSE = 0XA2;
	/**
	 * /** IR数据类型
	 * 
	 * <pre>
	 * <i>0xA*表示Dev到App的数据</i>
	 * <i>0x0*表示App到Dev的数据</i>
	 * </pre>
	 */
	public static final int BLE_DATA_TYPE_IR = 0X06;
	/**
	 * 非空调遥控码值
	 */
	public static final int BLE_DATA_SUB_TYPE_IR_NOT_AIR_REMOTE = 0X01;
	/**
	 * 空调遥控vm code
	 */
	public static final int BLE_DATA_SUB_TYPE_IR_AIR_REMOTE_VM_CODE = 0X02;

	/**
	 * 空调遥控BUF
	 */
	public static final int BLE_DATA_SUB_TYPE_IR_AIR_REMOTE_STATUS_BUFFER = 0X03;
	/**
	 * 查询遥控器列表
	 */
	public static final int BLE_DATA_SUB_TYPE_IR_REMOTE_GET_LIST = 0X04;
	/**
	 * 删除遥控器
	 */
	public static final int BLE_DATA_SUB_TYPE_IR_REMOTE_DELETE = 0X05;
	
	/**
	 * 自动匹配空调遥控 vm code
	 */
	public static final int BLE_DATA_SUB_TYPE_IR_AUTO_MATCH_AIR_REMOTE_VM_CODE = 0X06;

	/**
	 * 回复遥控器列表
	 */
	public static final int BLE_DATA_SUB_TYPE_IR_REMOTE_RESPONSE_LIST = 0XA1;

	/**
	 * 
	 * 历史数据类型
	 * 
	 * <pre>
	 * <i>0xA*表示Dev到App的数据</i>
	 * <i>0x0*表示App到Dev的数据</i>
	 * </pre>
	 */
	public static final int BLE_DATA_TYPE_HISTORY_DATA = 0X07;
	/**
	 * 获取当天历史数据
	 */

	public static final int BLE_DATA_SUB_TYPE_GET_CURRECT_DATA_HISTORY_DATA = 0X01;
	/**
	 * 获取周历史数据
	 */
	public static final int BLE_DATA_SUB_TYPE_GET_CURRECT_WEEK_HISTORY_DATA = 0X02;

	/**
	 * 回复当天历史数据
	 */

	public static final int BLE_DATA_SUB_TYPE_CURRECT_DATA_HISTORY_DATA_RESPONSE = 0XA1;
	/**
	 * 回复周历史数据
	 */
	public static final int BLE_DATA_SUB_TYPE_CURRECT_WEEK_HISTORY_DATA_RESPONSE = 0XA2;



	/**
	 * 
	 * 升级类型
	 * 
	 * <pre>
	 * <i>0xA*表示Dev到App的数据</i>
	 * <i>0x0*表示App到Dev的数据</i>
	 * </pre>
	 */
	public static final int BLE_DATA_TYPE_UPGRADE = 0X08;
	/**
	 * 获取设备信息
	 */

	public static final int BLE_DATA_SUB_TYPE_UPGRADE_GET_DEVICE_VERSION = 0X01;
	/**
	 * 发送wifi升级URL
	 */
	public static final int BLE_DATA_SUB_TYPE_UPGRADE_SEND_WIFI_URL = 0X02;
	/**
	 * 取消升级
	 */
	public static final int BLE_DATA_SUB_TYPE_UPGRADE_CANCEL_UPGRADE = 0X03;

	/**
	 * 回复设备版本信息（过时）
	 */
	public static final int BLE_DATA_SUB_TYPE_RESPONSE_DEVICE_VERSION = 0XA1;

	/**
	 * 回复wifi升级进度
	 */
	public static final int BLE_DATA_SUB_TYPE_RESPONSE_UPGRADE_PROGRESS = 0XA2;
	/**
	 * 回复wifi升级结果
	 */
	public static final int BLE_DATA_SUB_TYPE_RESPONSE_UPGRADE_STATES = 0XA3;
	
	/**
	 * 回复设备版本信息（新）
	 */
	public static final int BLE_DATA_SUB_TYPE_RESPONSE_DEVICE_NEW_VERSION = 0XA4;
	
	/**
	 * 回复设备MAC地址
	 */
	public static final int BLE_DATA_SUB_TYPE_RESPONSE_DEVICE_VERSION_AND_MAC = 0XA5;

	/**
	 * /** 距离传感器数
	 * 
	 * <pre>
	 * <i>0xA*表示Dev到App的数据</i>
	 * <i>0x0*表示App到Dev的数据</i>
	 * </pre>
	 */
	public static final int BLE_DATA_TYPE_GESTURE = 0X09;
	public static final int BLE_DATA_SUB_TYPE_GESTURE_GET_BRIGHT_TIME = 0X01;
	public static final int BLE_DATA_SUB_TYPE_GESTURE_SET_BRIGHT_TIME = 0X02;
	public static final int BLE_DATA_SUB_TYPE_GESTURE_CONTROL_GET_REMOTE_ID = 0X03;
	public static final int BLE_DATA_SUB_TYPE_GESTURE_CONTROL_SET_REMOTE_ID = 0X04;
	public static final int BLE_DATA_SUB_TYPE_GOON_GESTURE_CONTROL_MODE = 0X05;
	public static final int BLE_DATA_SUB_TYPE_EXIT_GESTURE_CONTROL_MODE = 0X06;

	public static final int BLE_DATA_SUB_TYPE_GESTURE_RESPONSE_BRIGHT_TIME = 0XA1;
	public static final int BLE_DATA_SUB_TYPE_GESTURE_RESPONSE_REMOTE_ID = 0XA2;

	public static final int STATE_DISCONNECTED = 0;
	public static final int STATE_CONNECTED = 2;
	
	
	public static final int BLE_DATA_TYPE_FACTORY_TEST = 0X99;
	public static final int BLE_DATA_SUB_TYPE_FACTORY_TEST_RESPONSE = 0XA1;
	
	
	/**
	 * 小雷达附件协议
	 * 
	 * <pre>
	 * <i>0xA*表示Dev到App的数据</i>
	 * <i>0x0*表示App到Dev的数据</i>
	 * </pre>
	 */
	public static final int BLE_DATA_TYPE_ACCESSORIES= 0X10;
	public static final int BLE_DATA_SUB_TYPE_ACCESSORIES_GET_CONNECTION_STATES= 0X01;
	public static final int BLE_DATA_SUB_TYPE_ACCESSORIES_CONNECT = 0X02;
	
	public static final int BLE_DATA_SUB_TYPE_ACCESSORIES_RESPONSE_CONNECTION_STATES = 0XA1;

	/**
	 * dev发送给APP的短数据解析
	 * 
	 * @param data
	 * 
	 */

	public int connectionStatus = STATE_DISCONNECTED;

	public ConnectorType mConnectorType;

	/**
	 * @author 姚海军 设备连接类型枚举
	 * 
	 *         <pre>
	 * <i>蓝牙连接</i>
	 * <i>Tcp连接</i>
	 * </pre>
	 */
	public enum ConnectorType {
		TclBleConnector, TclTcpConnector,
	}

	/**
	 * 初始化连接类型
	 * 
	 * @param tclconnector
	 *            <pre>
	 * <i>TclBleConnector 蓝牙连接</i>
	 * <i>TclTcpConnector Tcp连接</i>
	 * </pre>
	 */
	public Connector(ConnectorType tclconnector) {
		// TODO Auto-generated method stub
		this.mConnectorType = tclconnector;
	}

	/**
	 * 
	 */
	public ConnectorType getTclConnector() {
		// TODO Auto-generated method stub
		return mConnectorType;
	}

	/**
	 * 发送数据公共接口
	 * 
	 * @param data
	 *            byte[] 数组
	 */
	public abstract void sendData(byte[] data);

	/**
	 * 设备连接到控制环境
	 * 
	 * @param address
	 *            设备连接所需传递的参数
	 * 
	 *            <pre>
	 * <i>蓝牙传递Mac地址</i>
	 * <i>Wifi传递SSID和密码</i>
	 * </pre>
	 */
	public abstract void connect(String... address);

	/**
	 * 设备断开控制环境
	 */
	public abstract void disconnect();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huawei.smallRadar.Sdk.Interface.TclReceiveInterface#receiveData(byte
	 * [], int)
	 */
	@Override
	public void receiveData(byte[] data, int length) {
		// TODO Auto-generated method stub

	}

}

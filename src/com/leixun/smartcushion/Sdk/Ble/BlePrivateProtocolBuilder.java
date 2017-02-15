/******************************************************************************
 * File: BlePrivateProtocol.java
 * Author: 姚海军
 * Create Date : 2016年3月10日
 * JDK version used: <JDK1.6> 
 * Version : V1.0
 * Description : 
 * 
 * 
 * 
 * History :
 * 1. 姚海军 add for the first release ,2016年3月10日
 *
 * 
 ******************************************************************************/
package com.leixun.smartcushion.Sdk.Ble;

import com.leixun.smartcushion.Sdk.bean.UserBean;
import com.leixun.smartcushion.Sdk.util.DataTypeUtils;
import com.leixun.smartcushion.Sdk.util.L;


/**
 * @description 构造APP发送给DEV 的 BLE数据
 * @author 姚海军
 * @date 2016年3月10日 上午10:58:30
 */
public class BlePrivateProtocolBuilder {

	/**
	 * 初始化系统时间
	 */
	public static byte[] buildSystemTimeData() {
		long time = System.currentTimeMillis() / 1000;
		byte[] bytes = new byte[6];
		bytes[0] = (byte) 0x02;
		bytes[1] = DataTypeUtils.longToBytes2(time,4)[0];
		bytes[2] = DataTypeUtils.longToBytes2(time,4)[1];
		bytes[3] = DataTypeUtils.longToBytes2(time,4)[2];
		bytes[4] = DataTypeUtils.longToBytes2(time,4)[3];
		bytes[5] = 0x0a;
		// CRC16 crc16 = new CRC16();
		L.v("buildSystemTime ===========" + DataTypeUtils.bytesToHexString(bytes));
		// bytes[4] = (byte) (crc16.encode(bytes) & 0xff);
		return bytes;
	}
	
	/**
	 * App发送当前用户给硬件
	 */
	public static byte[] buildCurrectUserData(UserBean bean) {
		byte[] bytes = new byte[14];
		bytes[0] = (byte) 0x03;
		bytes[1] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[0];
		bytes[2] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[1];
		bytes[3] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[2];
		bytes[4] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[3];
		bytes[5] = DataTypeUtils.Int2Byte(bean.getUserStature());
		bytes[6] = DataTypeUtils.Int2Byte(bean.getUserWeight());
		bytes[7] = DataTypeUtils.Int2Byte(bean.getState());
		bytes[8] = DataTypeUtils.Int2Byte(bean.getPosture_check_switch());
		bytes[9] = DataTypeUtils.Int2Byte(bean.getCheck_precise());
		bytes[10] = DataTypeUtils.Int2Byte(bean.getTemperature_country_state());
		bytes[11] = DataTypeUtils.Int2Byte(bean.getTemperature_country_value());
		bytes[12] = DataTypeUtils.Int2Byte(bean.getSedentary_reminder());
		bytes[13] = 0x0a;
		// CRC16 crc16 = new CRC16();
		L.v("buildCurrectUserData ===========" + DataTypeUtils.bytesToHexString(bytes));
		// bytes[4] = (byte) (crc16.encode(bytes) & 0xff);
		return bytes;
	}
	
	/**
	 * 用户存在像设备发送
	 */
	public static byte[] buildCurrectUsrIsExistData(UserBean bean) {
		byte[] bytes = new byte[6];
		bytes[0] = (byte) 0xdb;
		bytes[1] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[0];
		bytes[2] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[1];
		bytes[3] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[2];
		bytes[4] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[3];
		bytes[5] = 0x0a;
		// CRC16 crc16 = new CRC16();
		L.v("buildCurrectUsrIsExistData ===========" + DataTypeUtils.bytesToHexString(bytes));
		// bytes[4] = (byte) (crc16.encode(bytes) & 0xff);
		return bytes;
	}
	
	/**
	 * APP主动查询电量与温度等参数
	 */
	public static byte[] buildQueryDeviceStatusData(UserBean bean) {
		byte[] bytes = new byte[6];
		bytes[0] = (byte) 0x05;
		bytes[1] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[0];
		bytes[2] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[1];
		bytes[3] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[2];
		bytes[4] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[3];
		bytes[5] = 0x0a;
		// CRC16 crc16 = new CRC16();
		L.v("buildQueryDeviceStatusData ===========" + DataTypeUtils.bytesToHexString(bytes));
		// bytes[4] = (byte) (crc16.encode(bytes) & 0xff);
		return bytes;
	}
	
	/**
	 * 进入坐姿学习状态
	 */
	public static byte[] build_Posture_Learning(UserBean bean) {
		byte[] bytes = new byte[14];
		bytes[0] = (byte) 0x99;
		bytes[1] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[0];
		bytes[2] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[1];
		bytes[3] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[2];
		bytes[4] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[3];
		bytes[5] = DataTypeUtils.Int2Byte(bean.getUserStature());
		bytes[6] = DataTypeUtils.Int2Byte(bean.getUserWeight());
		bytes[7] = DataTypeUtils.Int2Byte(bean.getState());
		bytes[8] = DataTypeUtils.Int2Byte(bean.getPosture_check_switch());
		bytes[9] = DataTypeUtils.Int2Byte(bean.getCheck_precise());
		bytes[10] = DataTypeUtils.Int2Byte(bean.getTemperature_country_state());
		bytes[11] = DataTypeUtils.Int2Byte(bean.getTemperature_country_value());
		bytes[12] = DataTypeUtils.Int2Byte(bean.getSedentary_reminder());
		bytes[13] = 0x0a;
		// CRC16 crc16 = new CRC16();
		L.v("build_Posture_Learning	 ===========" + DataTypeUtils.bytesToHexString(bytes));
		// bytes[4] = (byte) (crc16.encode(bytes) & 0xff);
		return bytes;
	}
	
	/**
	 * APP主动打开风扇
	 */
	public static byte[] buildOpenFanData(UserBean bean) {
		byte[] bytes = new byte[7];
		bytes[0] = (byte) 0x05;
		bytes[1] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[0];
		bytes[2] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[1];
		bytes[3] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[2];
		bytes[4] = DataTypeUtils.longToBytes2(Long.parseLong(bean.getUserId()),4)[3];
		bytes[5] = DataTypeUtils.Int2Byte(bean.getFan_status());
		bytes[6] = 0x0a;
		// CRC16 crc16 = new CRC16();
		L.v("buildOpenFanData ===========" + DataTypeUtils.bytesToHexString(bytes));
		// bytes[4] = (byte) (crc16.encode(bytes) & 0xff);
		return bytes;
	}
	
	/**
	 * APP主动打开Debug 模式
	 */
	public static byte[] buildOpenDebugModeData(UserBean bean) {
		byte[] bytes = new byte[3];
		bytes[0] = (byte) 0xd8;
		bytes[1] = (byte) 0xd8;
		bytes[2] = 0x0a;
		// CRC16 crc16 = new CRC16();
		L.v("buildOpenDebugModeData ===========" + DataTypeUtils.bytesToHexString(bytes));
		// bytes[4] = (byte) (crc16.encode(bytes) & 0xff);
		return bytes;
	}
	
	/**
	 * APP主动关闭Debug 模式
	 */
	public static byte[] buildCloseDebugModeData(UserBean bean) {
		byte[] bytes = new byte[3];
		bytes[0] = (byte) 0xd8;
		bytes[1] = (byte) 0x00;
		bytes[2] = 0x0a;
		// CRC16 crc16 = new CRC16();
		L.v("buildCloseDebugModeData ===========" + DataTypeUtils.bytesToHexString(bytes));
		// bytes[4] = (byte) (crc16.encode(bytes) & 0xff);
		return bytes;
	}

}

/**   
 * @Title:TclReceiveInterface.java
 * @Package com.huawei.smallRadar.Sdk.Interface
 * @Description: 
 * @author 姚海军  
 * @date 2016年2月29日下午9:25:58
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年2月29日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.Sdk.Interface;

/**
 * @author 姚海军
 *
 */
public interface ReceiveInterface {

	/**
	 * 接收到设备发过来的数据
	 * @param data byte[] 数组
	 * @param length byte[] 数组长度
	 */
	public  void receiveData(byte[] data,int length);
}

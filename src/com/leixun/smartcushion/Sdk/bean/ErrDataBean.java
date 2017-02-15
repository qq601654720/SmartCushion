/**   
 * @Title:ErrDataBean.java
 * @Package com.leixun.smartcushion
 * @Description: 
 * @author 姚海军  
 * @date 2017年1月5日下午4:15:37
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2017年1月5日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.Sdk.bean;

/**
 * @author 姚海军
 *
 */
public class ErrDataBean {
	public int ERR_TYPE_LHT = 0X01; //左斜
	public int ERR_TYPE_RHT = 0X02; //右斜
	public int ERR_TYPE_FOHT = 0X04; //后方
	public int ERR_TYPE_AFHT = 0X03; //前方
	public int ERR_TYPE_OTHER = 0X05; //其他
 	
	private String errTime = "";
	private long errCount = 0;
	private long errType = ERR_TYPE_LHT;
	/**
	 * @return the errTime
	 */
	public String getErrTime() {
		return errTime;
	}
	/**
	 * @param errTime the errTime to set
	 */
	public void setErrTime(String errTime) {
		this.errTime = errTime;
	}
	/**
	 * @return the errCount
	 */
	public long getErrCount() {
		return errCount;
	}
	/**
	 * @param errCount the errCount to set
	 */
	public void setErrCount(long errCount) {
		this.errCount = errCount;
	}
	/**
	 * @return the errType
	 */
	public long getErrType() {
		return errType;
	}
	/**
	 * @param errType the errType to set
	 */
	public void setErrType(long errType) {
		this.errType = errType;
	}
	
	
}

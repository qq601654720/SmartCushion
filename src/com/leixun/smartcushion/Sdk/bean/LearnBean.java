/**   
 * @Title:LearnBean.java
 * @Package com.leixun.smartcushion
 * @Description: 
 * @author 姚海军  
 * @date 2017年1月6日下午3:19:11
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2017年1月6日  
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
public class LearnBean {
	public static final int POSTURE_STATUS_LHT = 0X01; //左斜
	public static final int POSTURE_STATUS_RHT = 0X02; //右斜
	public static final int POSTURE_STATUS_FOHT = 0X04; //后方
	public static final int POSTURE_STATUS_AFHT = 0X03; //前方
	public static final int POSTURE_STATUS_OTHER = 0X05; //其他
	
	public static final int POSTURE_NO_PERSON = 0X00;
	public static final int POSTURE_RIGHT = 0X01;
	public static final int POSTURE_WRONG = 0X02;
 	
	private int posture_status = POSTURE_STATUS_OTHER;	
	private int posture = POSTURE_NO_PERSON;
	/**
	 * @return the posture_status
	 */
	public int getPosture_status() {
		return posture_status;
	}
	/**
	 * @param posture_status the posture_status to set
	 */
	public void setPosture_status(int posture_status) {
		this.posture_status = posture_status;
	}
	/**
	 * @return the posture
	 */
	public int getPosture() {
		return posture;
	}
	/**
	 * @param posture the posture to set
	 */
	public void setPosture(int posture) {
		this.posture = posture;
	}
	
	
}

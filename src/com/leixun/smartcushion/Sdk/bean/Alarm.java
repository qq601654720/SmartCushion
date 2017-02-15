/**   
 * @Title:Alarm.java
 * @Package com.leixun.smartcushion
 * @Description: 
 * @author 姚海军  
 * @date 2017年1月5日下午4:47:28
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
public class Alarm {
	public int POSTURE_NO_PERSON = 0X00;
	public int POSTURE_RIGHT = 0X01;
	public static int POSTURE_WRONG = 0X02;
	
	public static int TEMPERATURE_ALARM = 0X01;
	public int TEMPERATURE_NO_ALARM = 0X00;

	public static int SEDENTARY_ALARM = 0X01;
	public int SEDENTARY_NO_ALARM = 0X00;
	
	private int posture = POSTURE_NO_PERSON;
	private int temperature_control_alarm = TEMPERATURE_NO_ALARM;
	private int sedentary_alarm = SEDENTARY_NO_ALARM;
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


	/**
	 * @return the temperature_control_alarm
	 */
	public int getTemperature_control_alarm() {
		return temperature_control_alarm;
	}

	/**
	 * @param temperature_control_alarm the temperature_control_alarm to set
	 */
	public void setTemperature_control_alarm(int temperature_control_alarm) {
		this.temperature_control_alarm = temperature_control_alarm;
	}

	/**
	 * @return the sedentary_alarm
	 */
	public int getSedentary_alarm() {
		return sedentary_alarm;
	}

	/**
	 * @param sedentary_alarm the sedentary_alarm to set
	 */
	public void setSedentary_alarm(int sedentary_alarm) {
		this.sedentary_alarm = sedentary_alarm;
	}
}

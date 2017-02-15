/**   
 * @Title:UserBean.java
 * @Package com.leixun.smartcushion
 * @Description: 
 * @author 姚海军  
 * @date 2016年11月16日上午11:13:30
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年11月16日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.Sdk.bean;

import java.io.Serializable;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

/**
 * @author 姚海军
 *
 */
@Table(name = "UserBean")
public class UserBean implements Serializable{
	// ID @Id主键,int类型,数据库建表时此字段会设为自增长
	@Id
	@Column(name = "_id")
	private int _id;
	@Column(name = "userId")
	private String userId = "";
	@Column(name = "userName")
	private String userName = "Defult";
	@Column(name = "userSex")
	private String userSex = "女";
	@Column(name = "userAge", type = "INTEGER")
	private int userAge = 0;
	@Column(name = "userWeight", type = "INTEGER")
	private int userWeight = 0;
	@Column(name = "userStature", type = "INTEGER")
	private int userStature = 0;
	
	public  final static int STATE_NORMAL= 0x01 ;//设备状态 标准状态
	public  final static int STATE_VEHICLE= 0x03 ;//设备状态 车载状态
	public  final static int STATE_MUTE= 0x02 ;//设备状态 静音状态
	
	public static final int CHECK_PRECISE_NORMAL= 0x03 ;//检查精度 标准状态
	public static final int CHECK_PRECISE_HIGHT_LEVEL_1= 0x04 ;//检查精度 高一等状态 ;
	public static final int CHECK_PRECISE_HIGHT_LEVEL_2= 0x05 ;//检查精度 高二等状态 ;
	public static final int CHECK_PRECISE_LOW_LEVEL_1= 0x01 ;//检查精度 低一等状态 ;
	public static final int CHECK_PRECISE_LOW_LEVEL_2= 0x02 ;//检查精度 低二等状态 ;
	
	public static final int TEMPERATURE_TYPE_AUTO= 0x01 ;//温控方式 自动模式
	public static final int TEMPERATURE_TYPE_MATULE= 0x00 ;//温控方式 手动模式
	
	public static final int POSTURE_CHECK_ON= 0x11 ;//坐姿检测开关开
	public static final int POSTURE_CHECK_OFF= 0x00 ;//坐姿检测开关关闭
	public static final int SEDENTARY_REMINBER_ON= 0 ;//久坐提醒开关开
	public static final int SEDENTARY_REMINBER_OFF= 1 ;//久坐提醒开关关闭

	
	@Column(name = "state")//设备状态
	private int state = STATE_NORMAL;
	@Column(name = "posture_check_switch")
	private int posture_check_switch = POSTURE_CHECK_ON; //坐姿检测开关
	@Column(name = "check_precise")
	private int check_precise = CHECK_PRECISE_NORMAL; //检查精度
	@Column(name = "temperature_country_state")
	private int temperature_country_state = TEMPERATURE_TYPE_AUTO; //温控方式
	@Column(name = "temperature_country_value") 
	private int temperature_country_value = 20; //手动温控方式 温度
	@Column(name = "sedentary_reminder") 
	private int sedentary_reminder = SEDENTARY_REMINBER_ON;//久坐提醒
	
	public final static int FAN_STATUS_ON = 0X01;
	public final static int FAN_STATUS_OFF = 0X00;
	@Column(name = "fan_status") 
	private int fan_status = FAN_STATUS_OFF;
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the userSex
	 */
	public String getUserSex() {
		return userSex;
	}
	/**
	 * @param userSex the userSex to set
	 */
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	/**
	 * @return the userAge
	 */
	public int getUserAge() {
		return userAge;
	}
	/**
	 * @param userAge the userAge to set
	 */
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	/**
	 * @return the userWeight
	 */
	public int getUserWeight() {
		return userWeight;
	}
	/**
	 * @param userWeight the userWeight to set
	 */
	public void setUserWeight(int userWeight) {
		this.userWeight = userWeight;
	}
	/**
	 * @return the userStature
	 */
	public int getUserStature() {
		return userStature;
	}
	/**
	 * @param userStature the userStature to set
	 */
	public void setUserStature(int userStature) {
		this.userStature = userStature;
	}
	
	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	/**
	 * @return the posture_check
	 */
	public int getPosture_check_switch() {
		return posture_check_switch;
	}
	/**
	 * @param posture_check the posture_check to set
	 */
	public void setPosture_check_switch(int posture_check_switch) {
		this.posture_check_switch = posture_check_switch;
	}
	/**
	 * @return the check_precise
	 */
	public int getCheck_precise() {
		return check_precise;
	}
	/**
	 * @param check_precise the check_precise to set
	 */
	public void setCheck_precise(int check_precise) {
		this.check_precise = check_precise;
	}
	/**
	 * @return the temperature_country_state
	 */
	public int getTemperature_country_state() {
		return temperature_country_state;
	}
	/**
	 * @param temperature_country_state the temperature_country_state to set
	 */
	public void setTemperature_country_state(int temperature_country_state) {
		this.temperature_country_state = temperature_country_state;
	}
	/**
	 * @return the temperature_countryvalue
	 */
	public int getTemperature_country_value() {
		return temperature_country_value;
	}
	/**
	 * @param temperature_countryvalue the temperature_countryvalue to set
	 */
	public void setTemperature_country_value(int temperature_countryvalue) {
		this.temperature_country_value = temperature_countryvalue;
	}
	/**
	 * @return the sedentary_reminder
	 */
	public int getSedentary_reminder() {
		return sedentary_reminder;
	}
	/**
	 * @param sedentary_reminder the sedentary_reminder to set
	 */
	public void setSedentary_reminder(int sedentary_reminder) {
		this.sedentary_reminder = sedentary_reminder;
	}
	
	/**
	 * @return the fan_status
	 */
	public int getFan_status() {
		return fan_status;
	}

	/**
	 * @param fan_status the fan_status to set
	 */
	public void setFan_status(int fan_status) {
		this.fan_status = fan_status;
	}

}

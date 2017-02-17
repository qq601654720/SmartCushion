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

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

/**
 * @author 姚海军
 *
 */
@Table(name = "ErrDataBean")
public class ErrDataBean {
	public int ERR_TYPE_LHT = 0X01; //左斜
	public int ERR_TYPE_RHT = 0X02; //右斜
	public int ERR_TYPE_FOHT = 0X04; //后方
	public int ERR_TYPE_AFHT = 0X03; //前方
	public int ERR_TYPE_OTHER = 0X05; //其他
	// ID @Id主键,int类型,数据库建表时此字段会设为自增长
	@Id
	@Column(name = "_id")
	private int _id;
	@Column(name = "userId")
	private String userId = "111";	
	@Column(name = "errTime")
	private String errTime = "";
	@Column(name = "errCount", type = "INTEGER")
	private int errCount = 0;
	@Column(name = "errType", type = "INTEGER")
	private int errType = ERR_TYPE_LHT;
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
	public int getErrCount() {
		return errCount;
	}
	/**
	 * @param errCount the errCount to set
	 */
	public void setErrCount(int errCount) {
		this.errCount = errCount;
	}
	/**
	 * @return the errType
	 */
	public int getErrType() {
		return errType;
	}
	/**
	 * @param errType the errType to set
	 */
	public void setErrType(int errType) {
		this.errType = errType;
	}
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
	
	
	
	
}

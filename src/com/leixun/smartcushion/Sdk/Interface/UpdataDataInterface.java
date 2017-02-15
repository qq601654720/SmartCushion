/**   
 * @Title:UpdataDataInterface.java
 * @Package com.leixun.smartcushion.Sdk.Interface
 * @Description: 
 * @author 姚海军  
 * @date 2017年1月6日下午1:40:50
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2017年1月6日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.Sdk.Interface;

import com.leixun.smartcushion.Sdk.bean.Alarm;
import com.leixun.smartcushion.Sdk.bean.ErrDataBean;
import com.leixun.smartcushion.Sdk.bean.LearnBean;


/**
 * @author 姚海军
 *
 */
public class UpdataDataInterface {
	//同步系統時間成功
	public interface UpdateSystemTime{
		public void UpdateSystemTimeSuccess();
	}
	
	//查詢當前用戶
	public interface UpdateCurrectUser{
		public void currectUserExist();
		public void currectUserNotExist();
	}
	
	//同步歷史錯誤數據
	public interface UpdateHistoryData{
		public void UpdateHistoryDataInfo(ErrDataBean errDataBean,int tootleCount, int currectCount);
	}

	//同步試設備數據
	public interface UpdateCurrectData{
		public void UpdateCurrectDataInfo(Alarm alarm,int battery, int temperature);
	}
	
	//同步風扇打開結果
	public interface UpdateFanOpenStatus{
		public void UpdateFanOpenSuccess();
		public void UpdateFanCloseSuccess();
	}
	
	//同步進入學習狀態結果
	public interface UpdateLearnStatus{
		public void UpdateIntoLearnSuccess();
	}

	//同步學習坐姿結果
	public interface UpdateLearnResult{
		public void UpdateLearnResultSuccess(LearnBean learnBean);
	}
}

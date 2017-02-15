/******************************************************************************
 * File: BleDeviceManager.java
 * Author: wuyongjiang
 * Create Date : 2015年7月1日
 * JDK version used: <JDK1.6> 
 * Version : V1.0
 * Description : 
 * 
 * 
 * 
 * History :
 * 1. wuyj add for the first release , 2015年7月1日 
 *
 * 
 * Copyright (C), 2012-2013, Xi'an TCL Software Development Co.,Ltd
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.Sdk;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;

import com.leixun.smartcushion.Sdk.Ble.BluetoothLeService;
import com.leixun.smartcushion.Sdk.Interface.BleInterface.BtSwitchInterface;
import com.leixun.smartcushion.Sdk.Interface.CushionInterface.BleDeviceDiscoverListener;
import com.leixun.smartcushion.Sdk.Interface.CushionInterface.BleDeviceGattListener;
import com.leixun.smartcushion.Sdk.Interface.CushionInterface.DeviceChangeListener;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateCurrectData;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateCurrectUser;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateFanOpenStatus;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateHistoryData;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateLearnResult;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateLearnStatus;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateSystemTime;
import com.leixun.smartcushion.Sdk.bean.CushionBean;
import com.leixun.smartcushion.Sdk.bean.ErrDataBean;
import com.leixun.smartcushion.Sdk.bean.UserBean;

/**
 * @description
 * @author 姚海军
 * @date 2015年7月1日 下午3:41:09
 */
public class CushionBeanManager {

	private static CushionBeanManager mBleDevManager = null;

	private BluetoothLeService mBleService = null;

	private List<CushionBean> mCushionBeanList = new LinkedList<CushionBean>();
	private Object mCushionBeanListLock = new Object();
	
	public boolean RemoteIsSync = false;
	
	public int failcount = 0;
	public final static int MAX_FAILED_CONNECTION_NUM = 4;
	
	private UserBean mEditUserBean,mCurrectUserBean;
	
	private static Context mContext;
	
	private static List<UserBean> mbeans = new LinkedList<UserBean>(); 
	private List<ErrDataBean> errDatas = new LinkedList<ErrDataBean>();

	/**
	 * @return the mCurrectUserBean
	 */
	public UserBean getmEditUserBean() {
		return mEditUserBean;
	}

	/**
	 * @param mCurrectUserBean the mCurrectUserBean to set
	 */
	public void setmEditUserBean(UserBean mCurrectUserBean) {
		this.mEditUserBean = mCurrectUserBean;
	}
	
	/**
	 * @return the mCurrectUserBean
	 */
	public UserBean getmCurrectUserBean() {
		return mCurrectUserBean;
	}

	/**
	 * @param mCurrectUserBean the mCurrectUserBean to set
	 */
	public void setmCurrectUserBean(UserBean mCurrectUserBean) {
		this.mCurrectUserBean = mCurrectUserBean;
	}


	
	public static CushionBeanManager getInstance() {
		if (mBleDevManager == null) {
			synchronized (CushionBeanManager.class) {
				if (mBleDevManager == null) {
					mBleDevManager = new CushionBeanManager();
				}
			}
		}
		return mBleDevManager;
	}
	
	

	/**
	 * @return the beans
	 */
	public List<UserBean> getBeans() {
		return mbeans;
	}

	/**
	 * @param beans the beans to set
	 */
	public void setBeans(List<UserBean> beans) {
		mbeans = beans;
	}
	
	/**
	 * @param beans the beans to set
	 */
	public void AddBeanToBeans(UserBean bean) {
		mbeans.add(bean);
	}
	
	/**
	 * @param beans the beans to set
	 */
	public void RemoveBeanToBeans(UserBean bean) {
		mbeans.remove(bean);
	}
	
	



	/**
	 * @return the alarms
	 */
	public List<ErrDataBean> getErrDatas() {
		return errDatas;
	}

	/**
	 * @param alarms the alarms to set
	 */
	public void addErrDatas(int tootle,int count,ErrDataBean errDataBean) {
		if(count<tootle){
			this.errDatas.add(errDataBean);
		}
	}
	
	
	/**
	 * 
	 */
	public  void ResetCushion() {
		// TODO Auto-generated method stub
		errDatas.clear();
	}





	private BleDeviceGattListener mBleDevGattListener = null;
	private BleDeviceDiscoverListener mBleDevDiscoverListener = null;
	private DeviceChangeListener mDeviceChangeListener = null;
	private BtSwitchInterface mBtSwitchInterface = null;

	private UpdateSystemTime mUpdateSystemTime = null;
	private UpdateCurrectUser mUpdateCurrectUser= null;
	private UpdateHistoryData mUpdateHistoryData = null;
	private UpdateCurrectData mUpdateCurrectData = null;
	private UpdateFanOpenStatus mUpdateFanOpenStatus = null;
	private UpdateLearnStatus mUpdateLearnStatus = null;
	private UpdateLearnResult mUpdateLearnResult = null;

	public void addCushionBean(CushionBean CushionBean) {
		synchronized (mCushionBeanListLock) {
			mCushionBeanList.clear();
			mCushionBeanList.add(CushionBean);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CushionBean.setCurrectConnector(BleConnector.getInstance());
			mDeviceChangeListener.CushionBeanAdded(CushionBean);
		}
	}

	public void RemoveCushionBean(CushionBean cushionBean) {
		synchronized (mCushionBeanListLock) {
			mCushionBeanList.clear();
			mCushionBeanList.remove(cushionBean);
			mDeviceChangeListener.CushionBeanRemoved(cushionBean);
		}
	}

	public CushionBean getCushionBean(int index) {
		CushionBean cushionBean = null;
		if (index > 0 && index < mCushionBeanList.size()) {
			cushionBean = mCushionBeanList.get(index);
		}
		return cushionBean;
	}

	public CushionBean getDefaultCushionBean() {
		CushionBean cushionBean = null;
		if (0 < mCushionBeanList.size()) {
			cushionBean = mCushionBeanList.get(0);
		}
		return cushionBean;
	}


	/**
	 * @return the mBleDevGattListener
	 */
	public BleDeviceGattListener getBleDevGattListener() {
		return mBleDevGattListener;
	}

	/**
	 * @param mBleDevGattListener
	 *            the mBleDevGattListener to set
	 */
	public void setBleDevGattListener(BleDeviceGattListener bleDevGattListener) {
		this.mBleDevGattListener = bleDevGattListener;
	}

	/**
	 * @return the mBleService
	 */
	public BluetoothLeService getBleService() {
		return mBleService;
	}

	/**
	 * @param mBleService
	 *            the mBleService to set
	 */
	public void setBleService(BluetoothLeService mBleService) {
		this.mBleService = mBleService;
	}

	/**
	 * @return the mBleDevDiscoverListener
	 */
	public BleDeviceDiscoverListener getBleDevDiscoverListener() {
		return mBleDevDiscoverListener;
	}

	/**
	 * @param mBleDevDiscoverListener
	 *            the mBleDevDiscoverListener to set
	 */
	public void setBleDevDiscoverListener(
			BleDeviceDiscoverListener mBleDevDiscoverListener) {
		this.mBleDevDiscoverListener = mBleDevDiscoverListener;
	}

	/**
	 * @return the mDeviceChangeListener
	 */
	public DeviceChangeListener getDeviceChangeListener() {
		return mDeviceChangeListener;
	}

	/**
	 * @param mDeviceChangeListener
	 *            the mDeviceChangeListener to set
	 */
	public void setDeviceChangeListener(
			DeviceChangeListener mDeviceChangeListener) {
		this.mDeviceChangeListener = mDeviceChangeListener;
	}

	
	/**
	 * @return the mBtSwitchInterface
	 */
	public BtSwitchInterface getmBtSwitchInterface() {
		return mBtSwitchInterface;
	}

	/**
	 * @param mBtSwitchInterface the mBtSwitchInterface to set
	 */
	public void setmBtSwitchInterface(BtSwitchInterface mBtSwitchInterface) {
		this.mBtSwitchInterface = mBtSwitchInterface;
	}

	/**
	 * @return the mUpdateSystemTime
	 */
	public UpdateSystemTime getmUpdateSystemTime() {
		return mUpdateSystemTime;
	}

	/**
	 * @param mUpdateSystemTime the mUpdateSystemTime to set
	 */
	public void setmUpdateSystemTime(UpdateSystemTime mUpdateSystemTime) {
		this.mUpdateSystemTime = mUpdateSystemTime;
	}

	/**
	 * @return the mUpdateCurrectUser
	 */
	public UpdateCurrectUser getmUpdateCurrectUser() {
		return mUpdateCurrectUser;
	}

	/**
	 * @param mUpdateCurrectUser the mUpdateCurrectUser to set
	 */
	public void setmUpdateCurrectUser(UpdateCurrectUser mUpdateCurrectUser) {
		this.mUpdateCurrectUser = mUpdateCurrectUser;
	}

	/**
	 * @return the mUpdateHistoryData
	 */
	public UpdateHistoryData getmUpdateHistoryData() {
		return mUpdateHistoryData;
	}

	/**
	 * @param mUpdateHistoryData the mUpdateHistoryData to set
	 */
	public void setmUpdateHistoryData(UpdateHistoryData mUpdateHistoryData) {
		this.mUpdateHistoryData = mUpdateHistoryData;
	}

	/**
	 * @return the mUpdateCurrectData
	 */
	public UpdateCurrectData getmUpdateCurrectData() {
		return mUpdateCurrectData;
	}

	/**
	 * @param mUpdateCurrectData the mUpdateCurrectData to set
	 */
	public void setmUpdateCurrectData(UpdateCurrectData mUpdateCurrectData) {
		this.mUpdateCurrectData = mUpdateCurrectData;
	}

	/**
	 * @return the mUpdateFanOpenStatus
	 */
	public UpdateFanOpenStatus getmUpdateFanOpenStatus() {
		return mUpdateFanOpenStatus;
	}

	/**
	 * @param mUpdateFanOpenStatus the mUpdateFanOpenStatus to set
	 */
	public void setmUpdateFanOpenStatus(UpdateFanOpenStatus mUpdateFanOpenStatus) {
		this.mUpdateFanOpenStatus = mUpdateFanOpenStatus;
	}

	/**
	 * @return the mUpdateLearnStatus
	 */
	public UpdateLearnStatus getmUpdateLearnStatus() {
		return mUpdateLearnStatus;
	}

	/**
	 * @param mUpdateLearnStatus the mUpdateLearnStatus to set
	 */
	public void setmUpdateLearnStatus(UpdateLearnStatus mUpdateLearnStatus) {
		this.mUpdateLearnStatus = mUpdateLearnStatus;
	}

	/**
	 * @return the mUpdateLearnResult
	 */
	public UpdateLearnResult getmUpdateLearnResult() {
		return mUpdateLearnResult;
	}

	/**
	 * @param mUpdateLearnResult the mUpdateLearnResult to set
	 */
	public void setmUpdateLearnResult(UpdateLearnResult mUpdateLearnResult) {
		this.mUpdateLearnResult = mUpdateLearnResult;
	}
	
	
	


}

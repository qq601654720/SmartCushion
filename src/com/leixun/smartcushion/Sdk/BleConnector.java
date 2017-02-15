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

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import com.leixun.smartcushion.Sdk.Ble.BlePrivateProtocolParse;
import com.leixun.smartcushion.Sdk.Ble.BluetoothLeService;
import com.leixun.smartcushion.Sdk.Interface.CushionInterface.BleDeviceGattListener;
import com.leixun.smartcushion.Sdk.bean.CushionBean;
import com.leixun.smartcushion.Sdk.bean.DeviceInfoBean;
import com.leixun.smartcushion.Sdk.util.DataTypeUtils;
import com.leixun.smartcushion.Sdk.util.L;
import com.leixun.smartcushion.Sdk.util.ThreadPool;

/**
 * @author 姚海军
 *
 */
public class BleConnector extends Connector implements
		BleDeviceGattListener {
	private static String TAG = "TclBleConnector";
	private static BleConnector mBleConnector;
	private static CushionBeanManager mCushionBeanManager;
	private BluetoothLeService bleService;

	/**
	 * 
	 */
	public BleConnector() {
		// TODO Auto-generated constructor stub
		super(Connector.ConnectorType.TclBleConnector);
		mCushionBeanManager = CushionBeanManager.getInstance();
	}

	/**
	 * 
	 */
	public static BleConnector getInstance() {
		// TODO Auto-generated constructor stub
		synchronized (BleConnector.class) {
			if (mBleConnector == null) {
				mBleConnector = new BleConnector();
			}

			return mBleConnector;

		}
	}

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

		L.i_BleData("设备端发过来的数据为---->"+DataTypeUtils.bytesToHexString(data) + "------" + length);
		if (data.length == 0)
			return;
		
		BlePrivateProtocolParse.bleResponseLackDataParse(data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huawei.smallRadar.Sdk.TclConnector#connect()
	 */
	@Override
	public void connect(String... address) {
		// TODO Auto-generated method stub
		mCushionBeanManager.setBleDevGattListener(this);
		if (address[0] != null) {
			bleService = mCushionBeanManager.getBleService();
			bleService.connect(address[0]);

		}
	}

	public void setBluetoothDeviceAddress(String address) {
		mCushionBeanManager.setBleDevGattListener(this);
		bleService = mCushionBeanManager.getBleService();
		if (address != null&&bleService!=null) {
			bleService = mCushionBeanManager.getBleService();
			bleService.setBluetoothDeviceAddress(address);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huawei.smallRadar.Sdk.TclConnector#disconnect()
	 */
	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		if (bleService.isBleConnected())
			bleService.disconnect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huawei.smallRadar.Sdk.TclConnector#sendData(byte[], int)
	 */
	@Override
	public void sendData(byte[] data) {
		// TODO Auto-generated method stub
//		int type = data[SEND_BLE_DATA_OFFSET] & 0xff;
//		switch (type) {
//		// 接收到的设备发送给APP的短数据
////		case SEND_LACK_DATA_APP_TO_DEV:
////			L.i("send lack data" + "SEND_LACK_DATA_APP_TO_DEV");
//			sendCmd2Ble(data);
//			break;
////		case SEND_LONG_DATA_APP_TO_DEV:
////			sendLongCmd2Ble(data);
////			break;
//		default:
//			break;
//		}
		sendCmd2Ble(data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huawei.smallRadar.Sdk.Interface.BletoothLeInterface.BleDeviceGattListener
	 * #onConnect(android.bluetooth.BluetoothGatt)
	 */
	@Override
	public void onConnect(BluetoothGatt gatt) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huawei.smallRadar.Sdk.Interface.BletoothLeInterface.BleDeviceGattListener
	 * #onDisconnect(android.bluetooth.BluetoothGatt)
	 */
	@Override
	public void onDisconnect(BluetoothGatt gatt) {
		// TODO Auto-generated method stub
		connectionStatus = STATE_DISCONNECTED;
		L.i(gatt.getDevice().getName() + " Is DisConnected");
		CushionBean cushionBean = new CushionBean();
		DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
		deviceInfoBean.setDeviceName(gatt.getDevice().getName());
		deviceInfoBean.setDeviceID(gatt.getDevice().getAddress());
		cushionBean.setDeviceInfoBean(deviceInfoBean);
		deviceInfoBean.setBluetoothDevice(gatt.getDevice());
		mCushionBeanManager.RemoveCushionBean(cushionBean);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huawei.smallRadar.Sdk.Interface.BletoothLeInterface.BleDeviceGattListener
	 * #onServiceDiscovered(android.bluetooth.BluetoothGatt)
	 */
	@Override
	public void onServiceDiscovered(final BluetoothGatt gatt) {
		// TODO Auto-generated method stub
		connectionStatus = STATE_CONNECTED;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				L.i(gatt.getDevice().getName() + " Is Connected");
				CushionBean cushionBean = new CushionBean();
				DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
				deviceInfoBean.setDeviceName(gatt.getDevice().getName());
				deviceInfoBean.setDeviceID(gatt.getDevice().getAddress());
				deviceInfoBean.setBluetoothDevice(gatt.getDevice());
				cushionBean.setDeviceInfoBean(deviceInfoBean);
				mCushionBeanManager.addCushionBean(cushionBean);
			}
		}).start();


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huawei.smallRadar.Sdk.Interface.BletoothLeInterface.BleDeviceGattListener
	 * #onCharacteristicRead(android.bluetooth.BluetoothGatt,
	 * android.bluetooth.BluetoothGattCharacteristic, int)
	 */
	@Override
	public void onCharacteristicRead(BluetoothGatt gatt,
			BluetoothGattCharacteristic characteristic, int status) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huawei.smallRadar.Sdk.Interface.BletoothLeInterface.BleDeviceGattListener
	 * #onCharacteristicWrite(android.bluetooth.BluetoothGatt,
	 * android.bluetooth.BluetoothGattCharacteristic, int)
	 */
	@Override
	public void onCharacteristicWrite(BluetoothGatt gatt,
			BluetoothGattCharacteristic characteristic, int status) {
		// TODO Auto-generated method stub
		L.v("Data send success");
		L.v("BluetoothGattCallback",
				"onCharWrite "
						+ gatt.getDevice().getName()
						+ " write "
						+ characteristic.getUuid().toString()
						+ " -> "
						+ DataTypeUtils.bytesToHexString(characteristic
								.getValue()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huawei.smallRadar.Sdk.Interface.BletoothLeInterface.BleDeviceGattListener
	 * #onCharacteristicChanged(android.bluetooth.BluetoothGatt,
	 * android.bluetooth.BluetoothGattCharacteristic)
	 */
	@Override
	public void onCharacteristicChanged(final byte[] data) {
		// TODO Auto-generated method stub

		ThreadPool pool = ThreadPool.getInstance();
		if (pool != null) {
			pool.singleThreadsexecute(new Runnable() {

				@Override
				public void run() {
					// 解析characterristic 里面的值
					receiveData(data, data.length);
				}
			});
		}
	}

	/**
	 * 
	 * @title:
	 * @description:
	 * @param:@param cmd 发送命令
	 * @return:void
	 * @throws
	 */
	private static void sendCmd2Ble(byte[] cmd) {
		// L.i(TAG, "sendCmd2Ble start");
		BluetoothLeService bleService = mCushionBeanManager.getBleService();
		L.i("send sendCmd2Ble data" + "sendCmd2Ble");
		if (bleService != null) {
			bleService.requestCmd2Ble(cmd);
		}
	}

	/**
	 * 
	 * @title:
	 * @description:
	 * @param:@param cmd 发送长数据命令
	 * @return:void
	 * @throws
	 */
	private static void sendLongCmd2Ble(byte[] cmd) {
		// L.i(TAG, "sendCmd2Ble start");
		BluetoothLeService bleService = mCushionBeanManager.getBleService();
		if (bleService != null) {
			bleService.requestLongCmd2Ble(cmd);
		}
	}

	/**
	 * 重启Device
	 */
	public static void resetBleSendCmdState() {

		L.i(TAG, "resetBleSendCmdState start");
		BluetoothLeService bleService = mCushionBeanManager.getBleService();
		if (bleService == null) {
			Log.e(TAG, "resetBleSendCmdState BluetoothLeService is null");
			return;
		}
		bleService.resetSendCmdState();
		L.i(TAG, "resetBleSendCmdState end");
	}

	/**
	 * 
	 * @param state
	 *            重发命令 最多发三次的
	 */
	public static void reSendCmd(int state) {
		L.i(TAG, "reSendCmd start");

		BluetoothLeService bleService = mCushionBeanManager.getBleService();
		if (bleService == null) {
			Log.e(TAG, "reSendCmd BluetoothLeService is null");
			return;
		}
		bleService.resetSendCmdState();
		L.i(TAG, "reSendCmd end");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huawei.smallRadar.Sdk.Interface.SmallRadarInterface.BleDeviceGattListener
	 * #onCharacteristicRead(android.bluetooth.BluetoothGatt,
	 * android.bluetooth.BluetoothGattCharacteristic)
	 */
	@Override
	public void onCharacteristicRead(BluetoothGatt gatt,
			BluetoothGattCharacteristic characteristic) {
		// TODO Auto-generated method stub

	}

	// recontennt

}

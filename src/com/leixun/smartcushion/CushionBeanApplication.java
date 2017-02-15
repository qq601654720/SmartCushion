package com.leixun.smartcushion;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.Ble.BluetoothLeService;
import com.leixun.smartcushion.Sdk.util.L;

public class CushionBeanApplication extends Application {

	public boolean userPasswordRemember = false;
	public boolean ad = false;
	public boolean isFirstStart = true;
	public BluetoothLeService mBluetoothLeService;
	private static CushionBeanApplication mCushionBeanApplication;

	private BluetoothAdapter mBluetoothAdapter;
	private boolean isBind = false;
	private final String SMALLRADAR_LOG_FILE = "SmallRadarApplication_Logo";
	public static boolean factorytest = true;

	@Override
	public void onCreate() {
		super.onCreate();
		initBle();
		bindBLEService();
		mCushionBeanApplication = this;

	}

	public static CushionBeanApplication getInstance() {
		return mCushionBeanApplication;

	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	private void initBle() {

		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		// Checks if Bluetooth is supported on the device.
		if (mBluetoothAdapter == null) {
			L.e("mBluetoothAdapter is null!!!");
		}
		// TODO Auto-generated method stub
	}

	public void bindBLEService() {
		Intent intent = new Intent(this, BluetoothLeService.class);
		L.e("start BluetoothLeService");
		// startService(intent);
		isBind = bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
	}

	// Code to manage Service lifecycle.
	private ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName componentName,
				IBinder service) {
			mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
					.getService();
			CushionBeanManager.getInstance().setBleService(mBluetoothLeService);

			mBluetoothLeService.initialize();

		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			mBluetoothLeService.disconnect();
			mBluetoothLeService.close();
			mBluetoothLeService = null;
			CushionBeanManager.getInstance().setBleService(mBluetoothLeService);
		}
	};


}

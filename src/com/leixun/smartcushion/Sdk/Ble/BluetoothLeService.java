package com.leixun.smartcushion.Sdk.Ble;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.task.AbTaskQueue;
import com.leixun.smartcushion.MainActivity;
import com.leixun.smartcushion.R;
import com.leixun.smartcushion.Sdk.Connector;
import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.Interface.CushionInterface.BleDeviceDiscoverListener;
import com.leixun.smartcushion.Sdk.util.BLEReceiver;
import com.leixun.smartcushion.Sdk.util.DataTypeUtils;
import com.leixun.smartcushion.Sdk.util.GattAttributes;
import com.leixun.smartcushion.Sdk.util.IConstants;
import com.leixun.smartcushion.Sdk.util.L;
import com.leixun.smartcushion.Sdk.util.ThreadPool;
import com.leixun.smartcushion.Sdk.util.Uuid;

public class BluetoothLeService extends Service {
	private final static String TAG = BluetoothLeService.class.getSimpleName();

	private BluetoothManager mBluetoothManager;
	private BluetoothAdapter mBluetoothAdapter;
	public String mBluetoothDeviceAddress;

	public BluetoothGatt mBluetoothGatt;
//	private DeviceConnectTask connectTask;

	public static String WRITE_DATA_CONTENT = "write";
	public static String READ_DATA_CONTENT = "read";

	private int mConnectionState = STATE_DISCONNECTED;
	private int mServiceState = IConstants.STATE_DATA_SEND_IDLE;

	private static final int STATE_DISCONNECTED = 0;
	private static final int STATE_CONNECTING = 1;
	private static final int STATE_CONNECTED = 2;

	private Handler mHandler = new Handler();
	private Object mSendThreadLock = new Object();

	private HashMap<String, BluetoothDevice> scannedDevices;
	private static final int chunkSize = 20;
	private int totalChunk;
	private boolean isLastChunk = false;
//	private boolean is_long_data = false;
	private boolean receiveing = false;
	private byte[] buffer;
	private int offect = 0;
	private List<AbTaskItem> mTaskItems = new LinkedList<AbTaskItem>();
	private Object object = new Object();
	final AbTaskQueue mAbTaskQueue = AbTaskQueue.getInstance();
	private static final String action = BluetoothAdapter.ACTION_STATE_CHANGED;

	@Override
	public void onCreate() {

		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return START_STICKY;
	}

	/**
	 * 搜索ble设备
	 */
	Runnable runnableReconnect = new Runnable() {//判断是否断开，断开后搜索

		@Override
		public void run() {
			if (mConnectionState == STATE_DISCONNECTED) {
				if(!initialize()){
					return;
				}
				startScanBleDevice();
			}
		}
	};

	
	//回连的callback
	private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			L.i_BleData("status=====" + status+ "--------------------newState=================" + newState);
			if (newState == BluetoothProfile.STATE_CONNECTED) {//连接成功
				mBluetoothDeviceAddress = gatt.getDevice().getAddress();
				mConnectionState = STATE_CONNECTED;
				L.i_BleData("BluetoothGattCallback","Connected to GATT server.");
				// Attempts to discover services after successful connection.
				boolean discoverServicesSuccess = gatt.discoverServices();
				if(!discoverServicesSuccess){
					L.i_BleData("连接成功开始扫描服务："+"discoverServices()discoverServices()");
				}
				L.i_BleData(TAG, "Attempting to start service discovery state:"
						+ discoverServicesSuccess);
				stopScanBleDevice();

			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {//0 代表断开
				stopScanBleDevice();
				if (mTaskItems != null) {
					mTaskItems.clear();
				}
				mConnectionState = STATE_DISCONNECTED;
				close();
				L.i_BleData(TAG, "Disconnected from GATT server ");
				resetSendCmdState();
				if (CushionBeanManager.getInstance().getBleDevGattListener() != null) {
					CushionBeanManager.getInstance().getBleDevGattListener()
							.onDisconnect(gatt);
				}
			}
		}
		@Override
		public void onServicesDiscovered(final BluetoothGatt gatt, int status) {
			
			L.i_BleData("--------------------搜索服務狀態================="+ status);
			if (status == BluetoothGatt.GATT_SUCCESS) {

				
				L.i_BleData("开始扫描服务："+ "Service discovered");
				L.i("BluetoothGattCallback", "Service discovered ::: " + status);
				CushionBeanManager.getInstance().getBleDevGattListener()
						.onServiceDiscovered(gatt);
				enableNotifications();
		
			} else {
				L.i_BleData("BluetoothGattCallback",
						"onServicesDiscovered received: " + status);
				L.i_BleData("扫描服务异常："+ "Service discovered："+status);
				disconnect();
				CushionBeanManager.getInstance().ResetCushion();
			}
		}

		// 一个Characteristic包含一个Value和多个Descriptor
		@Override
		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			L.i_BleData("BluetoothGattCallback", "onCharacteristicRead");
			if (CushionBeanManager.getInstance().getBleDevGattListener() != null) {
				CushionBeanManager.getInstance().getBleDevGattListener()
						.onCharacteristicRead(gatt, characteristic, status);
			}
		}

		public void onDescriptorWrite(BluetoothGatt gatt,
				BluetoothGattDescriptor descriptor, int status) {
				L.i_BleData(TAG, "onDescriptorWrite"+"-----status==========="+status);

		};

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {
			L.i_BleData("FromBle", "onCharacteristicChanged"+"-----------uuid-----------"+characteristic
					.getUuid()
					.toString());
			if (characteristic
					.getUuid()
					.equals(GattAttributes.TX_CHAR_UUID)) {
				CushionBeanManager.getInstance().getBleDevGattListener()
						.onCharacteristicChanged(characteristic.getValue());
			} 
		}

		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			L.i_BleData("onCharacteristicWrite");
			if (status == BluetoothGatt.GATT_SUCCESS) {
				Log.i(TAG, "write succeeded");
				CushionBeanManager.getInstance().getBleDevGattListener()
						.onCharacteristicWrite(gatt, characteristic, status);
				if (characteristic
						.getUuid()
						.equals(GattAttributes.RX_CHAR_UUID)) {
					resetSendCmdState();
					sendNextCmd(false);
					return;
				}

			} else {
				Log.e(TAG, "write failed: " + status);
				if (characteristic
						.getUuid()
						.equals(GattAttributes.RX_CHAR_UUID)) {
					resetSendCmdState();
					sendNextCmd(true);
					return;
				}

			}

		}

	};
	
	/**
	 * 
	 */
	private void enableNotifications() {
		// TODO Auto-generated method stub
		BluetoothGatt bg = getBlueToothGatt();
		// Service UUID 唯一标示符
		BluetoothGattService bgs = bg.getService(GattAttributes.RX_SERVICE_UUID);
		if (bgs == null) {
			L.i_BleData("BluetoothGattCallback",
					"onServicesDiscovered GattAttributes.CushionBean_BLE_SERVICE is null");
		} else {
			// Characteristic UUID 唯一标示符
				BluetoothGattCharacteristic notifycharacteristic = bgs
						.getCharacteristic(GattAttributes.TX_CHAR_UUID);
				if (notifycharacteristic != null) {
					setCharacteristicNotification(notifycharacteristic,true);
					for (int i = 0; i < notifycharacteristic.getDescriptors().size(); i++) {
						L.i_BleData("notifycharacteristic.getDescriptors()==="+notifycharacteristic.getDescriptors().get(i).getUuid());	
					}
					BluetoothGattDescriptor descriptor = notifycharacteristic.getDescriptor(UUID.fromString(GattAttributes.CUSHIONBEAN_BLE_SERVICE_NOTIFY_DESCRIPTOR));
					descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
					writeDescriptor(descriptor);
							}

		}
	}

	public class LocalBinder extends Binder {

		public BluetoothLeService getService() {
			autoConnection();
			BLEReceiver receiver = new BLEReceiver();
			IntentFilter filter = new IntentFilter();
			// 为IntentFilter添加一个Action
			filter.addAction(action);
			registerReceiver(receiver, filter);
			PendingIntent pendingIntent3 = PendingIntent.getActivity(getApplicationContext(), 0,  
                    new Intent(getApplicationContext(), MainActivity.class), 0);  
            // 通过Notification.Builder来创建通知，注意API Level  
            // API16之后才支持  
            Notification notify3 = new Notification.Builder(getApplicationContext())  
                    .setSmallIcon(R.drawable.logo1028)  
                    .setContentTitle(getString(R.string.app_name))  
                    .setContentText(getString(R.string.app_running))  
                    .setContentIntent(pendingIntent3).build(); // 需要注意build()是在API  
                                                                            // level16及之后增加的，API11可以使用getNotificatin()来替代  
            notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。  
//            manager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示  
            startForeground(1, notify3);
			return BluetoothLeService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	private final IBinder mBinder = new LocalBinder();

	/**
	 * Initializes a reference to the local Bluetooth adapter.
	 *
	 * @return Return true if the initialization is successful.
	 */
	public boolean initialize() {

		L.i_BleData(TAG, "=========initialize()=start=========");
		// 先获取蓝牙的一个代理
		if (mBluetoothManager == null) {
			mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
			if (mBluetoothManager == null) {
				L.i_BleData(TAG, "Unable to initialize BluetoothManager.");
				return false;
			}
		}

		mBluetoothAdapter = mBluetoothManager.getAdapter();
		if (mBluetoothAdapter == null) {
			L.i_BleData(TAG, "Unable to obtain a BluetoothAdapter.");
			return false;
		}
		L.i_BleData(TAG, "=========initialize()=end=========");

		return true;
	}

	/**
     * 
     */
	@SuppressWarnings("deprecation")
	public void startScanBleDevice() {
		scannedDevices = new HashMap<String, BluetoothDevice>();
		scannedDevices.clear();
		L.i(TAG, "startScanBleDevice() start");
		if (mBluetoothAdapter == null) {
			L.i_BleData(TAG, "mBluetoothAdapter is null");
			return;
		}
		mBluetoothAdapter.stopLeScan(mLeScanCallback);
		mBluetoothAdapter.startLeScan(mLeScanCallback);
		L.i(TAG, "startScanBleDevice() end");
	}

	/**
	 * 
	 * @Title: getRemoteDevice
	 * @Description: TODO
	 * @param @param addr
	 * @param @return
	 * @return BluetoothDevice
	 * @exception/throws description
	 */
	public BluetoothDevice getRemoteDevice(String addr) {
		if (mBluetoothAdapter == null) {
			L.i_BleData(TAG, "mBluetoothAdapter is null");
			return null;
		}
		return mBluetoothAdapter.getRemoteDevice(addr);
	}

	/**
     * 
     */
	public void stopScanBleDevice() {
		if (mBluetoothAdapter == null) {
			L.i_BleData(TAG, "mBluetoothAdapter is null");
			return;
		}
		mBluetoothAdapter.stopLeScan(mLeScanCallback);
	}

	/**
	 * Connects to the GATT server hosted on the Bluetooth LE device.
	 *
	 * @param address
	 *            The device address of the destination device.
	 *
	 * @return Return true if the connection is initiated successfully. The
	 *         connection result is reported asynchronously through the
	 *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
	 *         callback.
	 */
	//蓝牙连接
	public boolean connect(final String address) {
		L.i(TAG, "connect address:" + address);
		if (mBluetoothAdapter == null || address == null) {
			L.i_BleData(TAG,
					"BluetoothAdapter not initialized or unspecified address.");
			return false;
		}
		disconnect();
		if (!mBluetoothAdapter.isEnabled()) {
			return false;
		}

		final BluetoothDevice device = mBluetoothAdapter
				.getRemoteDevice(address);
		if (device == null) {
			L.i_BleData(TAG, "Device not found.  Unable to connect.");
			return false;
		}

		L.i("yaohaijun", "device.name:" + device.getName() + "  device.addr:"
				+ device.getAddress());
		

//		connectTask = new DeviceConnectTask(this, device) {
//			@Override
//			protected void onProgressUpdate(BluetoothGatt... gatt) {
//				BluetoothGattSingleton.setGatt(gatt[0]);
//			}
//		};

//		connectTask.execute();
		mBluetoothDeviceAddress = address;
		if(mBluetoothGatt!=null){
			return false;
		}
		mBluetoothGatt = device.connectGatt(this, false,//建立连接
				mGattCallback);
		if (mBluetoothGatt == null) {
			L.i_BleData("mBluetoothGatt是空的！");
		}else{
//			BluetoothGattSingleton.setGatt(mBluetoothGatt);
		}
		mConnectionState = STATE_CONNECTING;
		return true;
	}

	/**
	 * Disconnects an existing connection or cancel a pending connection. The
	 * disconnection result is reported asynchronously through the
	 * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
	 * callback.
	 */
	public void disconnect() {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			L.i_BleData(TAG, "disconnect()--BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.disconnect();
	}

	/**
	 * After using a given BLE device, the app must call this method to ensure
	 * resources are released properly.
	 */
	public void close() {
		if (mBluetoothAdapter == null ||mBluetoothGatt == null) {
			return;
		}
		mBluetoothGatt.close();
		mBluetoothGatt = null;
	}

	/**
	 * 
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	public void onDestroy() {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			L.i_BleData(TAG, "disconnect()--BluetoothAdapter not initialized");
			return;
		}else{
			refresh(mBluetoothGatt);
		}
		disconnect();
		L.i_BleData("蓝牙连接onDestroy():"+"disconnect()");
		close();
	}

	/**
	 * Request a read on a given {@code BluetoothGattCharacteristic}. The read
	 * result is reported asynchronously through the
	 * {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
	 * callback.
	 *
	 * @param characteristic
	 *            The characteristic to read from.
	 */
	public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			L.i_BleData(TAG, "readCharacteristic--BluetoothAdapter not initialized");
			return;
		}
		L.i_BleData(TAG, "To readCharacteristic");
		mBluetoothGatt.readCharacteristic(characteristic);
	}

	/**
	 * 
	 * @param characteristic
	 * 
	 */
	public void writeCharacteristic(BluetoothGattCharacteristic characteristic) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			L.i_BleData(TAG, "writeCharacteristic--BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.writeCharacteristic(characteristic);
	}

	/**
	 * 
	 * @param descriptor
	 */
	public void writeDescriptor(BluetoothGattDescriptor descriptor) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			L.i_BleData(TAG, "writeDescriptor--BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.writeDescriptor(descriptor);
	}

	/**
	 * Enables or disables notification on a give characteristic.
	 *
	 * @param characteristic
	 *            Characteristic to act on.
	 * @param enabled
	 *            If true, enable notification. False otherwise.
	 */
	public void setCharacteristicNotification(
			BluetoothGattCharacteristic characteristic, boolean enabled) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			L.i_BleData(TAG, "setCharacteristicNotification--BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
	}

	/**
	 * Retrieves a list of supported GATT services on the connected device. This
	 * should be invoked only after {@code BluetoothGatt#discoverServices()}
	 * completes successfully.
	 *
	 * @return A {@code List} of supported services.
	 */
	public List<BluetoothGattService> getSupportedGattServices() {
		if (mBluetoothGatt == null)
			return null;

		return mBluetoothGatt.getServices();

	}

	public BluetoothGatt getBlueToothGatt() {
		return mBluetoothGatt;
	}

	/**
	 * 
	 * @title:
	 * @description:
	 * @param:@param cmd 请求命令 给设备发送命令
	 * @return:void
	 * @throws
	 */
	public void requestCmd2Ble(byte[] cmd) {
		if (cmd == null || cmd.length == 0) {
			L.i_BleData(TAG, "requestCmd2Ble  cmd error");
			return;
		}
		if (mConnectionState != STATE_CONNECTED) {
			L.i_BleData(TAG, "ble device has not connected");
			connect(mBluetoothDeviceAddress);
			return;
		}
		L.i_BleData("mServiceState :" + mServiceState);
		if (mServiceState == IConstants.STATE_DATA_SENDING) {
			L.i_BleData(TAG, "send data service is busy, plase wait!");
			// return;
		}
		sendDataThread(cmd);
		// startSendThread(cmd);

	}

	/**
	 * 
	 * @title:
	 * @description:
	 * @param:@param cmd 请求长命令 给设备发送命令
	 * @return:void
	 * @throws
	 */
	public void requestLongCmd2Ble(byte[] cmd) {
		if (cmd == null || cmd.length == 0) {
			L.i_BleData(TAG, "requestCmd2Ble  cmd error");
			return;
		}
		if (mConnectionState != STATE_CONNECTED) {
			L.i_BleData(TAG, "ble device has not connected");
			connect(mBluetoothDeviceAddress);
			return;
		}
		L.i_BleData(TAG, "mServiceState :" + mServiceState);

		// startSendLongThread(cmd);
		sendDataThread(cmd);
	}

	/**
	 * 
	 * @Title: startSendThread
	 * @Description: TODO
	 * @param @param cmd
	 * @return void
	 * @exception/throws description
	 */
	private void startSendThread(final byte[] cmd) {

		ThreadPool pool = ThreadPool.getInstance();
		if (pool != null) {
//			is_long_data = false;
			sendCmd2Ble(cmd);
		}
	}

	/**
	 * 
	 * @Title: startSendLongDataThread
	 * @Description: 发送长数据线程
	 * @param @param cmd
	 * @return void
	 * @exception/throws description
	 */
	private void startSendLongThread(final byte[] cmd) {

		if ((cmd.length % chunkSize) > 0) {
			totalChunk = (cmd.length / chunkSize) + 1;
		} else {
			totalChunk = (cmd.length / chunkSize);
		}
		isLastChunk = false;
//		is_long_data = true;
		int ChunkNumble = 0;
		while ((isLastChunk == false && (mConnectionState == STATE_CONNECTED))) {
			if (mServiceState == IConstants.STATE_DATA_SENDING) {
				continue;
			}

			L.i_BleData("发送第" + ChunkNumble + "个包");
			if ((totalChunk > (ChunkNumble + 1))) {
				isLastChunk = false;
				sendCmd2Ble(DataTypeUtils.bytesCut(cmd,
						ChunkNumble * chunkSize, chunkSize));

				ChunkNumble++;

			} else {
				isLastChunk = true;
				if (cmd.length % chunkSize > 0) {
					sendCmd2Ble(DataTypeUtils.bytesCut(cmd, ChunkNumble
							* chunkSize, cmd.length % chunkSize));
				} else {
					sendCmd2Ble(DataTypeUtils.bytesCut(cmd, ChunkNumble
							* chunkSize, chunkSize));
				}

				ChunkNumble++;
				L.i_BleData("发送结束");
				sendNextCmd(false);
			}
		}
	}

	/**
     * 
     */
	private void sendCmd2Ble(final byte[] cmd) {
		L.i(TAG, "sendCmd2Ble start");
		synchronized (mSendThreadLock) {
			mCmdBuffer = cmd;

			BluetoothGattCharacteristic characteristic = null;
			try {
				BluetoothGatt bg = getBlueToothGatt();
				BluetoothGattService bgs = bg.getService(GattAttributes.RX_SERVICE_UUID);
				if (bgs == null) {
					L.i_BleData(TAG,
							"GattAttributes.RX_SERVICE_UUID is null");
					isLastChunk = true;
					resetSendCmdState();
					return;
				}
				mServiceState = IConstants.STATE_DATA_SENDING;
//				if (!is_long_data) {

					characteristic = bgs
							.getCharacteristic(GattAttributes.RX_CHAR_UUID);
					if (characteristic == null) {
						isLastChunk = true;
						L.i_BleData(TAG,
								"GattAttributes.RX_CHAR_UUID is null");
						resetSendCmdState();
						return;
					}
//				}
//				else {
//					characteristic = bgs
//							.getCharacteristic(UUID
//									.fromString(GattAttributes.CUSHIONBEAN_BLE_SERVICE_WRITE_NOTIFY_CHARACTERISTIC));
//					if (characteristic == null) {
//						isLastChunk = true;
//						L.i_BleData(TAG,
//								"GattAttributes.CushionBean_BLE_SERVICE_WRITE_NOTIFY_CHARACTERISTIC is null");
//						resetSendCmdState();
//						return;
//					}
//					characteristic
//							.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
//				}
				characteristic.setValue(cmd);
				writeCharacteristic(characteristic);

			} catch (Exception e) {
				L.i_BleData(TAG, "write failed,characteristic is  not exist ");
				isLastChunk = true;
				resetSendCmdState();
			}
		}
		L.i(TAG, "sendCmd2Ble end");
	}

	/**
	 * 
	 * @title:
	 * @description:
	 * @param:@return
	 * @return:int
	 * @throws
	 */
	public boolean isBleConnected() {
		boolean rtn = false;
		switch (mConnectionState) {
		case STATE_CONNECTED:
			rtn = true;
			break;

		default:
			break;
		}
		return rtn;
	}

	/**
	 * 
	 * @title:
	 * @description:
	 * @param:@return
	 * @return:int
	 * @throws
	 */
	public boolean isBleDisConnected() {
		boolean rtn = false;
		switch (mConnectionState) {
		case STATE_DISCONNECTED:
			rtn = true;
			break;

		default:
			break;
		}
		return rtn;
	}

	/**
	 * 
	 * @title:
	 * @description:
	 * @param:@return
	 * @return:int
	 * @throws
	 */
	public boolean isBleConnecting() {
		boolean rtn = false;
		switch (mConnectionState) {
		case STATE_CONNECTING:
			rtn = true;
			break;

		default:
			break;
		}
		return rtn;
	}

	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {//自动连接逻辑

		@Override
		public void onLeScan(final BluetoothDevice device, int rssi,
				byte[] scanRecord) {

			BleDeviceDiscoverListener listener = CushionBeanManager
					.getInstance().getBleDevDiscoverListener();
						List<UUID> uuids = Uuid.parseFromAdvertisementData(scanRecord);
			for (UUID uuid : uuids) {
				if (!scannedDevices.containsKey(device.getAddress())
						&& uuid.equals(GattAttributes.CUSHIONBEAN_SERVICE_UUID)) {
					L.i_BleData("shebei",
							device.getName() + " --- " + device.getAddress() + " -- "
									+ device.getUuids());
					scannedDevices.put(device.getAddress(), device);
					if (listener != null) {
						listener.onDeviceDiscovered(device);
						if (mBluetoothDeviceAddress != null
								&& mBluetoothDeviceAddress.equals(device
										.getAddress())) {
							stopScanBleDevice();

							connect(mBluetoothDeviceAddress);
						}
					} else {



					}
				}
			}

		}
	};

	/**
	 * 
	 * @param addr
	 * @return
	 */
	public BluetoothDevice getCurrBluetoothDevice() {
		if (mBluetoothAdapter == null) {
			return null;
		}
		BluetoothDevice device = null;
		device = mBluetoothAdapter.getRemoteDevice(mBluetoothDeviceAddress);
		return device;
	}

	/**
	 * @return the mBluetoothDeviceAddress
	 */
	public String getBluetoothDeviceAddress() {
		return this.mBluetoothDeviceAddress;
	}

	/**
	 * @param mBluetoothDeviceAddress
	 *            the mBluetoothDeviceAddress to set
	 */
	public void setBluetoothDeviceAddress(String mBluetoothDeviceAddress) {
		this.mBluetoothDeviceAddress = mBluetoothDeviceAddress;
	}

	private byte[] mCmdBuffer = null;

	/**
	 * 
	 */
	public void resetSendCmdState() {
		mServiceState = IConstants.STATE_DATA_SEND_IDLE;
		if (mCmdBuffer != null) {
			mCmdBuffer = null;
		}
	}

//	public class DeviceConnectTask extends
//			AsyncTask<Void, BluetoothGatt, Boolean> {
//		public static final String TAG = "DeviceGattTask";
//		public Context mcontext;
//		private final BluetoothDevice mmDevice;
//
//
//		public DeviceConnectTask(Context context, BluetoothDevice device) {
//			Log.d(TAG, "init");
//			BluetoothSocket tmp = null;
//			mmDevice = device;
//			mcontext = context;
//
//		}
//
//
//		@Override
//		protected Boolean doInBackground(Void... params) {
//			
//			try {
//				Thread.sleep(1000);
//
//					return false;
//				}
//				publishProgress(mBluetoothGatt);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//
//			return true;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//		@Override
//		protected void onPostExecute(Boolean aBoolean) {
//			super.onPostExecute(aBoolean);
//		}
//
//		@Override
//		protected void onProgressUpdate(BluetoothGatt... values) {
//			super.onProgressUpdate(values);
//			L.i_BleData("onProgressUpdate SUCCESS");
//		}
//
//		@Override
//		protected void onCancelled(Boolean aBoolean) {
//			super.onCancelled(aBoolean);
//			// cancel();
//		}
//
//		@Override
//		protected void onCancelled() {
//			super.onCancelled();
//		}
//	}

	/**
	 * 自动重新连接
	 */
	private void autoConnection() {
		// TODO Auto-generated method stub

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {

					ThreadPool.getInstance().cacheThreadsexecute(
							runnableReconnect);
					try {
						Thread.sleep(5 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

	/**
	 * 
	 */
	private void sendDataThread(final byte[] cmd) {
		// TODO Auto-generated method stub

		final AbTaskItem item = new AbTaskItem();
		item.setListener(new AbTaskListener() {

			@Override
			public void update() {
			}

			@Override
			public void get() {
				startSendThread(cmd);
				// mServiceState = IConstants.STATE_DATA_SENDING;
//				if (cmd[0] == (byte) Connector.SEND_LACK_DATA_APP_TO_DEV) {
//					startSendThread(cmd);
//				} else if (cmd[0] == (byte) Connector.SEND_LONG_DATA_APP_TO_DEV) {
//					startSendLongThread(cmd);
//				}
			}
		});
		synchronized (object) {
			if (mTaskItems.size() == 0) {
				addTaskItems(item);
				mAbTaskQueue.execute(mTaskItems.get(0));
			} else {
				mTaskItems.add(item);
			}
		}
		// 开始执行

	}

	/**
	 * 
	 */
	public void sendNextCmd(boolean sendfail) {
		// TODO Auto-generated method stub
		if (mTaskItems.size() > 0) {
			if (!sendfail) {
				RemoveTaskItems(0);
			}
			if (mTaskItems.size() > 0) {
				mAbTaskQueue.execute(mTaskItems.get(0));
			}

		}
	}

	/**
	 * 
	 */
	private void addTaskItems(AbTaskItem item) {
		// TODO Auto-generated method stub
		synchronized (object) {
			mTaskItems.add(item);
		}
	}

	/**
	 * 
	 */
	private void RemoveTaskItems(int item) {
		// TODO Auto-generated method stub
		synchronized (object) {
			mTaskItems.remove(item);
		}
	}

    private static boolean refresh (BluetoothGatt gatt) {
        try {
            Log.d(TAG, "refresh device cache");
            Method localMethod = gatt.getClass().getMethod("refresh", (Class[]) null);
            if (localMethod != null) {
                boolean result = (Boolean) localMethod.invoke(gatt, (Object[]) null);
                if (!result)
                    Log.d(TAG, "refresh failed");
                return result;
            }
        } catch (Exception e) {
            Log.e(TAG, "An exception occurred while refreshing device cache");
        }
        return false;
    }
}

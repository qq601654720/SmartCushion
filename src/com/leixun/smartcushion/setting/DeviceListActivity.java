/**   
 * @Title:SettingDeviceManger.java
 * @Package com.huawei.smallRadar.Setting
 * @Description: 
 * @author 姚海军  
 * @date 2016年4月15日上午9:25:38
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年4月15日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.setting;

import java.util.ArrayList;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.ab.view.titlebar.AbTitleBar;
import com.leixun.smartcushion.BaseActivity;
import com.leixun.smartcushion.BaseActivity2;
import com.leixun.smartcushion.R;
import com.leixun.smartcushion.Sdk.BleConnector;
import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.Ble.BluetoothLeService;
import com.leixun.smartcushion.Sdk.Db.DbManger;
import com.leixun.smartcushion.Sdk.Interface.BleInterface.ScanBleInterface;
import com.leixun.smartcushion.Sdk.Interface.CushionInterface.DeviceChangeListener;
import com.leixun.smartcushion.Sdk.bean.CushionBean;
import com.leixun.smartcushion.Sdk.util.ThreadPool;

/**
 * @author 姚海军
 * 
 */
public class DeviceListActivity extends BaseActivity2 implements
		DeviceChangeListener, ScanBleInterface,
		OnHeaderRefreshListener, OnItemClickListener {
	private String TAG = "SettingDeviceManger";
	public AbTitleBar mAbTitleBar = null;
//	private List<DeviceInfoBean> mDeviceInfoBeans = new LinkedList<DeviceInfoBean>();
	private ListView  mScanningDeviceListview;
	private static final long SCAN_PERIOD = 10000;
	private LeDeviceListAdapter mLeDeviceListAdapter;
	private AbPullToRefreshView mAbPullToRefreshView = null;
	private BluetoothLeService bleService;
	// private boolean autoConnect = true;
	private boolean scanResultisNull = true;
	private static final int RESET_DEVICE_LIST = 0x01;
	private static final int UPDATE_CONNECTED_DEVICE= 0x02;
	private static final int UPDATE_CONNECTING_DEVICE= 0x03;
	public BluetoothAdapter mBluetoothAdapter;
	private String connectingaddress = "";
	Handler mDeviceMangeHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String address ="";
			switch (msg.what) {
			case RESET_DEVICE_LIST:
				resetDeviceList();
				handleRemoveDevice();
				break;
			case UPDATE_CONNECTING_DEVICE:
				 address = msg.obj.toString();
				mLeDeviceListAdapter.updateConnectingSelect(mLeDeviceListAdapter.getItemId(address));
				break;
			case UPDATE_CONNECTED_DEVICE:
				 address = msg.obj.toString();
				mLeDeviceListAdapter.updateConnectedSelect(mLeDeviceListAdapter.getItemId(address));
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.setting_device_manger_layout);
		showTitleBar();
		initView();
		initBle();
		initListener();
		initData();
	}

	/**
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		mScanningDeviceListview = (ListView) findViewById(R.id.lv_scanning_device);
		mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.anim.progress_load));
	}

	private void initBle() {

		// TODO Auto-generated method stub
		mLeDeviceListAdapter = new LeDeviceListAdapter(this);
		mScanningDeviceListview.setAdapter(mLeDeviceListAdapter);
	}

	/**
	 * 
	 */
	private void showTitleBar() {
		// TODO Auto-generated method stub
		mAbTitleBar = this.getTitleBar();
		mAbTitleBar.getTitleTextButton().setPadding(0, 35, 0, 35);
		mAbTitleBar.setTitleText(getResources().getString(
				R.string.ble_connect));
		mAbTitleBar.setLogo(R.drawable.back_arrow);
		mAbTitleBar.setTitleBarBackground(R.drawable.nav_bar);
		mAbTitleBar.clearRightView();
		mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);
//		mAbTitleBar.setTitleBarBackground(R.color.title_bar_Color);
	}

	/**
	 * 
	 */
	private void initListener() {
		// TODO Auto-generated method stub
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mScanningDeviceListview.setOnItemClickListener(this);
	}

	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
		bleService = CushionBeanManager.getInstance().getBleService();
		if(getDefaultCushion()!=null){
			mLeDeviceListAdapter.addDevice(getDefaultCushion().getDeviceInfoBean().getBluetoothDevice());
			if(mDeviceMangeHandler!=null){
				Message message = new Message();
				message.what= UPDATE_CONNECTED_DEVICE;
				message.obj = getDefaultCushion().getDeviceInfoBean().getDeviceID();
				mDeviceMangeHandler.sendMessage(message);
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!mBluetoothAdapter.isEnabled()) {
			// BluetoothDialog dialog = new BluetoothDialog(
			// SettingDeviceManger.this, R.style.blutoothDialog,
			// mBluetoothAdapter);
			// dialog.show();
		} else {
			stopScanLeDevice();
			startScanLeDevice();
		}
	}



	private void handleScanResult(final BluetoothDevice device) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// 过滤没有名字的BLE设备
				if(device.getAddress()==null){
					return;
				}
				scanResultisNull =false;
				mLeDeviceListAdapter.addDevice(device);
				// }
				mLeDeviceListAdapter.notifyDataSetChanged();
			}
		});
	}

	private void handleRemoveDevice() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mLeDeviceListAdapter.updateConnectingSelect(-1);
			}

		});

	}

	private void startScanLeDevice() {
		stopScanLeDevice();
		ThreadPool.getInstance().cacheThreadsexecute(new Runnable() {

			@Override
			public void run() {
				if (bleService != null) {
					// Stops scanning after a pre-defined scan period.
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							stopScanLeDevice();
							// mRootView.findViewById(R.id.scanProgress).setVisibility(View.GONE);
						}
					}, SCAN_PERIOD);

					bleService.startScanBleDevice();
				}

			}
		});
		
	}

	private void stopScanLeDevice() {

		if (bleService != null) {
			bleService.stopScanBleDevice();
		}
	}

	// Adapter for holding devices found through scanning.
	public class LeDeviceListAdapter extends BaseAdapter {
		private ArrayList<BluetoothDevice> mLeDevices;
		private int selectPos = -1;
		private int connectionedPos = -1;

		private LayoutInflater mInflator;

		public LeDeviceListAdapter(Context context) {
			super();
			mLeDevices = new ArrayList<BluetoothDevice>();
			selectPos = -1;
			connectionedPos = -1;
			mInflator = LayoutInflater.from(context);
		}

		public void addDevice(BluetoothDevice device) {
			if (!mLeDevices.contains(device)) {
				mLeDevices.add(device);
			}

		}

		public void clearDevice() {
			mLeDevices.clear();
			notifyDataSetChanged();
		}

		public BluetoothDevice getDevice(int position) {
			return mLeDevices.get(position);
		}

		public void clear() {
			mLeDevices.clear();
		}

		@Override
		public int getCount() {
			return mLeDevices.size();
		}

		@Override
		public Object getItem(int i) {
			return mLeDevices.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}
		
		public int getItemId(String address) {
			for (int j = 0; j < mLeDevices.size(); j++) {
				if (mLeDevices.get(j).getAddress().equals(address)) {
					return j;
				}
			}
			
			return -1;
			
		}

		@Override
		public View getView(int position, View convertView, ViewGroup viewGroup) {
			ViewHolder viewHolder;
			// General ListView optimization code.
			if (convertView == null) {
				convertView = mInflator.inflate(R.layout.device_manger_item,
						viewGroup, false);
				viewHolder = new ViewHolder();
				viewHolder.tvItem = (TextView) convertView
						.findViewById(R.id.custom_tv_setting);
				viewHolder.imgSelected = (ImageView) convertView
						.findViewById(R.id.custom_img_check);
				viewHolder.progressBar = (ProgressBar) convertView
						.findViewById(R.id.progress_loading);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			BluetoothDevice device = mLeDevices.get(position);
			final String deviceName = device.getName();
			
			if (selectPos == position) {
				viewHolder.progressBar.setVisibility(View.VISIBLE);
			} else {
				viewHolder.progressBar.setVisibility(View.GONE);
			}
			
			if(connectionedPos == position){
				viewHolder.imgSelected.setImageResource(R.drawable.checkmark);
			}else{
				viewHolder.imgSelected.setImageResource(R.drawable.checkmark_normal);
			}
			if (deviceName != null && deviceName.length() > 0)
				viewHolder.tvItem.setText(deviceName);
			else
				viewHolder.tvItem.setText(R.string.unknown_device);

			return convertView;
		}

		public ArrayList<BluetoothDevice> getLeDevices() {
			return mLeDevices;
		}

		public void setLeDevices(ArrayList<BluetoothDevice> mLeDevices) {
			this.mLeDevices = mLeDevices;
		}

		/**
		 * 
		 */
		public void updateConnectingSelect(int position) {
			// TODO Auto-generated method stub
			selectPos = position;
			notifyDataSetChanged();
		}
		
		/**
		 * 刷新当前连接的设备
		 */
		public void updateConnectedSelect(int position) {
			// TODO Auto-generated method stub
			connectionedPos = position;
			selectPos = -1;
			notifyDataSetChanged();
		}
	}

	class ViewHolder {
		public TextView tvItem;
		public ImageView imgSelected;
		public ProgressBar progressBar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener#
	 * onHeaderRefresh(com.ab.view.pullview.AbPullToRefreshView)
	 */
	@Override
	public void onHeaderRefresh(AbPullToRefreshView arg0) {
		
		
		// TODO Auto-generated method stub
		resetDeviceList();
		stopScanLeDevice();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				mAbPullToRefreshView.onHeaderRefreshFinish();
				if(scanResultisNull){
					scanOrConnectionFail();
				}
			}
		}, 5000);

	}





	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch (parent.getId()) {
		case R.id.lv_scanning_device:					
			if(mDeviceMangeHandler!=null){
				if(getDefaultCushion()!=null){
					if(getDefaultCushion().getDeviceInfoBean().getDeviceID().equals(mLeDeviceListAdapter.getDevice(position).getAddress())){
						return;
					}
				}
				if(connectingaddress.equals(mLeDeviceListAdapter.getDevice(position).getAddress())){
					return;
				}else{
					connectingaddress = mLeDeviceListAdapter.getDevice(position).getAddress();
				}
				BleConnector.getInstance().connect(mLeDeviceListAdapter.getDevice(position).getAddress());
				Message message = new Message();
				message.what= UPDATE_CONNECTING_DEVICE;
				message.obj = mLeDeviceListAdapter.getDevice(position).getAddress();
				mDeviceMangeHandler.sendMessage(message);
				}
			break;
		

		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		stopScanLeDevice();
	}


	@Override
	public void onstartScanLeDevice() {
		// TODO Auto-generated method stub
		stopScanLeDevice();
		startScanLeDevice();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		CushionBeanManager.getInstance().setBleDevDiscoverListener(null);
	}

	/**
	 * 
	 */
	private void resetDeviceList() {
		// TODO Auto-generated method stub
		scanResultisNull = true;
		mLeDeviceListAdapter.updateConnectingSelect(-1);
		mLeDeviceListAdapter.clearDevice();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huawei.smallRadar.Sdk.BaseActivity2#onResetBtSwitch()
	 */
	@Override
	public void onResetBtSwitch() {
		// TODO Auto-generated method stub
		super.onResetBtSwitch();
		startScanLeDevice();
	}
	
	/* (non-Javadoc)
	 * @see com.huawei.smallRadar.Sdk.BaseActivity2#onBtSwitechOff()
	 */
	@Override
	public void onBtSwitechOff() {
		// TODO Auto-generated method stub
		super.onBtSwitechOff();
		if(mDeviceMangeHandler!=null)
		mDeviceMangeHandler.sendEmptyMessage(RESET_DEVICE_LIST);
	}
	
	/* (non-Javadoc)
	 * @see com.huawei.smallRadar.Sdk.BaseActivity2#onBtSwitechOn()
	 */
	@Override
	public void onBtSwitechOn() {
		// TODO Auto-generated method stub
		super.onBtSwitechOn();
		startScanLeDevice();
	}
	
	/**
	 * 
	 */
	private void scanOrConnectionFail() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.SmallRadarInterface.DeviceChangeListener#CushionBeanAdded(com.leixun.smartcushion.CushionBean)
	 */
	@Override
	public void CushionBeanAdded(CushionBean cushionBean) {
		// TODO Auto-generated method stub
		super.CushionBeanAdded(cushionBean);
		if(mDeviceMangeHandler!=null){
			Message message = new Message();
			message.what= UPDATE_CONNECTED_DEVICE;
			message.obj = cushionBean.getDeviceInfoBean().getDeviceID();
			mDeviceMangeHandler.sendMessage(message);
		}
		
//		if(CushionBeanManager.getInstance().getBeans().size()==0){
//			Intent intent = new Intent();
//			intent.setClass(DeviceListActivity.this, EditUserNameActivity.class);
//			startActivity(intent);
//			finish();
//		}
	}

	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.SmallRadarInterface.DeviceChangeListener#CushionBeanRemoved(com.leixun.smartcushion.CushionBean)
	 */
	@Override
	public void CushionBeanRemoved(CushionBean cushionBean) {
		// TODO Auto-generated method stub
		super.CushionBeanRemoved(cushionBean);
	}
	
	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.BaseActivity#onDeviceDiscovered(android.bluetooth.BluetoothDevice)
	 */
	@Override
	public void onDeviceDiscovered(BluetoothDevice device) {
		// TODO Auto-generated method stub
		super.onDeviceDiscovered(device);
		handleScanResult(device);

	}

	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.BleInterface.ScanBleInterface#onregistBleListener()
	 */
	@Override
	public void onregistBleListener() {
		// TODO Auto-generated method stub
		registBleListener();
	}
	
	/**
	 * 
	 */
	private CushionBean getDefaultCushion() {

		// TODO Auto-generated method stub
		CushionBean cushionBean = CushionBeanManager.getInstance().getDefaultCushionBean();
		return cushionBean;
	}
	

}
/**   
 * @Title:BaseActivity.java
 * @Package com.leixun.smartcushion
 * @Description: 
 * @author 姚海军  
 * @date 2016年11月15日下午2:59:39
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年11月15日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.view.titlebar.AbTitleBar;
import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.Interface.BleInterface.BtSwitchInterface;
import com.leixun.smartcushion.Sdk.Interface.CushionInterface.BleDeviceDiscoverListener;
import com.leixun.smartcushion.Sdk.Interface.CushionInterface.DeviceChangeListener;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateCurrectData;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateCurrectUser;
import com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateHistoryData;
import com.leixun.smartcushion.Sdk.bean.Alarm;
import com.leixun.smartcushion.Sdk.bean.CushionBean;
import com.leixun.smartcushion.Sdk.bean.ErrDataBean;
import com.leixun.smartcushion.Sdk.bean.UserBean;
import com.leixun.smartcushion.Sdk.perference.CushionPreferences;
import com.leixun.smartcushion.Sdk.util.Tools;
import com.leixun.smartcushion.setting.DeviceListActivity;
import com.leixun.smartcushion.setting.EditUserNameActivity;
import com.leixun.smartcushion.view.CustomDialog;
import com.leixun.smartcushion.view.CustomDialog.Builder;

/**
 * @author 姚海军
 *
 */
public class BaseActivity2 extends AbActivity implements DeviceChangeListener,BtSwitchInterface, BleDeviceDiscoverListener,UpdateCurrectUser,UpdateCurrectData,UpdateHistoryData{
	protected AbTitleBar mAbTitleBar;
	public final int SHOW_OPEN_BT_DIALOG = 0xaa;
	public final int SHOW_ERR_DATA_DIALOG = 0xab;
	public final int HIDE_ERR_DATA_DIALOG = 0xaC;
	public final int SHOW_ALARM_DATA = 0xaD;
	public Handler mHandler;
	public boolean BtIsOpen = true;
	private CustomDialog openBtDialog = null;
	private CustomDialog openGpsDialog = null;
	private CustomDialog updataDataDialog = null;
	private static final int REQUEST_CODE_LOCATION_SETTINGS = 2;
	public BluetoothAdapter mBluetoothAdapter;
	private Timer timer;

	public void initHandle() {
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {		
				case SHOW_OPEN_BT_DIALOG:
					if (!mBluetoothAdapter.isEnabled()) {
						BtIsOpen = false;
						showBtDialog();
					}else{
							if(!Tools.isLocationEnable(BaseActivity2.this)){
								if (Build.VERSION.SDK_INT >= 23) {
									showGpsDialog();
							}
						}else{
							
						}
					}
					break;
				case SHOW_ERR_DATA_DIALOG:
					Builder build = new CustomDialog.Builder(BaseActivity2.this);
					build.setMessage(R.string.sys_data);		
					updataDataDialog = build.loadindDialog();
					updataDataDialog.setCancelable(false);
					updataDataDialog.setOnKeyListener(new OnKeyListener() {
						
						@Override
						public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
							// TODO Auto-generated method stub
							return false;
						}
					});
					updataDataDialog.show();					
					break;
				case HIDE_ERR_DATA_DIALOG:
					if(updataDataDialog.isShowing()){
						updataDataDialog.dismiss();
					}
					break;
				case SHOW_ALARM_DATA:
					Alarm alarm = (Alarm) msg.obj;
					
					if(alarm.getPosture()==Alarm.POSTURE_WRONG){
						Toast.makeText(BaseActivity2.this, "坐姿错误，请调整！", Toast.LENGTH_SHORT).show();
					}
					if(alarm.getTemperature_control_alarm()==Alarm.TEMPERATURE_ALARM){
						Toast.makeText(BaseActivity2.this, "当前温度过高，请调整！", Toast.LENGTH_SHORT).show();
					}
					if(alarm.getSedentary_alarm()==Alarm.SEDENTARY_ALARM){
						Toast.makeText(BaseActivity2.this, "您坐的时间太久了，请休息一会！", Toast.LENGTH_SHORT).show();
					}
					
					break;
				}
			}
		};
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		initState();
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();
		ShowBtTimeOut();
		
	}
	
	
	/**
	* 动态的设置状态栏  实现沉浸式状态栏
	*/
	protected void initState() {

	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	        //透明状态栏
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	        //透明导航栏
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
	        
	        mAbTitleBar.setVisibility(View.VISIBLE);
	        //获取到状态栏的高度
	        int statusHeight = getStatusBarHeight();
	        //动态的设置隐藏布局的高度
	        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mAbTitleBar.getLayoutParams();
	        params.height = statusHeight;
	        mAbTitleBar.setLayoutParams(params);
	    }
	}
	
	/**
	* 通过反射的方式获取状态栏高度
	* @return
	*/
	private int getStatusBarHeight() {
	    try {
	        Class<?> c = Class.forName("com.android.internal.R$dimen");
	        Object obj = c.newInstance();
	        Field field = c.getField("status_bar_height");
	        int x = Integer.parseInt(field.get(obj).toString());
	        return getResources().getDimensionPixelSize(x);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	     return 0;
	}
	
	public void registBleListener() {
		CushionBeanManager.getInstance().setBleDevDiscoverListener(this);
	}


	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.SmallRadarInterface.BleDeviceDiscoverListener#onDeviceDiscovered(android.bluetooth.BluetoothDevice)
	 */
	@Override
	public void onDeviceDiscovered(BluetoothDevice device) {
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registBleListener();
		initCallback();

	}
	
	/**
	 * 
	 */
	private void initCallback() {
		// TODO Auto-generated method stub
		CushionBeanManager.getInstance().setDeviceChangeListener(this);
		CushionBeanManager.getInstance().setmBtSwitchInterface(this);
		CushionBeanManager.getInstance().setmUpdateCurrectUser(this);
		CushionBeanManager.getInstance().setmUpdateCurrectData(this);
		CushionBeanManager.getInstance().setmUpdateHistoryData(this);
		
	}


	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.BleInterface.BtSwitchInterface#onBtSwitechOn()
	 */
	@Override
	public void onBtSwitechOn() {
		// TODO Auto-generated method stub
		if (openBtDialog != null && openBtDialog.isShowing()) {
			openBtDialog.cancel();
			openBtDialog = null;
		}
		BtIsOpen = true;
		CushionBeanApplication.getInstance().mBluetoothLeService.initialize();
	}


	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.BleInterface.BtSwitchInterface#onBtSwitechOff()
	 */
	@Override
	public void onBtSwitechOff() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessage(SHOW_OPEN_BT_DIALOG);
	}


	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.SmallRadarInterface.DeviceChangeListener#CushionBeanAdded(com.leixun.smartcushion.CushionBean)
	 */
	@Override
	public void CushionBeanAdded(CushionBean cushionBean) {
		// TODO Auto-generated method stub
		if(cushionBean.getDeviceInfoBean().getDeviceID() != null){
			CushionPreferences.getInstance(
					this).setCurrectConnectorCushion(cushionBean.getDeviceInfoBean().getDeviceID());		
		}

		
		cushionBean.SynchronousSystemTime();
		
		UserBean mUserBean = CushionBeanManager.getInstance().getmCurrectUserBean();
		if(mUserBean!=null){
			cushionBean.updateCurrectUserData(mUserBean);
			cushionBean.updateCurrectSettingData(mUserBean);
			cushionBean.GetHistoryWarming(mUserBean);			
		}else{
			Intent intent = new Intent();
			intent.setClass(BaseActivity2.this, EditUserNameActivity.class);
			startActivity(intent);
			finish();
		}
		

	}


	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.SmallRadarInterface.DeviceChangeListener#CushionBeanRemoved(com.leixun.smartcushion.CushionBean)
	 */
	@Override
	public void CushionBeanRemoved(CushionBean cushionBean) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.SmallRadarInterface.DeviceChangeListener#onResetBtSwitch()
	 */
	@Override
	public void onResetBtSwitch() {
		// TODO Auto-generated method stub
		
	}
	
	/**
 * 
 */
	public void showGpsDialog() {
		// TODO Auto-generated method stub
		final CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.gps_open_tips));
		builder.setTitleColor(getResources().getColor(
				R.color.listview_item_text_title_color));
		builder.setNegativeButton(
				getResources().getString(R.string.booluth_open),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						openGpsDialog = null;
						openGPS();
					}
				});

		builder.setPositiveButton(null, null);

		if (openGpsDialog != null && openGpsDialog.isShowing()) {

		} else {
			openGpsDialog = builder.customCreate();
			openGpsDialog.setCancelable(false);
			openGpsDialog.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					// TODO Auto-generated method stub

					return false;
				}
			});
			openGpsDialog.show();
		}
	}
	
	/**
 * 
 */
	private void showBtDialog() {
		// TODO Auto-generated method stub
		final CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.booluth_open_tips));
		builder.setTitleColor(getResources().getColor(
				R.color.listview_item_text_title_color));
		builder.setNegativeButton(
				getResources().getString(R.string.booluth_open),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						mBluetoothAdapter.enable();
						openBtDialog = null;
						if(!Tools.isLocationEnable(BaseActivity2.this)){
							if (Build.VERSION.SDK_INT >= 23) {
								showGpsDialog();
							}
						}else{
							
						}
					}
				});

		builder.setPositiveButton(null, null);

		if (openBtDialog != null && openBtDialog.isShowing()) {

		} else {
			openBtDialog = builder.customCreate();
			openBtDialog.setCancelable(false);
			openBtDialog.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					// TODO Auto-generated method stub

					return false;
				}
			});
			openBtDialog.show();
		}
	}
	/** 
     * 强制帮用户打开GPS 
     * @param context 
     */  
    public void openGPS( ) {  
		Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    this.startActivityForResult(locationIntent, REQUEST_CODE_LOCATION_SETTINGS);
    }
    
	/**
	 * 
	 */
	private void ShowBtTimeOut() {
		// TODO Auto-generated method stub
		if (timer != null) {
			timer.cancel();
		}
		initHandle();
		timer = new Timer(true);
		TimerTask task = new TimerTask() {
			public void run() {
				
				mHandler.sendEmptyMessage(SHOW_OPEN_BT_DIALOG);
				
			}
		};
		timer.schedule(task, 1000);
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (timer != null) {
			timer.cancel();
		}

	}

	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateCurrectUser#currectUserExist()
	 */
	@Override
	public void currectUserExist() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateCurrectUser#currectUserNotExist()
	 */
	@Override
	public void currectUserNotExist() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateCurrectData#UpdateCurrectDataInfo(com.leixun.smartcushion.Sdk.bean.Alarm, int, int)
	 */
	@Override
	public void UpdateCurrectDataInfo(Alarm alarm, int battery, int temperature) {
		// TODO Auto-generated method stub
		Message message = new Message();
		message.obj = alarm;
		message.what = SHOW_ALARM_DATA;
		mHandler.sendMessage(message);
		
	}

	/* (non-Javadoc)
	 * @see com.leixun.smartcushion.Sdk.Interface.UpdataDataInterface.UpdateHistoryData#UpdateHistoryDataInfo(com.leixun.smartcushion.Sdk.bean.ErrDataBean, int, int)
	 */
	@Override
	public void UpdateHistoryDataInfo(ErrDataBean errDataBean, int tootleCount,int currectCount) {
		// TODO Auto-generated method stub
		CushionBeanManager.getInstance().addErrDatas(tootleCount, currectCount, errDataBean);
		if(tootleCount==(currectCount+1)){			
			mHandler.sendEmptyMessage(HIDE_ERR_DATA_DIALOG);
		}
				
	}

	

	
}

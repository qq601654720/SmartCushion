/** * @author  作者: * @date 创建时间：2016年3月9日 下午4:54:07 * @version 1.0 * @parameter  * @since  * @return  */
package com.leixun.smartcushion.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.ab.fragment.AbFragment;
import com.leixun.smartcushion.R;

/**
 * @author chenjh
 * @date 2016-3-9 16:54
 * 
 */
public class DeviceStateControlPop extends PopupWindow {
	private View mMenuView;
	Button mBtnNormal;
	Button mBtnVehicle;
	Button mBtnMute;
	Button mBtnCancel;
	/**
	 * 昵称：设备状态popubwindow类
	 */
	public DeviceStateControlPop(Activity context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.device_state_pop_layout, null);
		initView(mMenuView);
		initListener(itemsOnClick);

		this.setContentView(mMenuView);

		this.setWidth(LayoutParams.FILL_PARENT);

		this.setHeight(LayoutParams.WRAP_CONTENT);

		this.setFocusable(true);

		this.setAnimationStyle(R.style.AnimBottom);

		ColorDrawable dw = new ColorDrawable(0x66000000);

		this.setBackgroundDrawable(dw);

		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.pop_check_lay).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}
	
	/**
	 * 
	 */
	private void initView(View view) {
		// TODO Auto-generated method stub
		mBtnNormal = (Button) view.findViewById(R.id.btn_device_state_normal);
		mBtnVehicle = (Button) view.findViewById(R.id.btn_device_state_vehicle);
		mBtnMute = (Button) view.findViewById(R.id.btn_device_state_mute);		
		mBtnCancel = (Button) view.findViewById(R.id.btn_device_state_cancel);
	}
	
	/**
	 * 
	 */
	private void initListener(OnClickListener l) {
		// TODO Auto-generated method stub
		mBtnNormal.setOnClickListener(l);
		mBtnVehicle.setOnClickListener(l);
		mBtnMute.setOnClickListener(l);
		mBtnCancel.setOnClickListener(l);
	}

}

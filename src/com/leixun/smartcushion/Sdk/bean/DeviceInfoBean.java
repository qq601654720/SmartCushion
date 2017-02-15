package com.leixun.smartcushion.Sdk.bean;

import android.bluetooth.BluetoothDevice;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

@Table(name = "DeviceInfoBean")
public class DeviceInfoBean {

	// ID @Id主键,int类型,数据库建表时此字段会设为自增长
	@Id
	@Column(name = "_id")
	private int _id;
	// 设备唯一标识
	@Column(name = "DeviceID")
	private String DeviceID = "0000000000";

	// 房间 ID
	@Column(name = "RoomID", length = 256)
	private String RoomID;

	// 设备名称， 用户可自定
	@Column(name = "DeviceName", length = 256)
	private String DeviceName = "";
	
	private boolean isFound = false;
	
	private BluetoothDevice bluetoothDevice;

	/**
	 * @return the _id
	 */
	public int get_id() {
		return _id;
	}

	/**
	 * @param _id
	 *            the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
	}

	/**
	 * @return the deviceID
	 */
	public String getDeviceID() {
		return DeviceID;
	}

	/**
	 * @param deviceID
	 *            the deviceID to set
	 */
	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}

	/**
	 * @return the roomID
	 */
	public String getRoomID() {
		return RoomID;
	}

	/**
	 * @param roomID
	 *            the roomID to set
	 */
	public void setRoomID(String roomID) {
		RoomID = roomID;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return DeviceName;
	}

	/**
	 * @param deviceName
	 *            the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		DeviceName = deviceName;
	}

	/**
	 * @return the isFound
	 */
	public boolean isFound() {
		return isFound;
	}

	/**
	 * @param isFound the isFound to set
	 */
	public void setFound(boolean isFound) {
		this.isFound = isFound;
	}

	/**
	 * @return the bluetoothDevice
	 */
	public BluetoothDevice getBluetoothDevice() {
		return bluetoothDevice;
	}

	/**
	 * @param bluetoothDevice the bluetoothDevice to set
	 */
	public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
		this.bluetoothDevice = bluetoothDevice;
	}

	// // 包含实体的存储，指定外键
	// @Relations(name = "stock", type = "one2one", foreignKey = "u_id", action
	// = "query_insert")
	// private Stock stock;
	//
	// // 包含List的存储，指定外键
	// @Relations(name = "stocks", type = "one2many", foreignKey = "u_id",
	// action = "query_insert")
	// private List<Stock> stocks;
	
	

}

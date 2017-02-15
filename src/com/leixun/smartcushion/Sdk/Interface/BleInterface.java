package com.leixun.smartcushion.Sdk.Interface;

public class BleInterface {
	public interface ScanBleInterface {
		public void onregistBleListener();

		public void onstartScanLeDevice();
	}

	public interface BtSwitchInterface {
		public void onBtSwitechOn();

		public void onBtSwitechOff();
	}
}
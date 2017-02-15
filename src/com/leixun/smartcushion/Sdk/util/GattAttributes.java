/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leixun.smartcushion.Sdk.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

import android.util.Log;

/**
 * This class includes a small subset of standard GATT attributes for
 * demonstration purposes.
 */
public class GattAttributes {
	private static HashMap<String, String> attributes = new HashMap<String, String>();
	private static HashMap<String, String> attributesAllName = new HashMap<String, String>();
	public static final UUID CUSHIONBEAN_SERVICE_UUID = UUID
			.fromString("6e400101-B5A3-F393-E0A9-E50E24DCCAA1");
//  public static final UUID TX_POWER_UUID = UUID.fromString("00001804-0000-1000-8000-00805f9b34fb");
  public static final UUID CCCD = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
//  public static final UUID FIRMWARE_REVISON_UUID = UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb");
//  public static final UUID DIS_UUID = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb");
  public static final UUID RX_SERVICE_UUID = UUID.fromString("6e400101-b5a3-f393-e0a9-e50e24dccaa1");
  public static final UUID RX_CHAR_UUID = UUID.fromString("6e400202-b5a3-f393-e0a9-e50e24dccaa1");
  public static final UUID TX_CHAR_UUID = UUID.fromString("6e400303-b5a3-f393-e0a9-e50e24dccaa1");
	public static String CUSHIONBEAN_BLE_SERVICE_NOTIFY_DESCRIPTOR = "00002902-0000-1000-8000-00805f9b34fb";
	public static UUID[] CUSHIONBEAN_NOTIFY_LIST = new UUID[] {TX_CHAR_UUID };

	public static String lookup(String uuid, String defaultName) {
		Log.d("zzll", "uuid = " + uuid);
		String name = attributes.get(uuid);
		if (name == null) {
			uuid = uuid.substring(4, 8).toUpperCase(Locale.getDefault());
			name = attributesAllName.get(uuid);
			if (name != null)
				Log.d("zzll", "name = " + name);
		} else {
			return name;
		}
		return name == null ? defaultName : name;
	}
}

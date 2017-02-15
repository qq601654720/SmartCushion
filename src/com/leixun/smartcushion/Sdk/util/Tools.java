package com.leixun.smartcushion.Sdk.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * 正则表达式判断
 * 
 * @author 409165
 */
public class Tools {
	


	/**
	 * 验证手机号
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^[1][3-8]\\d{9}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */

	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static List<String> YearList() {
		List<String> years = new ArrayList<String>();
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);// 获取年份
		for (int i = 0; i <= year - 1970; i++) {
			years.add(String.valueOf(i + 1970));
		}
		return years;
	}

	public static List<String> MonthList() {
		List<String> months = new ArrayList<String>();

		for (int i = 1; i <= 12; i++) {
			if (String.valueOf(i).length() == 1) {
				months.add("0" + String.valueOf(i));
			} else {
				months.add(String.valueOf(i));
			}

		}
		return months;
	}

	public static List<String> HoursList() {
		List<String> Hours = new ArrayList<String>();

		for (int i = 1; i <= 24; i++) {
			if (String.valueOf(i).length() == 1) {
				Hours.add("0" + String.valueOf(i));
			} else {
				Hours.add(String.valueOf(i));
			}

		}
		return Hours;
	}

	public static List<String> MinutesList() {
		List<String> Minutes = new ArrayList<String>();

		for (int i = 0; i <= 59; i++) {
			if (String.valueOf(i).length() == 1) {
				Minutes.add("0" + String.valueOf(i));
			} else {
				Minutes.add(String.valueOf(i));
			}

		}
		return Minutes;
	}

	/**
	 * 
	 * @Title: getWeekOfDate
	 * @Description: TODO
	 * @param @param dt
	 * @param @return
	 * @return String
	 * @exception/throws description
	 */
	public static int getWeekOfDate(Date dt) {
		String[] weekDays = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 2;
		if (w < 0)
			w = 6;
		return w;
	}

	/**
	 * 
	 * @Title: getWeekofToday
	 * @Description: TODO
	 * @param @return
	 * @return int
	 * @exception/throws description
	 */
	public static int getWeekofToday() {
		Date date = new Date();
		SimpleDateFormat dateFm = new SimpleDateFormat("EEEE",
				Locale.getDefault());
		dateFm.format(date);

		return getWeekOfDate(date);
	}

	/**
	 * 
	 * @return
	 */
	public static Calendar getCalendar() {
		Calendar c = java.util.Calendar.getInstance();

		return c;
	}

	/**
	 * 
	 * @Title: getApplicationVersion
	 * @Description: TODO
	 * @param @return
	 * @return String
	 * @exception/throws description
	 */
	public static String getApplicationVersion() {
		String ver = null;
		PackageManager manager;
		PackageInfo info = null;
		manager = ResourceManager.getInstance().getApplicationContext()
				.getPackageManager();
		try {
			info = manager.getPackageInfo(ResourceManager.getInstance()
					.getApplicationContext().getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info.versionName;
	}

	public static String getImei(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String DEVICE_ID = tm.getDeviceId();
		return DEVICE_ID;
	}

	/**
	 * 
	 * @Title: getScreenWH
	 * @Description: TODO
	 * @param @param context
	 * @return void
	 * @exception/throws description
	 */
	public static DisplayMetrics getScreenWH() {
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		WindowManager wm = (WindowManager) ResourceManager.getInstance()
				.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);

		wm.getDefaultDisplay().getMetrics(dm);

		return dm;
	}

	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}

	public static boolean isNetworkAvailable(Context ctx) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		} else {
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					// System.out.println(i + "===状态===" +
					// networkInfo[i].getState());
					// System.out.println(i + "===类型===" +
					// networkInfo[i].getTypeName());
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// public static final boolean ping() {
	//
	// String result = null;
	// try {
	// String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
	// Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);//
	// ping网址3次
	// // 读取ping的内容，可以不加
	// InputStream input = p.getInputStream();
	// BufferedReader in = new BufferedReader(new InputStreamReader(input));
	// StringBuffer stringBuffer = new StringBuffer();
	// String content = "";
	// while ((content = in.readLine()) != null) {
	// stringBuffer.append(content);
	// }
	// Log.d("------ping-----",
	// "result content : " + stringBuffer.toString());
	// // ping的状态
	// int status = p.waitFor();
	// if (status == 0) {
	// result = "success";
	// return true;
	// } else {
	// result = "failed";
	// }
	// } catch (IOException e) {
	// result = "IOException";
	// } catch (InterruptedException e) {
	// result = "InterruptedException";
	// } finally {
	// Log.d("----result---", "result = " + result);
	// }
	// return false;
	// }

	public static Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		// if( baos.toByteArray().length / 1024>1024)
		// {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
		// baos.reset();//重置baos即清空baos
		// image.compress(Bitmap.CompressFormat.JPEG, 50,
		// baos);//这里压缩50%，把压缩后的数据存放到baos中
		// }
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	private static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	public static Uri getImageURI(String path, File cache) {
		String name = path.substring(path.lastIndexOf("/"));
		name = name.substring(0, name.indexOf(".")) + ".jpg";
		// name = name.substring(0, name.indexOf("."));
		File file = new File(cache, name);
		// 如果图片存在本地缓存目录，则不去服务器下载
		if (file.exists()) {
			return Uri.fromFile(file);// Uri.fromFile(path)这个方法能得到文件的URI
		} else {
			// 从网络上获取图片
			try {
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				if (conn.getResponseCode() == 200) {

					InputStream is = conn.getInputStream();
					FileOutputStream fos = new FileOutputStream(file);
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}
					is.close();
					fos.close();

					// 返回一个URI对象
					return Uri.fromFile(file);
				}
			} catch (Exception e) {
				if (file.exists()) {
					file.delete();
				}
			}
		}
		return null;
	}

	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				/*
				 * BACKGROUND=400 EMPTY=500 FOREGROUND=100 GONE=1000
				 * PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
				 */
				Log.i(context.getPackageName(), "此appimportace ="
						+ appProcess.importance
						+ ",context.getClass().getName()="
						+ context.getClass().getName());
				if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					Log.i(context.getPackageName(), "处于后台"
							+ appProcess.processName);
					return true;
				} else {
					Log.i(context.getPackageName(), "处于前台"
							+ appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * Location service if enable
	 *
	 * @param context
	 * @return location is enable if return true, otherwise disable.
	 */
	public static final boolean isLocationEnable(Context context) {
	    LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	    boolean networkProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	    boolean gpsProvider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    if (networkProvider|gpsProvider) 
	    return true;
	    return false;
	}
	

}

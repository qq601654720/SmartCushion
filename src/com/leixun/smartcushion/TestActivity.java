package com.leixun.smartcushion;

import java.util.Calendar;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class TestActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_main);
		
//		ErrDataBean errDataBean = new ErrDataBean();
//		errDataBean.setErrTime("1487211610");
//		ErrDataBean errDataBean1 = new ErrDataBean();
//		errDataBean1.setErrTime("1487211611");
//		ErrDataBean errDataBean2 = new ErrDataBean();
//		errDataBean2.setErrTime("1487211612");
//		DbManger.getInstance(this).saveDbErrData(errDataBean);
//		DbManger.getInstance(this).saveDbErrData(errDataBean1);
//		DbManger.getInstance(this).saveDbErrData(errDataBean2);
//		
//		int count = DbManger.getInstance(this).queryErrDataCount("1487211619", "1487211610");
//		Toast.makeText(this,"错误历史数据次数==="+count, 100).show();
		Calendar curr = Calendar.getInstance();
		Toast.makeText(this,"当前时间==="+curr.getTimeInMillis()/1000, 100).show();
		Log.e("当前时间===", curr.getTimeInMillis()/1000+"");
		curr.set(Calendar.DAY_OF_MONTH,curr.get(Calendar.DAY_OF_MONTH)-7);
		long date=curr.getTimeInMillis()/1000;
		Log.e("一周前时间===", date+"");
		Toast.makeText(this,"一周前时间==="+date, 100).show();
	}
	
}

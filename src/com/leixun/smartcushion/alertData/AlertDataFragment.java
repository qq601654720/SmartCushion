package com.leixun.smartcushion.alertData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ab.fragment.AbFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.leixun.smartcushion.R;
import com.leixun.smartcushion.Sdk.CushionBeanManager;
import com.leixun.smartcushion.Sdk.bean.ErrDataBean;

public class AlertDataFragment extends AbFragment
		 {

	public static String TAG = "AirDetectionChartDayFragment";

	private View mRootView;

	public LineChart lineChart ;
	public ArrayList<String> x = new ArrayList<String>();
	public ArrayList<String> time_x = new ArrayList<String>();
	public ArrayList<Entry> y_pm25 = new ArrayList<Entry>();
	public ArrayList<ILineDataSet> linePm25DataSets = new ArrayList<ILineDataSet>();
	
	public LineData linePM25Data = null;
	private List<ErrDataBean> mhistoryDatas = new LinkedList<ErrDataBean>();
	private boolean beforeDay = false;

	@Override
	public View onCreateContentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.chart_fragment,
				container, false);
		initView();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// 显示内容
				showContentView();
			}

		}, 0000);
		// item被点击事件
		return mRootView;
	}

	/**
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		lineChart = (LineChart) mRootView
				.findViewById(R.id.spread_line_chart2);
//		testData();
	}

	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
		initChart();
		initPm25LineChar();


	}

	@Override
	public void setResource() {
		// 设置加载的资源
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private void initPm25LineChar() {
		// TODO Auto-generated method stub
		LineDataSet lineDataSet = new LineDataSet(y_pm25, "");// y轴数据集合
		// lineDataSet.set
		lineDataSet.setLineWidth(1f);// 线宽
		lineDataSet.setCircleSize(6f);// 现实圆形大小
		lineDataSet.setColor(getResources().getColor(
				R.color.line_chart_line_color));// 现实颜色
		lineDataSet
				.setCircleColor(getResources().getColor(R.color.transparent));// 圆形颜色
		lineDataSet.setHighLightColor(Color.YELLOW);// 高度线的颜色
		lineDataSet.setCircleColorHole(getResources().getColor(
				R.color.line_chart_tab_text_color_normal));
		lineDataSet.setValueTextColor(getResources().getColor(
				R.color.line_chart_text_color));
		lineDataSet.setValueTextSize(8f);
		lineDataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
		linePm25DataSets.clear();
		linePm25DataSets.add(lineDataSet);
		linePM25Data = new LineData(x, linePm25DataSets);
	}

	

	/**
	 * 设置样式
	 */
	private void initChart() {
			lineChart.clear();
			lineChart.setDrawBorders(false);// 是否添加边框
			lineChart.setDescription("");// 数据描述
			lineChart.setNoDataTextDescription("我需要数据");// 没数据显示
			lineChart.setDrawGridBackground(true);// 是否显示表格颜色
			lineChart.setBackgroundColor(Color.TRANSPARENT);// 背景颜色
			lineChart.setGridBackgroundColor(getResources().getColor(R.color.alert_background));
			lineChart.setDoubleTapToZoomEnabled(false);
			lineChart.setTouchEnabled(false);
			lineChart.setDrawMarkerViews(false);

//			lineChart.animateX(750);
			Legend legend = lineChart.getLegend();// 设置比例图片标示，就是那一组Y的value
			legend.setForm(Legend.LegendForm.CIRCLE);// 样式
			legend.setFormSize(6f);// 字体
			legend.setTextColor(getResources().getColor(
					R.color.line_chart_text_color));// 设置颜色G
			legend.setEnabled(false);
			lineChart.animateX(1500);// X轴的动画
			YAxis rightAxis = lineChart.getAxisRight();
			YAxis leftAxis = lineChart.getAxisLeft();
			XAxis xAxis = lineChart.getXAxis();
			xAxis.setDrawGridLines(false);
			xAxis.setTextSize(12);
			xAxis.setXOffset(0f);
			leftAxis.setEnabled(false);
			xAxis.setDrawAxisLine(true);
			xAxis.setAxisLineColor(Color.WHITE);
			xAxis.setAxisLineWidth(1);
			xAxis.setSpaceBetweenLabels(0);
			xAxis.setTextColor(getResources().getColor(
					R.color.line_chart_text_color));
			xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
			// BOTTOM.
			rightAxis.setEnabled(false);
			// lineChart.set
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			updataCharData();

		}

	};

	/**
	 * 
	 */
	private void updatePm25Data(List<ErrDataBean> historyDatas) {
		// TODO Auto-generated method stub
		for(int i = 0;i<mhistoryDatas.size();i++){
			x.add(historyDatas.get(i).getErrTime());
			time_x.add(historyDatas.get(i).getErrTime());				
			y_pm25.add(new Entry(historyDatas.get(i).getErrCount(), i));
		}
	
	}

	
	/**
	 * 
	 */
	private void updataCharData() {
		// TODO Auto-generated method stub
		initData();
		lineChart.setData(linePM25Data);

	}

	/**
	 * 
	 */
	public void UpdateHistory(List<ErrDataBean> historyDatas) {
		// TODO Auto-generated method stub
		updatePm25Data(historyDatas);
		mHandler.sendEmptyMessage(1);
	}
	
//	/**
//	 * 
//	 */
//	private void testData() {
//		// TODO Auto-generated method stub
//		String date1 = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(1483692810 * 1000));
//		ErrDataBean errDataBean1 = new ErrDataBean();
//		errDataBean1.setErrTime(date1);
//		errDataBean1.setErrCount(10);
//		String date2 = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(1483693810 * 1000));
//
//		ErrDataBean errDataBean2 = new ErrDataBean();
//		errDataBean2.setErrTime(date2);
//		errDataBean2.setErrCount(20);
//		String date3 = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(1483694810 * 1000));
//
//		ErrDataBean errDataBean3 = new ErrDataBean();
//		errDataBean3.setErrTime(date3);
//		errDataBean3.setErrCount(11);
//		String date4 = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(1483695810 * 1000));
//
//		ErrDataBean errDataBean4 = new ErrDataBean();
//		errDataBean4.setErrTime(date4);
//		errDataBean4.setErrCount(13);
//		String date5 = new SimpleDateFormat("HH:mm").format(new java.util.Date(1483696810 * 1000));
//
//		ErrDataBean errDataBean5 = new ErrDataBean();
//		errDataBean5.setErrTime(date5);
//		errDataBean5.setErrCount(9);
//		
//		String date6 = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(1483697810 * 1000));
//		ErrDataBean errDataBean6 = new ErrDataBean();
//		errDataBean6.setErrTime(date6);
//		errDataBean6.setErrCount(6);
//		
//		mhistoryDatas.add(errDataBean1);
//		mhistoryDatas.add(errDataBean2);
//		mhistoryDatas.add(errDataBean3);
//		mhistoryDatas.add(errDataBean4);
//		mhistoryDatas.add(errDataBean5);
//		mhistoryDatas.add(errDataBean6);
//		
//		UpdateHistory(mhistoryDatas);
//	}
	
	
	/**
	 * 
	 */
	public void RefreshErrDataList() {
		// TODO Auto-generated method stub
		mhistoryDatas = CushionBeanManager.getInstance().getErrDatas();
		UpdateHistory(mhistoryDatas);
		
	}
	
	
}

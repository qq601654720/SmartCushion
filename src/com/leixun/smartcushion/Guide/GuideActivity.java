package com.leixun.smartcushion.Guide;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.leixun.smartcushion.MainActivity;
import com.leixun.smartcushion.R;
import com.leixun.smartcushion.Sdk.perference.XMLPreferences;

public class GuideActivity extends Activity implements OnPageChangeListener{
	    private ViewPager vp;
	    private ViewPagerAdapter vpAdapter;
	    private List<View> views;

	    // 底部小点图片
	    private ImageView[] dots;

	    // 记录当前选中位置
	    private int currentIndex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_activity);
//		if (XMLPreferences.getInstance(this).getIsFirstStartApplication(false)) {
//			Intent intent = new Intent();
//			intent.setClass(this,MainActivity.class);
//			intent.putExtra("FIRST_TIME_START", true);
//	        startActivity(intent);
//	        finish();
//	        return;
//		}
	    // 初始化页面
        initViews();
        // 初始化底部小点
        initDots();
	}
	  private void initViews() {
	        LayoutInflater inflater = LayoutInflater.from(this);

	        views = new ArrayList<View>();
	        // 初始化引导图片列表
	        views.add(inflater.inflate(R.layout.guide_image_one, null));
	        views.add(inflater.inflate(R.layout.guide_image_two, null));
			views.add(inflater.inflate(R.layout.guide_image_three, null));
	        // 初始化Adapter
	        vpAdapter = new ViewPagerAdapter(views, this);

	        vp = (ViewPager) findViewById(R.id.viewpager);
	        vp.setAdapter(vpAdapter);
	        // 绑定回调
	        vp.setOnPageChangeListener(this);
	    }

	    private void initDots() {
	        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

	        dots = new ImageView[views.size()];

	        // 循环取得小点图片
	        for (int i = 0; i < views.size(); i++) {
	            dots[i] = (ImageView) ll.getChildAt(i);
	            dots[i].setVisibility(View.VISIBLE); 
	            dots[i].setEnabled(true);// 都设为灰色
	        }

	        currentIndex = 0;
	        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	    }

	    private void setCurrentDot(int position) {
	        if (position < 0 || position > views.size() - 1
	                || currentIndex == position) {
	            return;
	        }

	        dots[position].setEnabled(false);
	        dots[currentIndex].setEnabled(true);

	        currentIndex = position;
	    }
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		 setCurrentDot(arg0);
	}

}

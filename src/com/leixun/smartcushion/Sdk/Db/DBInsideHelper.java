package com.leixun.smartcushion.Sdk.Db;

import android.content.Context;

import com.ab.db.orm.AbDBHelper;
import com.leixun.smartcushion.Sdk.Db.Dao.ErrDataBeanDao;
import com.leixun.smartcushion.Sdk.bean.ErrDataBean;
import com.leixun.smartcushion.Sdk.bean.UserBean;
/**
 * © 2016 amsoft.cn
 * @名称：DBInsideHelper.java 
 * @描述：本地数据库 在data下面
 * @author 姚海军
 * @date：2016-3-12 下午4:12:36
 * @versi
 */
public class DBInsideHelper extends AbDBHelper {
	// 数据库名
	private static final String DBNAME = "cushion.db";
    
    // 当前数据库的版本
	private static final int DBVERSION = 2;
	// 要初始化的表
	private static final Class<?>[] clazz = {UserBean.class,ErrDataBean.class};
	public DBInsideHelper(Context context) {
		super(context, DBNAME, null, DBVERSION, clazz);
	}

}




package com.leixun.smartcushion.Sdk.Db.Dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.leixun.smartcushion.Sdk.Db.DBInsideHelper;
import com.leixun.smartcushion.Sdk.bean.ErrDataBean;
/**
 * © 2016 amsoft.cn
 * @名称：DeviceInsideDao.java 
 * @描述：本地数据库 在data下面
 * @author 姚海军
 * @date：2016-3-12 下午4:12:36
 * @version v1.0
 */
public class ErrDataBeanDao extends AbDBDaoImpl<ErrDataBean> {
	public ErrDataBeanDao(Context context) {
		super(new DBInsideHelper(context),ErrDataBean.class);
	}
}

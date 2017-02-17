/**   
 * @Title:DbManger.java
 * @Package com.huawei.smallRadar.Sdk.Db
 * @Description: 
 * @author 姚海军  
 * @date 2016年3月14日上午9:29:04
 * @version V1.0   
 * History :
 *  1. Yaohaijun add for the first release ,2016年3月14日  
 *
 * 
 * Copyright (C), Tonly electronics Holdincs Limited
 * All rights reserved
 ******************************************************************************/
package com.leixun.smartcushion.Sdk.Db;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.ab.util.AbLogUtil;
import com.leixun.smartcushion.Sdk.Db.Dao.ErrDataBeanDao;
import com.leixun.smartcushion.Sdk.Db.Dao.UserInsideDao;
import com.leixun.smartcushion.Sdk.bean.ErrDataBean;
import com.leixun.smartcushion.Sdk.bean.UserBean;
import com.leixun.smartcushion.Sdk.util.L;

/**
 * @author 姚海军
 *
 */
public class DbManger {
	// 定义数据库操作实现类
	private ErrDataBeanDao mErrDataBeanDao = null;
	// 定义数据库操作实现类
	private UserInsideDao mUserInsideDao = null;
	// 数据库操作类
	// private AbSqliteStorage mAbSqliteStorage = null;
	private static DbManger mDbManger;
	private int totalCount = 0;
	private boolean del_ok = false;
	List<UserBean> UserInfoBeans = new LinkedList<UserBean>();

	/**
	 * 
	 */
	private DbManger(Context context) {
		// TODO Auto-generated constructor stub
		mUserInsideDao = new UserInsideDao(context);
		mErrDataBeanDao = new ErrDataBeanDao(context);
		// 初始化AbSqliteStorage
		// mAbSqliteStorage = AbSqliteStorage.getInstance(context);
	}

	/**
	 * 
	 */
	public static DbManger getInstance(Context context) {
		synchronized (DbManger.class) {
			if (mDbManger == null)
				mDbManger = new DbManger(context);
		}
		return mDbManger;
		// TODO Auto-generated method stub

	}

	// 保存错误历史数据
	public void saveDbErrData(ErrDataBean errData) {
		synchronized (DbManger.class) {
			mErrDataBeanDao.startWritableDatabase(false);
			// (2)执行查询
			mErrDataBeanDao.insert(errData);
			// (3)关闭数据库
			mErrDataBeanDao.closeDatabase();
		}
	}

	// 保存用户信息
	public void saveDbUserInfoData(UserBean UserInfoBean) {
		synchronized (DbManger.class) {
			mUserInsideDao.startWritableDatabase(false);
			// (2)执行查询
			mUserInsideDao.insert(UserInfoBean);
			// (3)关闭数据库
			mUserInsideDao.closeDatabase();
		}
	}

	/**
	 * 
	 * 描述：查询数据
	 * 
	 * @throws
	 */
	public List<UserBean> queryDbUserInfoData() {
		// (1)获取数据库
		synchronized (DbManger.class) {
			mUserInsideDao.startReadableDatabase();
			// (2)执行查询
			List<UserBean> UserInfoBeans = mUserInsideDao.queryList();
			// (3)关闭数据库
			mUserInsideDao.closeDatabase();
			return UserInfoBeans;
		}
	}

	/**
	 * 
	 * 描述：查询数据
	 * 
	 * @throws
	 */
	public List<UserBean> queryData(String UserID) {
		synchronized (DbManger.class) {
			// (1)获取数据库
			mUserInsideDao.startReadableDatabase();
			// (2)执行查询
			List<UserBean> userListNew = new LinkedList<UserBean>();
			userListNew = mUserInsideDao.queryList(null, "userId=?",
					new String[] { UserID }, null, null, null, null);
			// (3)关闭数据库
			mUserInsideDao.closeDatabase();
			if (userListNew.size() > 0)
				return userListNew;
			else
				return new LinkedList<UserBean>();
		}
	}

	/**
	 * 
	 * 描述：查询历史錯誤数据区间数量
	 * 
	 * @throws
	 */
	public List<ErrDataBean> queryErrDatas(String errDataID1, String errDataID2) {
		synchronized (DbManger.class) {
			// (1)获取数据库
			mErrDataBeanDao.startReadableDatabase();
			// (2)执行查询
			List<ErrDataBean> errDataBeans = new LinkedList<ErrDataBean>();
			errDataBeans = mErrDataBeanDao.queryList(null,
					"errTime > ? and errTime < ? ", new String[] { errDataID1,
							errDataID2 }, null, null, null, null);

			// (3)关闭数据库
			mErrDataBeanDao.closeDatabase();

			if (errDataBeans.size() > 0)
				return errDataBeans;
			else
				return new LinkedList<ErrDataBean>();
		}
	}

	/**
	 * 
	 * 描述：查询历史錯誤数据
	 * 
	 * @throws
	 */
	public int queryErrDataCount() {
		synchronized (DbManger.class) {
			// (1)获取数据库
			mErrDataBeanDao.startReadableDatabase();
			// (2)执行查询

			int count = mErrDataBeanDao.queryCount(null, null);

			// (3)关闭数据库
			mErrDataBeanDao.closeDatabase();

			return count;
		}
	}
	
	/**
	 * 
	 * 描述：查询历史錯誤数据
	 * 
	 * @throws
	 */
	public List<ErrDataBean> queryErrDataAll() {
		synchronized (DbManger.class) {
			// (1)获取数据库
			mErrDataBeanDao.startReadableDatabase();
			// (2)执行查询
			List<ErrDataBean> errDataBeans = new LinkedList<ErrDataBean>();

			errDataBeans = mErrDataBeanDao.queryList();

			// (3)关闭数据库
			mErrDataBeanDao.closeDatabase();

			return errDataBeans;
		}
	}

	private String getLogSql(String sql, Object[] args) {
		if (args == null || args.length == 0) {
			return sql;
		}
		for (int i = 0; i < args.length; i++) {
			sql = sql.replaceFirst("\\?", "'" + String.valueOf(args[i]) + "'");
		}
		return sql;
	}

	/**
	 * 
	 * 描述：查询数量
	 * 
	 * @throws
	 */
	public int queryDbUserInfoDataCount() {
		synchronized (DbManger.class) {
			// (1)获取数据库
			mUserInsideDao.startReadableDatabase();
			// (2)执行查询
			int totalCount = mUserInsideDao.queryCount(null, null);
			// (3)关闭数据库
			mUserInsideDao.closeDatabase();
			return totalCount;
			// 查询数据
		}
	}

	/**
	 * 更新数据 描述：TODO
	 * 
	 * @param u
	 */
	public void updateDbUserInfoData(UserBean u) {
		// (1)获取数据库
		synchronized (DbManger.class) {
			mUserInsideDao.startWritableDatabase(false);
			mUserInsideDao.update(u);
			mUserInsideDao.closeDatabase();
		}
	}

	/**
	 * 
	 * 描述：根据ID查询数据
	 * 
	 * @param id
	 * @return
	 */
	public UserBean queryDbUserInfoDataById(int id) {
		synchronized (DbManger.class) {
			// (1)获取数据库
			mUserInsideDao.startReadableDatabase();
			UserBean u = (UserBean) mUserInsideDao.queryOne(id);
			mUserInsideDao.closeDatabase();
			return u;
		}
	}

	/**
	 * 
	 * 描述：删除数据
	 * 
	 * @param id
	 */
	public void delDbUserInfoData(int id) {
		synchronized (DbManger.class) {
			// (1)获取数据库
			mUserInsideDao.startWritableDatabase(false);
			// (2)执行查询
			mUserInsideDao.delete(id);
			// (3)关闭数据库
			mUserInsideDao.closeDatabase();

			queryDbUserInfoData();
		}
	}
}

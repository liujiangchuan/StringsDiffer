package com.ll.diffstringxml.db;

import com.ll.diffstringxml.MyApp;
import com.ll.diffstringxml.db.framework.DaoMaster;
import com.ll.diffstringxml.db.framework.DaoSession;


/**
 * Created by Liujc on 2016/8/17.
 * Email: liujiangchuan@hotmail.com
 */
public class DaoCreator
{
	private static final String PATH = "database.db";

	private static class InnerInstance
	{
		private static DaoMaster daoMaster;
		private static DaoSession daoSession;

		static
		{
			DaoMaster.OpenHelper
					openHelper = new DaoMaster.DevOpenHelper(MyApp.getAppContext(), PATH, null);
			daoMaster = new DaoMaster(openHelper.getWritableDatabase());
			daoSession = daoMaster.newSession();
		}
	}

	public static DaoMaster getDaoMaster()
	{
		return InnerInstance.daoMaster;
	}

	public static DaoSession getDaoSession()
	{
		return InnerInstance.daoSession;
	}

	public static void close()
	{
		if (InnerInstance.daoSession != null)
		{
			InnerInstance.daoSession = null;
		}
		if (InnerInstance.daoMaster != null)
		{
			if (InnerInstance.daoMaster.getDatabase().isOpen())
			{
				InnerInstance.daoMaster.getDatabase().close();
			}
			InnerInstance.daoMaster = null;
		}
	}
}

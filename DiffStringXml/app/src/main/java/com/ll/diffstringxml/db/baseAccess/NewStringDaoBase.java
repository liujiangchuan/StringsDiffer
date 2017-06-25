package com.ll.diffstringxml.db.baseAccess;


import com.ll.diffstringxml.db.DaoCreator;
import com.ll.diffstringxml.db.framework.NewString;
import com.ll.diffstringxml.db.framework.NewStringDao;

import java.util.List;

public class NewStringDaoBase
{
	private NewStringDao mNewStringDao;

	private static class SingletonClassInstance
	{
		private static final NewStringDaoBase self = new NewStringDaoBase();
	}

	public static NewStringDaoBase getInstance()
	{
		return SingletonClassInstance.self;
	}

	private NewStringDaoBase()
	{
		mNewStringDao = DaoCreator.getDaoSession().getNewStringDao();
	}

	//-------------------------------------------------------     Retrieve     -----------------------------------------------------------------

	//-------------------------------------------------------     Create/Update     -----------------------------------------------------------------
	/**
	 * Insert list in transaction.
	 * @param newStringList
	 */
	public void insertInTx(List<NewString> newStringList)
	{
		try
		{
			mNewStringDao.insertInTx(newStringList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//-------------------------------------------------------     Delete     -----------------------------------------------------------------
	/**
	 * Delete all.
	 */
	public void deleteAll()
	{
		try
		{
			mNewStringDao.deleteAll();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

package com.ll.diffstringxml.db.baseAccess;


import com.ll.diffstringxml.db.DaoCreator;
import com.ll.diffstringxml.db.framework.OldString;
import com.ll.diffstringxml.db.framework.OldStringDao;

import java.util.List;

public class OldStringDaoBase
{
	private OldStringDao mOldStringDao;

	private static class SingletonClassInstance
	{
		private static final OldStringDaoBase self = new OldStringDaoBase();
	}

	public static OldStringDaoBase getInstance()
	{
		return SingletonClassInstance.self;
	}

	private OldStringDaoBase()
	{
		mOldStringDao = DaoCreator.getDaoSession().getOldStringDao();
	}

	//-------------------------------------------------------     Retrieve     -----------------------------------------------------------------

	//-------------------------------------------------------     Create/Update     -----------------------------------------------------------------
	/**
	 * Insert list in transaction.
	 * @param oldStringList
	 */
	public void insertInTx(List<OldString> oldStringList)
	{
		try
		{
			mOldStringDao.insertInTx(oldStringList);
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
			mOldStringDao.deleteAll();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

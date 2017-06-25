package com.ll.diffstringxml.db.baseAccess;


import android.database.Cursor;

import com.ll.diffstringxml.db.DaoCreator;
import com.ll.diffstringxml.db.framework.OldStringDao;
import com.ll.diffstringxml.db.view.JoinViewBean;
import com.ll.diffstringxml.db.view.ViewCreator;

import java.util.ArrayList;
import java.util.List;

public class JoinViewDaoBase
{
    private OldStringDao mOldStringDao;

    private static class SingletonClassInstance
    {
        private static final JoinViewDaoBase self = new JoinViewDaoBase();
    }

    public static JoinViewDaoBase getInstance()
    {
        return SingletonClassInstance.self;
    }

    private JoinViewDaoBase()
    {
        mOldStringDao = DaoCreator.getDaoSession().getOldStringDao();
    }

    //-------------------------------------------------------     Retrieve     -----------------------------------------------------------------

    /**
     * Get list.
     *
     * @return List
     */
    public List<JoinViewBean> queryList()
    {
        List<JoinViewBean> joinViewBeanList = new ArrayList<>();
        Cursor cursor = DaoCreator.getDaoSession().getDatabase()
                .query(ViewCreator.V_JOIN, null, null, null, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                JoinViewBean joinViewBean = new JoinViewBean();
                String oldKey = cursor.getString(0);
                String oldValue = cursor.getString(1);
                String newKey = cursor.getString(2);
                String newValue = cursor.getString(3);
                joinViewBean.mOldString.setKey(oldKey);
                joinViewBean.mOldString.setValue(oldValue);
                joinViewBean.mNewString.setKey(newKey);
                joinViewBean.mNewString.setValue(newValue);
                joinViewBeanList.add(joinViewBean);
            }
            while (cursor.moveToNext());
        }
        return joinViewBeanList;
    }
}

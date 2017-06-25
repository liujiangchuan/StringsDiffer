package com.ll.diffstringxml.db.framework;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession
{

    private final DaoConfig oldStringDaoConfig;
    private final DaoConfig newStringDaoConfig;

    private final OldStringDao mOldStringDao;
    private final NewStringDao mNewStringDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        oldStringDaoConfig = daoConfigMap.get(OldStringDao.class).clone();
        oldStringDaoConfig.initIdentityScope(type);

        newStringDaoConfig = daoConfigMap.get(NewStringDao.class).clone();
        newStringDaoConfig.initIdentityScope(type);

        mOldStringDao = new OldStringDao(oldStringDaoConfig, this);
        mNewStringDao = new NewStringDao(newStringDaoConfig, this);

        registerDao(OldString.class, mOldStringDao);
        registerDao(NewString.class, mNewStringDao);
    }
    
    public void clear() {

        oldStringDaoConfig.getIdentityScope().clear();
        newStringDaoConfig.getIdentityScope().clear();
    }

    public OldStringDao getOldStringDao() {
        return mOldStringDao;
    }

    public NewStringDao getNewStringDao() {
        return mNewStringDao;
    }
}

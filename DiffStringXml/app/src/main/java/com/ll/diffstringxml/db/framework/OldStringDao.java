package com.ll.diffstringxml.db.framework;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table T_OLD_STRING.
*/
public class OldStringDao extends AbstractDao<OldString, String> {

    public static final String TABLENAME = "T_OLD_STRING";

    /**
     * Properties of entity OldString.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Key = new Property(0, String.class, "key", true, "KEY");
        public final static Property Value = new Property(1, String.class, "value", false, "VALUE");
    };

    private DaoSession daoSession;


    public OldStringDao(DaoConfig config) {
        super(config);
    }
    
    public OldStringDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'T_OLD_STRING' (" + //
                "'KEY' TEXT PRIMARY KEY NOT NULL ," + // 0: key
                "'VALUE' TEXT);"); // 1: value
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'T_OLD_STRING'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, OldString entity) {
        stmt.clearBindings();
 
        String key = entity.getKey();
        if (key != null) {
            stmt.bindString(1, key);
        }
 
        String value = entity.getValue();
        if (value != null) {
            stmt.bindString(2, value);
        }
    }

    @Override
    protected void attachEntity(OldString entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public OldString readEntity(Cursor cursor, int offset) {
        OldString entity = new OldString( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // key
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1)  // value
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, OldString entity, int offset) {
        entity.setKey(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setValue(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(OldString entity, long rowId) {
        return entity.getKey();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(OldString entity) {
        if(entity != null) {
            return entity.getKey();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}

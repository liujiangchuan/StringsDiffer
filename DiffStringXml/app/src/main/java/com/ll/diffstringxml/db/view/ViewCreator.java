package com.ll.diffstringxml.db.view;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Liujc on 2017/6/24.
 * Email liujiangchuan@hotmail.com
 */
public class ViewCreator
{
    public static final String V_JOIN = "V_JOIN";

    public static void createAllViews(SQLiteDatabase db, boolean ifNotExists)
    {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE VIEW " + constraint + V_JOIN + " AS " +
                "SELECT T_OLD_STRING.KEY AS OLD_KEY, T_OLD_STRING.VALUE AS OLD_VALUE, " +
                "T_NEW_STRING.KEY AS NEW_KEY, T_NEW_STRING.VALUE AS NEW_VALUE FROM " +
                "T_OLD_STRING LEFT JOIN T_NEW_STRING ON OLD_KEY=NEW_KEY " +
                "WHERE OLD_VALUE <> NEW_VALUE OR NEW_VALUE IS NULL " +
                "UNION " +
                "SELECT T_OLD_STRING.KEY AS OLD_KEY, T_OLD_STRING.VALUE AS OLD_VALUE, " +
                "T_NEW_STRING.KEY AS NEW_KEY, T_NEW_STRING.VALUE AS NEW_VALUE FROM " +
                "T_NEW_STRING LEFT JOIN T_OLD_STRING ON OLD_KEY=NEW_KEY " +
                "WHERE OLD_VALUE <> NEW_VALUE OR OLD_VALUE IS NULL ");
    }
}
package com.ll.diffstringxml.db.view;

import com.ll.diffstringxml.db.framework.NewString;
import com.ll.diffstringxml.db.framework.OldString;

/**
 * Created by Liujc on 2017/6/24.
 * Email liujiangchuan@hotmail.com
 */
public class JoinViewBean
{
    public OldString mOldString;
    public NewString mNewString;

    public JoinViewBean()
    {
        mOldString = new OldString();
        mNewString = new NewString();
    }
}

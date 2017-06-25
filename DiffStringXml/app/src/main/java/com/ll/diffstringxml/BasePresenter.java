package com.ll.diffstringxml;

import rx.Subscription;

/**
 * Created by Liujc on 2017/6/25.
 * Email liujiangchuan@hotmail.com
 */
public class BasePresenter
{
    protected Subscription mSubscription;

    public void unsubscribe()
    {
        if (null != mSubscription)
        {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }
}

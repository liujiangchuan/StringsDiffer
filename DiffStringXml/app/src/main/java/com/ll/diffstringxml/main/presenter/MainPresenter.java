package com.ll.diffstringxml.main.presenter;

import android.text.TextUtils;

import com.ll.diffstringxml.BasePresenter;
import com.ll.diffstringxml.MyApp;
import com.ll.diffstringxml.R;
import com.ll.diffstringxml.main.model.MainModel;
import com.ll.diffstringxml.main.view.IMainActivity;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Liujc on 2017/6/24.
 * Email liujiangchuan@hotmail.com
 */
public class MainPresenter extends BasePresenter
{
    private IMainActivity mIMainActivity;
    private MainModel mMainModel;

    public MainPresenter(IMainActivity iMainActivity)
    {
        mIMainActivity = iMainActivity;
        mMainModel = new MainModel();
    }

    public void startDiff(String oldPath, String newPath)
    {
        mIMainActivity.onPreDiff();
        mSubscription =
                mMainModel.startDiff(oldPath, newPath).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>()
                        {
                            @Override public void onCompleted()
                            {
                                mIMainActivity.onDiffCompleted();
                            }

                            @Override public void onError(Throwable throwable)
                            {
                                throwable.printStackTrace();
                                mIMainActivity.onDiffError(throwable.toString());
                                mIMainActivity.onDiffCompleted();
                            }

                            @Override public void onNext(String string)
                            {
                                if (TextUtils.isEmpty(string))
                                {
                                    mIMainActivity.onDiffError(MyApp.getAppContext()
                                            .getString(R.string.export_failed));
                                }
                                else
                                {
                                    mIMainActivity.onDiffSuccess(string);
                                }
                            }
                        });
    }
}

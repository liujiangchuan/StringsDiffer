package com.ll.diffstringxml.main.view;

/**
 * Created by Liujc on 2017/6/24.
 * Email liujiangchuan@hotmail.com
 */
public interface IMainActivity
{
    void onPreDiff();
    void onDiffCompleted();
    void onDiffError(String message);
    void onDiffSuccess(String path);
}

package com.ll.diffstringxml.fileChooser.presenter;

import com.ll.diffstringxml.BasePresenter;
import com.ll.diffstringxml.fileChooser.model.FileChooseModel;
import com.ll.diffstringxml.fileChooser.view.IFileChooseActivity;

import java.io.File;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Liujc on 2017/6/25.
 * Email liujiangchuan@hotmail.com
 */
public class FileChoosePresenter extends BasePresenter
{
    private IFileChooseActivity mIFileChooseActivity;
    private FileChooseModel mFileChooseModel;

    public FileChoosePresenter(IFileChooseActivity iFileChooseActivity)
    {
        mIFileChooseActivity = iFileChooseActivity;
        mFileChooseModel = new FileChooseModel();
    }

    public void loadFiles(File file)
    {
        mSubscription = mFileChooseModel.getFileList(file).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<File>>()
                {
                    @Override public void onCompleted()
                    {

                    }

                    @Override public void onError(Throwable throwable)
                    {
                        throwable.printStackTrace();
                        mIFileChooseActivity.onFilesUpdate(null);
                        mIFileChooseActivity.onGetFilesError(throwable.getMessage());
                    }

                    @Override public void onNext(List<File> files)
                    {
                        mIFileChooseActivity.onFilesUpdate(files);
                    }
                });
    }
}

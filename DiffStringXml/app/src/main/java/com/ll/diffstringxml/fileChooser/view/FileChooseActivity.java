package com.ll.diffstringxml.fileChooser.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ll.diffstringxml.R;
import com.ll.diffstringxml.fileChooser.OnFileChooseItemClickListener;
import com.ll.diffstringxml.fileChooser.model.FileChooseListAdapter;
import com.ll.diffstringxml.fileChooser.presenter.FileChoosePresenter;
import com.ll.diffstringxml.main.view.MainActivity;
import com.ll.services.tools.FToast;
import com.ll.services.view.FBaseActivity;
import com.ll.services.view.recyclerview.FViewHolder;
import com.ll.services.view.titlebar.IFTitlebar;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Liujc on 2017/6/25.
 * Email liujiangchuan@hotmail.com
 */
public class FileChooseActivity extends FBaseActivity implements IFileChooseActivity
{
    //view
    @Bind(R.id.tv_path) TextView mTvPath;
    @Bind(R.id.btn_last_step) Button mBtnLastStep;
    @Bind(R.id.rv_list) RecyclerView mRvList;
    //list adapter
    private FileChooseListAdapter mListAdapter;
    //presenter
    private FileChoosePresenter mFileChoosePresenter;

    @Override protected int getLayoutResource()
    {
        return R.layout.activity_file_choose;
    }

    @Override protected void initTitlebar(IFTitlebar titlebar)
    {
        titlebar.setTitleText(R.string.choose_file);
    }

    @Override protected void onInit(Bundle savedInstanceState)
    {
        mFileChoosePresenter = new FileChoosePresenter(this);
        mListAdapter = new FileChooseListAdapter(null);
        mListAdapter.setOnLocalImportItemClickListener(new OnFileChooseItemClickListener()
        {
            @Override public void onFolderClick(FViewHolder holder, View view, File bean)
            {
                mBtnLastStep.setEnabled(true);
                mTvPath.setText(bean.getAbsolutePath());
                mFileChoosePresenter.loadFiles(bean);
            }

            @Override public void onFileClick(FViewHolder holder, View view, File bean)
            {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.INTENT_RESULT_PATH, bean.getAbsolutePath());
                setResult(0, intent);
                finish();
            }
        });
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setItemAnimator(null);
        mRvList.setAdapter(mListAdapter);
        mTvPath.setText(Environment.getExternalStorageDirectory().getAbsolutePath());
        mBtnLastStep.setEnabled(false);
    }

    @Override protected void loadData()
    {
        mFileChoosePresenter.loadFiles(Environment.getExternalStorageDirectory());
    }

    @Override protected void onDestroy()
    {
        super.onDestroy();
        mFileChoosePresenter.unsubscribe();
    }

    @OnClick(R.id.btn_last_step) public void onClick()
    {
        String path = mTvPath.getText().toString();
        File file = new File(path);
        File parentFile = file.getParentFile();
        String parentPath = parentFile.getAbsolutePath();
        if (Environment.getExternalStorageDirectory().getAbsolutePath().equals(parentPath))
        {
            mBtnLastStep.setEnabled(false);
        }
        mTvPath.setText(parentPath);
        mFileChoosePresenter.loadFiles(parentFile);
    }

    @Override public void onFilesUpdate(List<File> fileList)
    {
        mListAdapter.notifyDataSetChanged(fileList);
    }

    @Override public void onGetFilesError(String message)
    {
        FToast.showShort(message);
    }
}

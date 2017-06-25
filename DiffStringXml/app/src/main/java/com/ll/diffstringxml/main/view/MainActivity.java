package com.ll.diffstringxml.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ll.diffstringxml.R;
import com.ll.diffstringxml.fileChooser.view.FileChooseActivity;
import com.ll.diffstringxml.main.presenter.MainPresenter;
import com.ll.services.view.FBaseActivity;
import com.ll.services.view.titlebar.IFTitlebar;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends FBaseActivity implements IMainActivity
{
    //view
    @Bind(R.id.et_old_file_path) EditText mEtOldFilePath;
    @Bind(R.id.et_new_file_path) EditText mEtNewFilePath;
    @Bind(R.id.tv_result) TextView mTvResult;
    //presenter
    private MainPresenter mMainPresenter;
    //
    private static final int REQUEST_CODE_OLD = 1;
    private static final int REQUEST_CODE_NEW = 2;
    public static final String INTENT_RESULT_PATH = "path";
    private static final String DEFAULT_OLD_PATH_NAME = "old.xml";
    private static final String DEFAULT_NEW_PATH_NAME = "new.xml";

    @Override protected int getLayoutResource()
    {
        return R.layout.activity_main;
    }

    @Override protected void initTitlebar(IFTitlebar titlebar)
    {
        titlebar.setTitleText(R.string.app_name);
        titlebar.getLeft1().setBtnInvisible();
    }

    @Override protected void onInit(Bundle savedInstanceState)
    {
        mMainPresenter = new MainPresenter(this);
        String rootPath =
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        mEtOldFilePath.setText(rootPath + DEFAULT_OLD_PATH_NAME);
        mEtNewFilePath.setText(rootPath + DEFAULT_NEW_PATH_NAME);
    }

    @Override protected void loadData()
    {

    }

    @Override protected void onDestroy()
    {
        super.onDestroy();
        mMainPresenter.unsubscribe();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (null == data)
        {
            return;
        }
        String path = data.getStringExtra(INTENT_RESULT_PATH);
        switch (requestCode)
        {
            case REQUEST_CODE_OLD:
                mEtOldFilePath.setText(path);
                break;
            case REQUEST_CODE_NEW:
                mEtNewFilePath.setText(path);
                break;
        }
    }

    @OnClick({R.id.btn_old_file_path, R.id.btn_new_file_path, R.id.btn_start_diff})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_old_file_path:
                startActivityForResult(new Intent(this, FileChooseActivity.class),
                        REQUEST_CODE_OLD);
                break;
            case R.id.btn_new_file_path:
                startActivityForResult(new Intent(this, FileChooseActivity.class),
                        REQUEST_CODE_NEW);
                break;
            case R.id.btn_start_diff:
                mMainPresenter.startDiff(mEtOldFilePath.getText().toString(),
                        mEtNewFilePath.getText().toString());
                break;
        }
    }

    @Override public void onPreDiff()
    {
        showLoadingDialog(null);
    }

    @Override public void onDiffCompleted()
    {
        dismissLoadingDialog();
    }

    @Override public void onDiffError(String message)
    {
        mTvResult.setText(message);
    }

    @Override public void onDiffSuccess(String path)
    {
        mTvResult.setText(getString(R.string.diff_result_success, path));
    }
}

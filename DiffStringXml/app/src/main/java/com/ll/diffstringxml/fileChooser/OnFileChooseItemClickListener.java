package com.ll.diffstringxml.fileChooser;

import android.view.View;

import com.ll.services.view.recyclerview.FViewHolder;

import java.io.File;

/**
 * Created by Liujc on 2016/8/31.
 * Email: liujiangchuan@hotmail.com
 */
public interface OnFileChooseItemClickListener
{
    void onFolderClick(FViewHolder holder, View view, File bean);

    void onFileClick(FViewHolder holder, View view, File bean);
}

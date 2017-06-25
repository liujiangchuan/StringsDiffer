package com.ll.diffstringxml.fileChooser.view;

import java.io.File;
import java.util.List;

/**
 * Created by Liujc on 2017/6/25.
 * Email liujiangchuan@hotmail.com
 */
public interface IFileChooseActivity
{
    void onFilesUpdate(List<File> fileList);
    void onGetFilesError(String message);
}

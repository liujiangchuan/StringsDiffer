package com.ll.diffstringxml.fileChooser.model;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Liujc on 2017/6/25.
 * Email liujiangchuan@hotmail.com
 */
public class FileChooseModel
{
    private static final String XML_SUFFIX = ".xml";
    private FileFilter mFileFilterInFolder;

    public FileChooseModel()
    {
        mFileFilterInFolder = new FileFilter()
        {
            @Override public boolean accept(File pathname)
            {
                if (!pathname.isDirectory())
                {
                    return isLegalFile(pathname.getName());
                }
                return true;
            }
        };
    }

    public Observable<List<File>> getFileList(final File file)
    {
        return Observable.fromCallable(new Callable<List<File>>()
        {
            @Override public List<File> call() throws Exception
            {
                List<File> ret = new LinkedList();
                int folderCount = 0;
                if (null != file)
                {
                    File[] files = file.listFiles(mFileFilterInFolder);
                    int length = files.length;
                    for (int i = 0; i < length; i++)
                    {
                        File file = files[i];
                        if (file.isDirectory())
                        {
                            ret.add(folderCount, file);
                            folderCount++;
                        }
                        else
                        {
                            ret.add(file);
                        }
                    }
                }
                return ret;
            }
        }).subscribeOn(Schedulers.io());
    }

    private boolean isLegalFile(String fileName)
    {
        return fileName.endsWith(XML_SUFFIX);
    }
}

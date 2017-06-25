package com.ll.diffstringxml.main.model;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Xml;

import com.ll.diffstringxml.MyApp;
import com.ll.diffstringxml.R;
import com.ll.diffstringxml.db.baseAccess.JoinViewDaoBase;
import com.ll.diffstringxml.db.baseAccess.NewStringDaoBase;
import com.ll.diffstringxml.db.baseAccess.OldStringDaoBase;
import com.ll.diffstringxml.db.framework.NewString;
import com.ll.diffstringxml.db.framework.OldString;
import com.ll.diffstringxml.db.view.JoinViewBean;
import com.ll.diffstringxml.db.view.ParentString;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Liujc on 2017/6/24.
 * Email liujiangchuan@hotmail.com
 */
public class MainModel
{
    private static final String EXCEL_NAME = "diff";
    private static final String EXCEL_SUFFIX = ".xls";
    private static final String XML_STRING = "string";

    public Observable<String> startDiff(final String oldPath, final String newPath)
    {
        return Observable.fromCallable(new Callable<String>()
        {
            @Override public String call() throws Exception
            {
                //delete old data in database
                clearStrings();
                //insert data
                insertOldString(oldPath);
                insertNewString(newPath);
                //export
                return exportResult();
            }
        }).subscribeOn(Schedulers.io());
    }

    private String exportResult() throws IOException, WriteException
    {
        File root = Environment.getExternalStorageDirectory();
        if (null == root)
        {
            return null;
        }
        String path = root.getPath() + File.separator + EXCEL_NAME + System.currentTimeMillis() +
                EXCEL_SUFFIX;
        //create file
        File file = new File(path);
        WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);
        //create sheet
        String sheetAddName = MyApp.getAppContext().getString(R.string.sheet_add);
        String sheetDeleteName = MyApp.getAppContext().getString(R.string.sheet_delete);
        String sheetUpdateName = MyApp.getAppContext().getString(R.string.sheet_update);
        WritableSheet writableSheetAdd = writableWorkbook.createSheet(sheetAddName, 0);
        WritableSheet writableSheetDelete = writableWorkbook.createSheet(sheetDeleteName, 1);
        WritableSheet writableSheetUpdate = writableWorkbook.createSheet(sheetUpdateName, 2);
        //get data and add to sheet
        List<JoinViewBean> joinViewBeanList = JoinViewDaoBase.getInstance().queryList();
        int size = joinViewBeanList.size();
        for (int i = 0, s0 = 0, s1 = 0, s2 = 0; i < size; i++)
        {
            Label label1, label2, label3;
            JoinViewBean joinViewBean = joinViewBeanList.get(i);
            if (TextUtils.isEmpty(joinViewBean.mOldString.getKey()))
            {
                label1 = new Label(0, s0, joinViewBean.mNewString.getKey());
                label2 = new Label(1, s0, joinViewBean.mNewString.getValue());
                writableSheetAdd.addCell(label1);
                writableSheetAdd.addCell(label2);
                s0++;
                continue;
            }
            if (TextUtils.isEmpty(joinViewBean.mNewString.getKey()))
            {
                label1 = new Label(0, s1, joinViewBean.mOldString.getKey());
                label2 = new Label(1, s1, joinViewBean.mOldString.getValue());
                writableSheetDelete.addCell(label1);
                writableSheetDelete.addCell(label2);
                s1++;
                continue;
            }
            label1 = new Label(0, s2, joinViewBean.mOldString.getKey());
            label2 = new Label(1, s2, joinViewBean.mOldString.getValue());
            label3 = new Label(2, s2, joinViewBean.mNewString.getValue());
            writableSheetUpdate.addCell(label1);
            writableSheetUpdate.addCell(label2);
            writableSheetUpdate.addCell(label3);
            s2++;
        }
        //write and close
        writableWorkbook.write();
        writableWorkbook.close();
        return path;
    }

    private void clearStrings()
    {
        OldStringDaoBase.getInstance().deleteAll();
        NewStringDaoBase.getInstance().deleteAll();
    }

    private void insertOldString(String path) throws IOException, XmlPullParserException
    {
        List oldStringList = analysisXML(new IStringCreator()
        {
            @Override public ParentString getString()
            {
                return new OldString();
            }
        }, path);

        OldStringDaoBase.getInstance().insertInTx(oldStringList);
    }

    private void insertNewString(String path) throws IOException, XmlPullParserException
    {
        List newStringList = analysisXML(new IStringCreator()
        {
            @Override public ParentString getString()
            {
                return new NewString();
            }
        }, path);
        NewStringDaoBase.getInstance().insertInTx(newStringList);
    }

    private List analysisXML(IStringCreator iStringCreator, String path)
            throws XmlPullParserException, IOException
    {
        List parentStringList = null;
        XmlPullParser pullParser = Xml.newPullParser();
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(path));
        pullParser.setInput(inputStreamReader);
        int event = pullParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT)
        {
            switch (event)
            {
                case XmlPullParser.START_DOCUMENT:
                    parentStringList = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (XML_STRING.equals(pullParser.getName()))
                    {
                        ParentString parentString = iStringCreator.getString();
                        String key = pullParser.getAttributeValue(0);
                        parentString.setKey(key);
                        String value = pullParser.nextText();
                        parentString.setValue(value);
                        parentStringList.add(parentString);
                    }
                    break;
            }
            event = pullParser.next();
        }
        return parentStringList;
    }

    interface IStringCreator
    {
        ParentString getString();
    }
}

package com.ll.diffstringxml.fileChooser.model;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ll.diffstringxml.R;
import com.ll.diffstringxml.fileChooser.OnFileChooseItemClickListener;
import com.ll.services.view.recyclerview.FBaseAdapter;
import com.ll.services.view.recyclerview.FViewHolder;

import java.io.File;
import java.util.List;


/**
 * Created by Liujc on 2016/9/6.
 * Email: liujiangchuan@hotmail.com
 */
public class FileChooseListAdapter extends FBaseAdapter<File, FViewHolder>
{
    public static final int VIEW_TYPE_FILE = 11;
    public static final int VIEW_TYPE_FOLDER = 12;

    private OnFileChooseItemClickListener mOnFileChooseItemClickListener;

    public FileChooseListAdapter(List<File> list)
    {
        super(list);
    }

    @Override public int getCustomViewType(int position)
    {
        File bean = getItem(position);
        if (null == bean)
        {
            return VIEW_TYPE_FILE;
        }
        if (bean.isDirectory())
        {
            return VIEW_TYPE_FOLDER;
        }
        else
        {
            return VIEW_TYPE_FILE;
        }
    }

    @Override public FViewHolder createCustomViewHolder(ViewGroup parent, int viewType)
    {
        switch (viewType)
        {
            case VIEW_TYPE_FILE:
                return new FViewHolder(parent, R.layout.file_choose_file_item);
            case VIEW_TYPE_FOLDER:
                return new FViewHolder(parent, R.layout.file_choose_folder_item);
            default:
                return new FViewHolder(parent, R.layout.file_choose_file_item);
        }
    }

    @Override public void bindCustomViewHolder(FViewHolder holder, int position)
    {
        File bean = getItem(position);
        if (null == bean)
        {
            return;
        }
        switch (holder.getItemViewType())
        {
            case VIEW_TYPE_FILE:
                bindFileViewHolder(bean, holder);
                break;
            case VIEW_TYPE_FOLDER:
                bindFolderViewHolder(bean, holder);
                break;
        }
    }

    private void bindFileViewHolder(final File bean, final FViewHolder holder)
    {
        //get data
        String name = bean.getName();
        //get view
        TextView tvName = holder.getView(R.id.tv_name);
        //bind
        tvName.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                mOnFileChooseItemClickListener.onFileClick(holder, v, bean);
            }
        });
    }

    private void bindFolderViewHolder(final File bean, final FViewHolder holder)
    {
        //get data
        String name = bean.getName();
        //get view
        TextView tvName = holder.getView(R.id.tv_name);
        //bind
        tvName.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                mOnFileChooseItemClickListener.onFolderClick(holder, v, bean);
            }
        });
    }

    public void setOnLocalImportItemClickListener(OnFileChooseItemClickListener listener)
    {
        mOnFileChooseItemClickListener = listener;
    }
}

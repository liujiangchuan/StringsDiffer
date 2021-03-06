package com.ll.services.view.noscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by Liujc on 2016/5/12.
 * Email liujiangchuan@hotmail.com
 */
public class FNoScrollGridView extends GridView
{
    public FNoScrollGridView(Context context)
    {
        super(context);
    }

    public FNoScrollGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FNoScrollGridView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == MotionEvent.ACTION_MOVE)
        {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

package com.phonelink.recycleriviewdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by AndyChang on 2017/4/27.
 */

public class MyRecyclerView extends RecyclerView {
    private static final String TAG = "MyRecyclerView";

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static String actionToString(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            case MotionEvent.ACTION_OUTSIDE:
                return "ACTION_OUTSIDE";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_HOVER_MOVE:
                return "ACTION_HOVER_MOVE";
            case MotionEvent.ACTION_SCROLL:
                return "ACTION_SCROLL";
            case MotionEvent.ACTION_HOVER_ENTER:
                return "ACTION_HOVER_ENTER";
            case MotionEvent.ACTION_HOVER_EXIT:
                return "ACTION_HOVER_EXIT";
            case MotionEvent.ACTION_BUTTON_PRESS:
                return "ACTION_BUTTON_PRESS";
            case MotionEvent.ACTION_BUTTON_RELEASE:
                return "ACTION_BUTTON_RELEASE";
        }
        int index = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                return "ACTION_POINTER_DOWN(" + index + ")";
            case MotionEvent.ACTION_POINTER_UP:
                return "ACTION_POINTER_UP(" + index + ")";
            default:
                return Integer.toString(action);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
//        stopScroll();
//        if (getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING) {
//            super.onInterceptTouchEvent(e);
//            return true;
//        }
        boolean b = super.onInterceptTouchEvent(e);
        if (e.getAction() == MotionEvent.ACTION_MOVE) return b;
        LogUtils.i(TAG, "onInterceptTouchEvent() " + actionToString(e.getAction()) +
                " returned: " + b + " getScrollState() " + getScrollState());
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean b = super.onTouchEvent(e);
        if (e.getAction() == MotionEvent.ACTION_MOVE) return b;
        LogUtils.i(TAG, "onTouchEvent() " + actionToString(e.getAction()) +
                " returned: " + b + " getScrollState() " + getScrollState());
        return b;
    }
}

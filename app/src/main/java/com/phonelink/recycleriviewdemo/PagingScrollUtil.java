package com.phonelink.recycleriviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;


/**
 * ClassName:com.phonelink.recycleriviewdemo
 * Description:分页滑动效果
 * author:wjc on 2017/4/20 11:46
 */

public class PagingScrollUtil {

    private static final String TAG = "PagingScrollUtil";
    public int indicatorEndX;
    public int indicatorStartX;
    ORIENTATION mOrientation = ORIENTATION.HORIZONTAL;
    RecyclerView mRecyclerView = null;
    OnPageChangeListener mOnPageChangeListener;
    CircleIndicator mCircleIndicator;
    private int lastPageIndex = -1;
    //x 的偏移量
    private int offsetX = 0;
    private MyOnFlingListener mOnFlingListener = new MyOnFlingListener();
    private MyOnScollListener mMyOnScollListener = new MyOnScollListener();
    private RectIndicator mRectIndicator;
    private RecyclerView.LayoutManager layoutManager;

    public void setUpRecycleView(RecyclerView recycleView) {
        if (recycleView == null) {
            throw new IllegalArgumentException("recycleView must be not null");
        }
        mRecyclerView = recycleView;
        //处理滑动
        recycleView.setOnFlingListener(mOnFlingListener);
        //设置滚动监听，记录滚动的状态，和总的偏移量
        recycleView.addOnScrollListener(mMyOnScollListener);

        updateLayoutManger();
    }

    public void updateLayoutManger() {
        layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager.canScrollVertically()) {
                mOrientation = ORIENTATION.VERTICAL;
            } else if (layoutManager.canScrollHorizontally()) {
                mOrientation = ORIENTATION.HORIZONTAL;
            } else {
                mOrientation = ORIENTATION.NULL;
            }
            offsetX = 0;
        }
    }

    private int getPageIndex() {
        int p = 0;
        if (mOrientation == ORIENTATION.HORIZONTAL) {
            p = (offsetX + mRecyclerView.getWidth() / 2) / mRecyclerView.getWidth();
        }
        return p;
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    public void setCircleIndicator(CircleIndicator mCircleIndicator) {
        this.mCircleIndicator = mCircleIndicator;
    }

    public void setRectIndicator(RectIndicator mRectIndicator) {
        this.mRectIndicator = mRectIndicator;
        mRectIndicator.setOnIndicatorChangeListener(new RectIndicator.OnIndicatorChangeListener() {

            @Override
            public void onIndicatorAction(int position) {
                indicatorEndX = mRecyclerView.getWidth() * position;
                indicatorStartX = getPageIndex() * mRecyclerView.getWidth();
                mOrientation = ORIENTATION.HORIZONTAL;
                handleIndicatorAction(indicatorStartX, indicatorEndX);
            }
        });
    }

    void handleIndicatorAction(int indicatorStartX, int indicatorEndX) {
        int startPoint = indicatorStartX;
        int endPoint = indicatorEndX;
        if (endPoint < 0) {
            endPoint = 0;
        }
        handleAnimator(endPoint, startPoint);

    }

    private void handleAnimator(int endPoint, int startPoint) {
        mRecyclerView.smoothScrollBy(endPoint - startPoint, 0, new DecelerateInterpolator(1));
    }

    private void updateIndicator() {
        if (mRectIndicator != null) {
            mRectIndicator.setOffset(getPageIndex());
        }
        if (mCircleIndicator != null) {
            mCircleIndicator.setOffset(getPageIndex());
        }
    }

    private void notifyPageIndexChange() {
        if (getPageIndex() != lastPageIndex) {
            lastPageIndex = getPageIndex();
            if (null != mOnPageChangeListener) {
                mOnPageChangeListener.onPageChange(getPageIndex());
            }
        }
    }


    private void animateToCenter() {
        if (offsetX != mRecyclerView.getWidth() * getPageIndex()) {
            handleIndicatorAction(offsetX, mRecyclerView.getWidth() * getPageIndex());
        }
    }

    //方向,其中包含指示器设置
    enum ORIENTATION {
        HORIZONTAL, VERTICAL, NULL
    }

    public interface OnPageChangeListener {
        void onPageChange(int index);
    }

    /**
     * return true ,自己处理滑动事件
     */
    class MyOnFlingListener extends RecyclerView.OnFlingListener {
        @Override
        public boolean onFling(int velocityX, int velocityY) {
            if (mOrientation == ORIENTATION.NULL) {
                return false;
            }
            if (mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                return true;
            }
            int startScollPageIndex = getPageIndex();
            //记录滚动开始和结束的位置
            int endPoint = 0;
            int startPoint = 0;

            if (mOrientation == ORIENTATION.HORIZONTAL) {
                startPoint = offsetX;
                if (velocityX < -1000) {//速度小，计算offesX是否过了一半
                    startScollPageIndex--;
                } else if (velocityX > 1000) {//直接滚动到下一页
                    startScollPageIndex++;
                }
                endPoint = startScollPageIndex * mRecyclerView.getWidth();
            }
            if (endPoint < 0) {
                endPoint = 0;
            }
            handleAnimator(endPoint, startPoint);
            return true;
        }

    }

    /* An OnScrollListener can be added to a RecyclerView to receive messages
       when a scrolling event has occurred on that RecyclerView. */
    class MyOnScollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //停止滚动的状态，或者滚动速度非常小，触发不了 onFling 时，自动设置
            LogUtils.i(TAG, "onScrollStateChanged() called with: newState = [" + newState + "]");
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                animateToCenter();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //滚动结束记录滚动的偏移量
            offsetX += dx;
            LogUtils.v(TAG, "onScrolled() " + " dx = [" + dx + "]" + ",offsetX=" + offsetX);
            notifyPageIndexChange();
        }
    }

}
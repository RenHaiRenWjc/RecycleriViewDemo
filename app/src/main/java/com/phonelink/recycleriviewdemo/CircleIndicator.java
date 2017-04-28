package com.phonelink.recycleriviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * ClassName:com.phonelink.recycleriviewdemo
 * Description:
 * author:wjc on 2017/4/24 12:13
 */

public class CircleIndicator extends View {

    private Paint mFroePain;
    private Paint mBgPaint;

    /**
     * Indicator的背景色画笔颜色
     **/
    private int mBgColor = Color.RED;
    /**
     * Indicator的前景色画笔颜色
     **/
    private int mForeColor = Color.BLUE;
    /**
     * Indicator数量
     **/
    private int mNumber;
    /**
     * Indicator半径
     **/
    private int mRadius = 10;
    /**
     * 移动的偏移量
     **/
    private float mOffset;
    /**
     * 指示器间隔
     */
    private int indicatorSpace = 3 * mRadius;
    /**
     * 指示器开始位置
     */
    private int startOffset = 60;

    private int indicatorWidth;
    private int indicatorHeight;


    public CircleIndicator(Context context) {
        super(context);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
        indicatorSpace = typedArray.getInteger(R.styleable.CircleIndicator_circle_space, indicatorSpace);
        mRadius = typedArray.getInteger(R.styleable.CircleIndicator_circle_radius, mRadius);
        mBgColor = typedArray.getColor(R.styleable.CircleIndicator_circle_bgColor, mBgColor);
        mForeColor = typedArray.getColor(R.styleable.CircleIndicator_circle_foreColor, mForeColor);
    }

    private void initPaint() {
        mFroePain = new Paint();
        mFroePain.setAntiAlias(true);
        mFroePain.setStyle(Paint.Style.FILL);
        mFroePain.setColor(mForeColor);
        mFroePain.setStrokeWidth(2);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        for (int i = 0; i < mNumber; i++) {
            canvas.drawCircle(startOffset + indicatorSpace * i, indicatorHeight / 2, mRadius, mBgPaint);
        }
        canvas.drawCircle(startOffset + mOffset, indicatorHeight / 2, mRadius, mFroePain);

    }

    public void setOffset(int position) {
        position %= mNumber;
        mOffset = position * indicatorSpace;
        //重绘
        invalidate();
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        indicatorWidth = getMySize(500, widthMeasureSpec);
        indicatorHeight = getMySize(100, heightMeasureSpec);
        setMeasuredDimension(indicatorWidth, indicatorHeight);
        startOffset = indicatorWidth / 2 - mRadius * mNumber - (indicatorSpace - 2 * mRadius) * (mNumber - 1) / 2;
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }


}

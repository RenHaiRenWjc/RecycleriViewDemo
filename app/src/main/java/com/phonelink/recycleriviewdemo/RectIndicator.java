package com.phonelink.recycleriviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ClassName:com.phonelink.recycleriviewdemo
 * Description:
 * author:wjc on 2017/4/24 17:29
 */

public class RectIndicator extends View {
    private static final String TAG = "RectIndicator";
    OnIndicatorChangeListener mOnIndicatorChangeListener;
    private Paint mFroePaint;
    private Paint mBgPaint;
    private Paint mFroeTextPaint;
    private Paint mBgTextPaint;
    /**
     * Indicator的背景色画笔颜色
     **/
    private int mBgColor;
    /**
     * Indicator的前景色画笔颜色
     **/
    private int mForeColor;
    // 文字画笔颜色
    private int mBgTextColor = Color.WHITE;
    private int mForeTextColor = Color.BLACK;
    /**
     * Indicator数量
     **/
    private int mNumber;
    /**
     * 矩形长宽
     **/
    private int rectWidth = 100;
    private int rectHeight = 80;
    /**
     * 字体大小
     */
    private float mTextSize = 30l;
    /**
     * 移动的偏移量
     **/
    private float mOffset;
    /**
     * 指示器间隔,必须大于指示器的宽度
     */
    private int indicatorSpace = rectWidth + 20;
    /**
     * 指示器开始位置
     */
    private int startOffset = 60;
    private int indicatorWidth;
    private int indicatorHeight;
    private int rectCorner = 15;

    public RectIndicator(Context context) {
        super(context);
        initPaint();
    }

    public RectIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RectIndicator);
        // mRadius = typedArray.getInteger(R.styleable.CircleIndicator_Indicator_radius, mRadius);
        indicatorSpace = typedArray.getInteger(R.styleable.RectIndicator_rect_space, indicatorSpace);
        rectWidth = typedArray.getInteger(R.styleable.RectIndicator_rect_width, rectWidth);
        rectHeight = typedArray.getInteger(R.styleable.RectIndicator_rect_height, rectHeight);

        mBgColor = typedArray.getColor(R.styleable.RectIndicator_rect_bgColor, mBgColor);
        mForeColor = typedArray.getColor(R.styleable.RectIndicator_rect_foreColor, mForeColor);

        mBgTextColor = typedArray.getColor(R.styleable.RectIndicator_text_bgColor, mBgTextColor);
        mForeTextColor = typedArray.getColor(R.styleable.RectIndicator_text_foreColor, mForeTextColor);
        mTextSize = typedArray.getDimension(R.styleable.RectIndicator_rect_text_size, mTextSize);
        rectCorner = typedArray.getInteger(R.styleable.RectIndicator_rect_corner, rectCorner);
        LogUtils.d(TAG, "RectIndicator");
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        LogUtils.i(TAG, "initPaint");
        mFroePaint = new Paint();
        mFroePaint.setAntiAlias(true);
        mFroePaint.setStyle(Paint.Style.FILL);
        mFroePaint.setColor(mForeColor);
        mFroePaint.setStrokeWidth(2);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStrokeWidth(2);

        mFroeTextPaint = new Paint();
        mFroeTextPaint.setColor(mForeTextColor);
        mFroeTextPaint.setTextAlign(Paint.Align.CENTER);
        mFroeTextPaint.setTextSize(mTextSize);


        mBgTextPaint = new Paint();
        mBgTextPaint.setColor(mBgTextColor);
        mBgTextPaint.setTextAlign(Paint.Align.CENTER);
        mBgTextPaint.setTextSize(mTextSize);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int baselineY = getTextHeight(indicatorHeight / 2);
        for (int i = 0; i < mNumber; i++) {
            // 画圆角矩形(RectF)
            RectF rectrf = new RectF(
                    startOffset + indicatorSpace * i,
                    indicatorHeight / 2 - rectHeight / 2,
                    startOffset + indicatorSpace * i + rectWidth,
                    indicatorHeight / 2 + rectHeight / 2);

            if (indicatorSpace * i == mOffset) {//被选中,添加点击事件
                canvas.drawRoundRect(rectrf, rectCorner, rectCorner, mFroePaint);
                canvas.drawText("被点" + i,
                        startOffset + indicatorSpace * i + rectWidth / 2,
                        baselineY,
                        mFroeTextPaint);

            } else {
                canvas.drawRoundRect(rectrf, rectCorner, rectCorner, mBgPaint);
                canvas.drawText("我是" + i,
                        startOffset + indicatorSpace * i + rectWidth / 2,
                        baselineY,
                        mBgTextPaint);
            }
        }

    }

    private int getTextHeight(int center) {
        Paint.FontMetricsInt mFroeTextPaintFontMetricsInt = mFroeTextPaint.getFontMetricsInt();
        int textTop = mFroeTextPaintFontMetricsInt.top;
        int textBottom = mFroeTextPaintFontMetricsInt.bottom;
        // return textBottom-textTop;
        return center + (textBottom - textTop) / 2 - textBottom;
    }

    public void setOnIndicatorChangeListener(OnIndicatorChangeListener mOnIndicatorChangeListener) {
        this.mOnIndicatorChangeListener = mOnIndicatorChangeListener;
    }

    public void setOffset(int position) {
//        LogUtils.d(TAG, "setOffset() called with: " + "position = [" + position + "]");
        if (position == mNumber) return;
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
        indicatorWidth = getMySize(1024, widthMeasureSpec);
        indicatorHeight = getMySize(100, heightMeasureSpec);
        setMeasuredDimension(indicatorWidth, indicatorHeight);
        startOffset = indicatorWidth / 2 - rectWidth * mNumber / 2
                - (indicatorSpace - rectWidth) * (mNumber - 1) / 2;
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < mNumber; i++) {
                    if (indicatorHeight / 2 - rectHeight / 2 < y
                            && indicatorHeight / 2 + rectHeight / 2 > y
                            && startOffset + indicatorSpace * i < x
                            && startOffset + indicatorSpace * i + rectWidth > x) {
                        if (mOnIndicatorChangeListener != null) {
                            mOnIndicatorChangeListener.onIndicatorAction(i);
                        }

                        break;
                    }
                }
        }
        return true;
    }

    public interface OnIndicatorChangeListener {
        void onIndicatorAction(int position);
    }

}

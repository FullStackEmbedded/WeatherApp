package de.wherndon.weatherapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * This View draws a line graph with the given data. It is transparent so that
 * one can stack views to show multiple data sets at the same time.
 */
public class LineGraphView extends View {

    private float[] mData;
    private float mRangeLo;
    private float mRangeHi;
    private int mColor;
    private Paint mLinePaint;

    public LineGraphView(Context context) {
        super(context);
        // default data
        init(null, 0.0f, 5.0f, Color.RED);
    }

    public LineGraphView(Context context, AttributeSet attrs) {
        super(context,attrs);
        // default data
        init(null, 0.0f, 5.0f, Color.RED);
    }

    public LineGraphView(Context context, AttributeSet attrs, int defStyle) {
        super(context,attrs,defStyle);
        // default data
        init(null, 0.0f, 5.0f, Color.RED);
    }

    private void init(float[] data,float rangeLo,float rangeHi,int color) {
        // Load attributes

        if (data != null) {
            mData = data;
            mRangeLo = rangeLo;
            mRangeHi = rangeHi;
            mColor = color;
        } else {
            mData = new float[]{1.0f, 3.0f, 2.0f, 4.0f};
            mRangeLo = 0.0f;
            mRangeHi = 5.0f;
            mColor = Color.RED;
        }

        // Set up a default Paint object
        mLinePaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mData.length > 1) {
            float curX, curY, prevX = 0, prevY = 0;
            for (int ii = 0; ii < mData.length ; ii++) {
                curX = (ii*getWidth()) / (mData.length-1);
                curY = getHeight() - (((mData[ii] - mRangeLo) * getHeight()) / (mRangeHi-mRangeLo));
                if (ii > 0)
                    canvas.drawLine(prevX, prevY, curX, curY, mLinePaint);
                prevX = curX;
                prevY = curY;
            }
        }
    }

    public void setData(float[] data)
    {
        mData = data;
    }

    public void setRange(float rangeLo,float rangeHi)
    {
        mRangeLo = rangeLo;
        mRangeHi = rangeHi;
    }

    public void setColor(int color)
    {
        mColor = color;
        mLinePaint.setColor(mColor);
    }

}

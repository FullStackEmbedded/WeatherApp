package de.wherndon.weatherapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class GraphGridView extends View {
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...

    private TextPaint mTextPaint;
    private Paint mPaint;
    private float mTextWidth;
    private float mTextHeight;
    private int   mDataCount;
    private float mRangeLo;
    private float mRangeHi;
    private float mRangeDiv;

    public GraphGridView(Context context) {
        super(context);
        init(null, 0);
    }

    public GraphGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GraphGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.GraphGridView, defStyle, 0);

        mExampleColor = a.getColor(
                R.styleable.GraphGridView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.GraphGridView_exampleDimension,
                mExampleDimension);

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        mPaint = new Paint();

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();

        mDataCount = 4;
        mRangeLo = 0.0f;
        mRangeHi = 5.0f;
        mRangeDiv = 1.0f;
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        //mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        /*
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);
        */

        mPaint.setColor(mExampleColor);
        float curX, width = getWidth(), height = getHeight();
        // Do the vertical lines (TODO: dash lines except end lines?)
        for (int ii=0 ; ii<mDataCount ; ii++) {
            curX = (ii*width) /(mDataCount-1);
            canvas.drawLine(curX, 0.0f, curX, height, mPaint);
        }

        // Draw two horizontal lines to finish the square
        canvas.drawLine(0.0f, 0.0f, width, 0.0f, mPaint);
        canvas.drawLine(0.0f, height, width, height, mPaint);

        int iLo = (int)(mRangeLo / mRangeDiv);
        float curVal = mRangeLo, curY;

        // Do the horizontal lines (TODO: dash?)
        while (curVal < mRangeHi) {
            curY = (((int)(curVal / mRangeDiv) - iLo)*height) / (mRangeHi-mRangeLo);
            if (0.5 < curY && curY < height - 0.5) {
                canvas.drawLine(0.0f, height-curY, width, height-curY, mPaint);
            }
            curVal += mRangeDiv;
        }
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

}

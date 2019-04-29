package leavesc.hello.customview.view.waveLoading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import leavesc.hello.customview.view.BaseView;

/**
 * 作者：leavesC
 * 时间：2019/4/27 16:43
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class WaveLoadingView extends BaseView {

    private static final String TAG = "WaveLoadingView";

    //每个波浪的宽度占据View宽度的默认比例
    private static final float DEFAULT_WAVE_SCALE_WIDTH = 0.8f;

    //每个波浪的高度占据View高度的默认比例
    private static final float DEFAULT_WAVE_SCALE_HEIGHT = 0.13f;

    //波浪的默认速度
    private static final long DEFAULT_SPEED = 900;

    //默认大小，dp
    private static final int DEFAULT_SIZE = 220;

    private float waveScaleWidth;

    private float waveScaleHeight;

    private Paint wavePaint;

    private Paint textPaint;

    private int size;

    //每个波浪的起伏高度
    private float waveHeight;

    //每个波浪的宽度
    private float waveWidth;

    //波浪的速度
    private long speed = DEFAULT_SPEED;

    private float animatedValue;

    private ValueAnimator valueAnimator;

    @ColorInt
    private int waveColor = Color.parseColor("#f54183");

    @ColorInt
    private int downTextColor = Color.parseColor("#ffffff");

    private int textSize = 220;

    private char text = '叶';

    public WaveLoadingView(Context context) {
        this(context, null);
    }

    public WaveLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initAnimation();
        setWaveScaleWidth(DEFAULT_WAVE_SCALE_WIDTH);
        setWaveScaleHeight(DEFAULT_WAVE_SCALE_HEIGHT);
    }

    private void initPaint() {
        wavePaint = new Paint();
        wavePaint.setAntiAlias(true);
        wavePaint.setDither(true);
        wavePaint.setColor(waveColor);
        wavePaint.setStyle(Paint.Style.FILL);
        wavePaint.setStrokeWidth(0f);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextSize(textSize);
    }

    public void initAnimation() {
        valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(speed);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultSize = dp2px(DEFAULT_SIZE);
        int width = getSize(widthMeasureSpec, defaultSize);
        int height = getSize(heightMeasureSpec, defaultSize);
        width = height = Math.min(width, height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        size = Math.min(w, h);

        radius = size / 2f;
        centerX = getPaddingLeft() + radius;
        centerY = getPaddingTop() + radius;

        resetWaveParams();

        Log.e(TAG, "size: " + size);
        Log.e(TAG, "radius: " + radius);
        Log.e(TAG, "centerX: " + centerX);
        Log.e(TAG, "centerY: " + centerY);
    }

    private Path circlePath = new Path();

    private Path wavePath = new Path();

    @Override
    protected void onDraw(Canvas canvas) {

        textPaint.setColor(waveColor);
        drawText(canvas, textPaint, String.valueOf(text));

        wavePath.reset();
        wavePath.moveTo(-waveWidth + animatedValue, size / 2.2f);
        for (float i = -waveWidth; i < size + waveWidth; i += waveWidth) {
            wavePath.rQuadTo(waveWidth / 4, -waveHeight, waveWidth / 2, 0);
            wavePath.rQuadTo(waveWidth / 4, waveHeight, waveWidth / 2, 0);
        }
        wavePath.lineTo(size, size);
        wavePath.lineTo(0, size);
        wavePath.close();

        circlePath.reset();
        circlePath.addCircle(centerX, centerY, radius, Path.Direction.CCW);
        circlePath.op(wavePath, Path.Op.INTERSECT);

        canvas.drawPath(circlePath, wavePaint);
        canvas.clipPath(circlePath);

        textPaint.setColor(downTextColor);
        drawText(canvas, textPaint, String.valueOf(text));
    }

    private void drawText(Canvas canvas, Paint textPaint, String text) {
        Rect rect = new Rect(0, 0, size, size);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int centerY = (int) (rect.centerY() - top / 2 - bottom / 2);
        canvas.drawText(text, rect.centerX(), centerY, textPaint);
    }

    private float radius;

    private float centerX;

    private float centerY;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    public void start() {
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }
    }

    public void stop() {
        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }

    public void setWaveScaleWidth(float waveScaleWidth) {
        if (waveScaleWidth <= 0 || waveScaleWidth > 1) {
            return;
        }
        this.waveScaleWidth = waveScaleWidth;
        resetWaveParams();
    }

    public float getWaveScaleWidth() {
        return waveScaleWidth;
    }

    public void setWaveScaleHeight(float waveScaleHeight) {
        if (waveScaleWidth <= 0 || waveScaleWidth > 1) {
            return;
        }
        this.waveScaleHeight = waveScaleHeight;
        resetWaveParams();
    }

    public float getWaveScaleHeight() {
        return waveScaleHeight;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
        resetWaveParams();
    }

    private void resetWaveParams() {
        waveWidth = size * waveScaleWidth;
        waveHeight = size * waveScaleHeight;
        if (valueAnimator != null) {
            valueAnimator.setFloatValues(0, waveWidth);
            valueAnimator.setDuration(speed);
        }
    }

}
package leavesc.hello.customview.view.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import leavesc.hello.customview.view.BaseView;

/**
 * 作者：leavesC
 * 时间：2019/4/25 10:36
 * 描述：
 */
public class WaveView extends BaseView {

    //每个波浪的宽度占据屏幕宽度的默认比例
    private static final float DEFAULT_WAVE_SCALE_WIDTH = 0.5f;

    //每个波浪的高度占据屏幕高度的默认比例
    private static final float DEFAULT_WAVE_SCALE_HEIGHT = 0.024f;

    private float waveScaleWidth;

    private float waveScaleHeight;

    private Paint paint;

    private int contentWidth;

    private int contentHeight;

    //每个波浪的起伏高度
    private float waveHeight;

    //每个波浪的宽度
    private float waveWidth;

    private float animatedValue;

    private ValueAnimator valueAnimator;

    @ColorInt
    private int bgColor = Color.parseColor("#f54183");

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWaveScaleWidth(DEFAULT_WAVE_SCALE_WIDTH);
        setWaveScaleHeight(DEFAULT_WAVE_SCALE_HEIGHT);
        initPaint();
        initAnimation();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(bgColor);
        paint.setStyle(Paint.Style.FILL);
    }

    public void initAnimation() {
        valueAnimator = ValueAnimator.ofFloat(0, waveWidth);
        valueAnimator.setDuration(2000);
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
        setMeasuredDimension(getSize(widthMeasureSpec, getResources().getDisplayMetrics().widthPixels),
                getSize(heightMeasureSpec, getResources().getDisplayMetrics().heightPixels));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        contentWidth = w;
        contentHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWave(canvas);
    }

    private Path path = new Path();

    private void drawWave(Canvas canvas) {
        path.reset();
        path.moveTo(-waveWidth + animatedValue, contentHeight / 2);
        for (float i = -waveWidth; i < contentWidth + waveWidth; i += waveWidth) {
            path.rQuadTo(waveWidth / 4, -waveHeight, waveWidth / 2, 0);
            path.rQuadTo(waveWidth / 4, waveHeight, waveWidth / 2, 0);
        }
        path.lineTo(contentWidth, contentHeight);
        path.lineTo(0, contentHeight);
        path.close();
        canvas.drawPath(path, paint);
    }

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
        this.waveScaleWidth = waveScaleWidth;
        waveWidth = getResources().getDisplayMetrics().widthPixels * waveScaleWidth;
    }

    public float getWaveScaleWidth() {
        return waveScaleWidth;
    }

    public void setWaveScaleHeight(float waveScaleHeight) {
        this.waveScaleHeight = waveScaleHeight;
        waveHeight = getResources().getDisplayMetrics().heightPixels * waveScaleHeight;
    }

    public float getWaveScaleHeight() {
        return waveScaleHeight;
    }

}
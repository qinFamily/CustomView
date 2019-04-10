package leavesc.hello.customview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.TimeZone;

/**
 * 作者：leavesC
 * 时间：2019/4/5 18:44
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class ClockView extends View {

    private Time time;

    private Drawable hourHandDraw;

    private Drawable minuteHandDraw;

    private Drawable clockDraw;

    private int clockWidth;

    private int clockHeight;

    private float minute;

    private float hour;

    private boolean changed;

    private BroadcastReceiver timerBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    //监听时区的变化
                    case Intent.ACTION_TIMEZONE_CHANGED: {
                        time = new Time(TimeZone.getTimeZone(intent.getStringExtra("time-zone")).getID());
                        break;
                    }
                }
            }
            onTimeChanged();
            invalidate();
        }
    };

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        clockDraw = ContextCompat.getDrawable(context, R.drawable.clock);
        hourHandDraw = ContextCompat.getDrawable(context, R.drawable.clock_hour_hand);
        minuteHandDraw = ContextCompat.getDrawable(context, R.drawable.clock_minute_hand);
        time = new Time();
        clockWidth = clockDraw.getIntrinsicWidth();
        clockHeight = clockDraw.getIntrinsicHeight();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        getContext().registerReceiver(timerBroadcast, filter);
        time = new Time();
        onTimeChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(timerBroadcast);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        changed = true;
    }

    private int getSize(int measureSpec, int defaultSize) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int realSize;
        switch (mode) {
            case MeasureSpec.AT_MOST: {
                realSize = Math.min(size, defaultSize);
                break;
            }
            case MeasureSpec.EXACTLY: {
                realSize = Math.min(size, defaultSize);
                break;
            }
            case MeasureSpec.UNSPECIFIED:
            default: {
                realSize = defaultSize;
                break;
            }
        }
        return realSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = getSize(widthMeasureSpec, clockWidth);
        int heightSize = getSize(heightMeasureSpec, clockHeight);
        widthSize = heightSize = Math.min(widthSize, heightSize);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean lChanged = changed;
        if (lChanged) {
            changed = false;
        }
        int availableWidth = getRight() - getLeft();
        int availableHeight = getBottom() - getTop();
        int x = availableWidth / 2;
        int y = availableHeight / 2;

        int drawableWidth = clockDraw.getIntrinsicWidth();
        int drawableHeight = clockDraw.getIntrinsicHeight();

        //如果View的可用展示面积小于图片，因此此处需要判断是否需要进行缩放
        boolean scaled = false;
        if (availableWidth < drawableWidth || availableHeight < drawableHeight) {
            scaled = true;
            float scale = Math.min((float) availableWidth / (float) drawableWidth, (float) availableHeight / (float) drawableHeight);
            canvas.save();
            canvas.scale(scale, scale, availableWidth / 2 + getPaddingStart(), availableHeight / 2 + getPaddingTop());
        }

        if (lChanged) {
            clockDraw.setBounds(x - (drawableWidth / 2), y - (drawableHeight / 2), x + (drawableWidth / 2), y + (drawableHeight / 2));
        }
        clockDraw.draw(canvas);

        canvas.save();
        canvas.rotate(hour / 12.0f * 360.0f, x, y);
        if (lChanged) {
            drawableWidth = hourHandDraw.getIntrinsicWidth();
            drawableHeight = hourHandDraw.getIntrinsicHeight();
            hourHandDraw.setBounds(x - (drawableWidth / 2), y - (drawableHeight / 2), x + (drawableWidth / 2), y + (drawableHeight / 2));
        }
        hourHandDraw.draw(canvas);
        canvas.restore();

        canvas.save();
        canvas.rotate(minute / 60.0f * 360.0f, x, y);
        if (lChanged) {
            drawableWidth = minuteHandDraw.getIntrinsicWidth();
            drawableHeight = minuteHandDraw.getIntrinsicHeight();
            minuteHandDraw.setBounds(x - (drawableWidth / 2), y - (drawableHeight / 2), x + (drawableWidth / 2), y + (drawableHeight / 2));
        }
        minuteHandDraw.draw(canvas);
        canvas.restore();

        if (scaled) {
            canvas.restore();
        }
    }


    private void onTimeChanged() {
        time.setToNow();
        minute = time.minute + time.second / 60.0f;
        hour = time.hour + minute / 60.0f;
        changed = true;
    }

}
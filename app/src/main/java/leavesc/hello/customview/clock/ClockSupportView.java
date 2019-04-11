package leavesc.hello.customview.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;

import java.util.TimeZone;

/**
 * 作者：leavesC
 * 时间：2019/4/10 23:05
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class ClockSupportView extends View {

    private Paint clockPaint;

    private Paint textPaint;

    private int stockWidth = 12;

    private static final int DEFAULT_SIZE = 400;

    private int aroundColor = Color.parseColor("#083476");

    private Time time;

    private float hour;

    private float minute;

    private float second;

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

    public ClockSupportView(Context context) {
        this(context, null);
    }

    public ClockSupportView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockSupportView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initClockPaint();
        initTextPaint();
        time = new Time();
    }

    private void initClockPaint() {
        clockPaint = new Paint();
        clockPaint.setAntiAlias(true);
        clockPaint.setStyle(Paint.Style.STROKE);
        clockPaint.setStrokeWidth(stockWidth);
    }

    private void initTextPaint() {
        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(12);
        textPaint.setTextSize(34);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = getSize(widthMeasureSpec);
        int heightSize = getSize(heightMeasureSpec);
        widthSize = heightSize = Math.min(widthSize, heightSize);
        setMeasuredDimension(widthSize, heightSize);
    }

    private int getSize(int measureSpec) {
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY: {
                return MeasureSpec.getSize(measureSpec);
            }
            case MeasureSpec.UNSPECIFIED:
            default: {
                return ClockSupportView.DEFAULT_SIZE;
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        getContext().registerReceiver(timerBroadcast, filter);
        onTimeChanged();
    }

    private void onTimeChanged() {
        time.setToNow();
        minute = time.minute + time.second / 60.0f;
        hour = time.hour + minute / 60.0f;
        second = time.second;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(timerBroadcast);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //中心点的横纵坐标
        int point = getWidth() / 2;
        //外圆的半径
        int radiusOut = point - stockWidth;

        canvas.translate(point, point);
        canvas.rotate(-90);

        clockPaint.setStyle(Paint.Style.STROKE);

        clockPaint.setColor(aroundColor);
        canvas.drawCircle(0, 0, radiusOut, clockPaint);

        canvas.save();

        float longStartY = radiusOut - 40;
        float longStopY = longStartY + 20;
        float longStockWidth = stockWidth * 0.6f;

        float temp = (longStopY - longStartY) / 2.5f;
        float shortStartY = longStartY + temp;
        float shortStopY = shortStartY + temp;
        float shortStockWidth = longStockWidth * 0.5f;

        clockPaint.setColor(Color.BLACK);

        float degrees = 6;
        for (int i = 0; i <= 360; i += degrees) {
            if (i % 30 == 0) {
                clockPaint.setStrokeWidth(longStockWidth);
                canvas.drawLine(0, longStartY, 0, longStopY, clockPaint);
            } else {
                clockPaint.setStrokeWidth(shortStockWidth);
                canvas.drawLine(0, shortStartY, 0, shortStopY, clockPaint);
            }
            canvas.rotate(degrees);
        }

        canvas.restore();

        float perHour = hour / 12.0f;
        float perMinute = minute / 60.0f;

        canvas.save();
        canvas.rotate(perHour * 360.0f);
        canvas.drawLine(-30, 0, radiusOut / 2, 0, clockPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(perMinute * 360.0f);
        canvas.drawLine(-30, 0, radiusOut * 0.75f, 0, clockPaint);
        canvas.restore();

        clockPaint.setStyle(Paint.Style.FILL);
        clockPaint.setColor(clockCenterColor);
        canvas.drawCircle(0, 0, 10, clockPaint);
    }

    private int clockCenterColor = Color.parseColor("#f54183");

}
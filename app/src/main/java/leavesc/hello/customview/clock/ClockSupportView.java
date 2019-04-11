package leavesc.hello.customview.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：leavesC
 * 时间：2019/4/10 23:05
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class ClockSupportView extends View {

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
    }

    private Paint clockPaint;

    private void initClockPaint() {
        clockPaint = new Paint();
        clockPaint.setColor(Color.BLACK);
        clockPaint.setAntiAlias(true);
        clockPaint.setStyle(Paint.Style.STROKE);
        clockPaint.setStrokeWidth(stockWidth);
    }

    private int stockWidth = 10;

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

    private static final int DEFAULT_SIZE = 400;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = getSize(widthMeasureSpec);
        int heightSize = getSize(heightMeasureSpec);
        widthSize = heightSize = Math.min(widthSize, heightSize);
        setMeasuredDimension(widthSize, heightSize);
    }

    private int padding = 20;

    private float hour = 7;

    private float minute = 50;

    private Paint textPaint;

    private void initTextPaint() {
        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(12);
        textPaint.setTextSize(34);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //中心点的横纵坐标
        int point = getWidth() / 2;
        //外圆的半径
        int radiusOut = getWidth() / 2 - stockWidth;
        //内圆的半径
        int radiusIn = radiusOut - padding;

        canvas.translate(point, point);

        canvas.rotate(-90);
        canvas.drawCircle(0, 0, radiusOut, clockPaint);
        canvas.drawCircle(0, 0, radiusIn, clockPaint);

        canvas.save();

//        canvas.rotate(90);
        float degrees = 6;
        for (int i = 0; i <= 360; i += degrees) {
            if (i % 30 == 0) {
                clockPaint.setStrokeWidth(stockWidth * 1.3f);
                canvas.drawLine(0, radiusIn, 0, radiusIn - 30, clockPaint);
//                if (i != 0) {
//                    canvas.drawText(String.valueOf(i / 30), radiusIn - 90, 0, textPaint);
//                }
            } else {
                clockPaint.setStrokeWidth(stockWidth / 1.7f);
                canvas.drawLine(0, radiusIn, 0, radiusIn - 15, clockPaint);
            }
            canvas.rotate(degrees);
        }

//        canvas.rotate(90);
//        for (int i = 0; i <= 360; i += 30) {
//            canvas.drawText(String.valueOf(i / 30), radiusIn - 90, 0, textPaint);
//            canvas.rotate(30);
//        }

        canvas.restore();

        float perHour = hour / 12.0f;
        float perMinute = minute / 60.0f;

        canvas.save();
        canvas.rotate(perHour * 360.0f);
        canvas.drawLine(0, 0, radiusIn / 2, 0, clockPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(perMinute * 360.0f);
        canvas.drawLine(0, 0, radiusIn * 0.75f, 0, clockPaint);
        canvas.restore();
    }

}
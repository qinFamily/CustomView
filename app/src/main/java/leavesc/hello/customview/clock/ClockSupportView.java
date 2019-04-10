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
        initPaint();
    }

    private Paint paint;

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(stockWidth);
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
        canvas.drawCircle(0, 0, radiusOut, paint);
        canvas.drawCircle(0, 0, radiusIn, paint);

        canvas.save();

        float degrees = 6;
        for (int i = 0; i <= 360; i += degrees) {
            if (i % 30 == 0) {
                paint.setStrokeWidth(stockWidth * 1.3f);
                canvas.drawLine(0, radiusIn, 0, radiusIn - 30, paint);
            } else {
                paint.setStrokeWidth(stockWidth / 1.7f);
                canvas.drawLine(0, radiusIn, 0, radiusIn - 15, paint);
            }
            canvas.rotate(degrees);
        }
        canvas.restore();

        float perHour = hour / 12.0f;
        float perMinute = minute / 60.0f;

        canvas.save();
        canvas.rotate(perHour * 360.0f);
        canvas.drawLine(0, 0, radiusIn / 2, 0, paint);
        canvas.restore();

        canvas.save();
        canvas.rotate(perMinute * 360.0f);
        canvas.drawLine(0, 0, radiusIn * 0.75f, 0, paint);
        canvas.restore();
    }

}

package leavesc.hello.customview.view.rain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 作者：leavesC
 * 时间：2019/4/24 18:06
 * 描述：
 */
public class RainView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "RainView";

    private static final class Line {
        private float startX;
        private float startY;
        private float stopX;
        private float stopY;
    }

    private SurfaceHolder surfaceHolder;

    private Paint paint;

    private final List<Line> lineList = new ArrayList<>();

    private Random random;

    private static final int DEFAULT_SPEED = 30;

    private static final int DEFAULT_DEGREE = 30;

    //雨的下落速度
    private volatile int speed = DEFAULT_SPEED;

    //雨的密集程度
    private volatile int degree = DEFAULT_DEGREE;

    private ScheduledExecutorService scheduledExecutorService;

    private ScheduledFuture<?> scheduledFuture;

    public RainView(Context context) {
        this(context, null);
    }

    public RainView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);
        initPaint();
        random = new Random();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2f);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                for (Line line : lineList) {
                    //重置超出屏幕的 Line 的坐标
                    if (line.startY >= getHeight()) {
                        resetLine(line);
                        continue;
                    }
                    canvas.drawLine(line.startX, line.startY, line.stopX, line.stopY, paint);
                    line.startY = line.startY + speed;
                    line.stopY = line.stopY + speed;
                }
                int tempDegree = degree;
                int size = lineList.size();
                if (size < tempDegree) {
                    lineList.add(getRandomLine());
                } else if (size > tempDegree) {
                    lineList.removeAll(lineList.subList(tempDegree, size - 1));
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.e(TAG, "surfaceCreated");
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
            scheduledFuture = null;
        }
        scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(runnable, 300, 1, TimeUnit.MILLISECONDS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.e(TAG, "surfaceDestroyed");
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
            scheduledFuture = null;
        }
    }

    private Line getRandomLine() {
        Line line = new Line();
        resetLine(line);
        return line;
    }

    private void resetLine(Line line) {
        line.startX = nextFloat(0, getWidth());
        line.startY = 0;
        //使之有一点点倾斜
        line.stopX = line.startX + nextFloat(3.0f, 6.0f);
        line.stopY = line.startY + nextFloat(40.0f, 60.0f);
    }

    //返回 min 到 max 之间的随机数值，包括 min，不包括 max
    private float nextFloat(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getDegree() {
        return degree;
    }

}
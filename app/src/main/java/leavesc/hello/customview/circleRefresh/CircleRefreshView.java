package leavesc.hello.customview.circleRefresh;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

import leavesc.hello.customview.utils.ViewUtils;

/**
 * 作者：leavesC
 * 时间：2019/4/23 16:40
 * 描述：
 */
public class CircleRefreshView extends View {

    private final static class Circle {

        private int x;
        private int y;
        private int radius;
        private int color;

        private Circle(int x, int y, int radius, int color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
        }
    }

    private final int LEFT = 0;

    private final int RIGHT = 1;

    private final int CENTER = 2;

    //View的默认宽度，dp
    private static final int DEFAULT_WIDTH = 60;

    //View的默认高度，dp
    private static final int DEFAULT_HEIGHT = 40;

    //每个圆的默认最大半径
    private static final int DEFAULT_MAX_RADIUS = 7;

    //每个圆的默认最小半径
    private static final int DEFAULT_MIN_RADIUS = 5;

    //默认速度
    private static final long DEFAULT_SPEED = 3000;

    //初始状态，即三个原点还重叠时的状态
    public static final int STATE_ORIGIN = 10;

    //准备开启动画，三个圆的距离处于最大时的状态
    public static final int STATE_PREPARED = 20;

    private long speed = DEFAULT_SPEED;

    private int originState = STATE_ORIGIN;

    private int contentWidth;

    private int contentHeight;

    //中心圆与相邻两个圆的圆心间隔
    private int mGap;

    //圆最大半径
    private int maxRadius;

    //圆最小半径
    private int minRadius;

    private int colorCircleLeft = Color.parseColor("#008577");
    private int colorCircleCenter = Color.parseColor("#f96630");
    private int colorCircleRight = Color.parseColor("#f54183");

    private List<Circle> circleList;

    private Paint paint;

    private ValueAnimator animator;

    public CircleRefreshView(Context context) {
        this(context, null);
    }

    public CircleRefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleRefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circleList = new ArrayList<>(3);
        maxRadius = dp2px(DEFAULT_MAX_RADIUS);
        minRadius = dp2px(DEFAULT_MIN_RADIUS);
        initPaint();
        initAnimator();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    private void initAnimator() {
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(speed);
        animator.setRepeatCount(-1);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //当动画启动时，将三个圆的位置重置到准备开启动画的临界状态
                resetToStart();
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //循环刷新三个圆的位置
                for (int i = 0; i < circleList.size(); i++) {
                    updateCircle(i, animation.getAnimatedFraction());
                }
                postInvalidate();
            }
        });
    }

    //将三个圆的位置重置到准备开启动画的临界状态
    private void resetToStart() {
        Circle circle = circleList.get(LEFT);
        circle.x = minRadius;
        circle.radius = minRadius;

        circle = circleList.get(RIGHT);
        circle.x = contentWidth - minRadius;
        circle.radius = minRadius;

        circle = circleList.get(CENTER);
        circle.x = contentWidth / 2;
        circle.radius = maxRadius;

        postInvalidate();
    }

    private void updateCircle(int index, float fraction) {
        float progress = fraction;  //真实进度
        float virtualFraction;      //每个小球内部的虚拟进度
        switch (index) {
            case LEFT:
                if (fraction < 5f / 6f) {
                    progress = progress + 1f / 6f;
                } else {
                    progress = progress - 5f / 6f;
                }
                break;
            case CENTER:
                if (fraction < 0.5f) {
                    progress = progress + 0.5f;
                } else {
                    progress = progress - 0.5f;
                }
                break;
            case RIGHT:
                if (fraction < 1f / 6f) {
                    progress += 5f / 6f;
                } else {
                    progress -= 1f / 6f;
                }
                break;
        }
        Circle circle = circleList.get(index);
        if (progress <= 1f / 6f) {
            virtualFraction = progress * 6;
            circle.radius = (int) (minRadius * virtualFraction);
            circle.x = minRadius;
            return;
        }
        if (progress >= 5f / 6f) {
            virtualFraction = (progress - 5f / 6f) * 6;
            circle.radius = (int) (minRadius * (1 - virtualFraction));
            return;
        }
        virtualFraction = (progress - 1f / 6f) * 3f / 2f;
        int difference = maxRadius - minRadius;
        if (virtualFraction < 0.5) {
            circle.radius = (int) (minRadius + difference * virtualFraction * 2);
        } else {
            circle.radius = (int) (maxRadius - difference * (virtualFraction - 0.5) * 2);
        }
        circle.x = (int) (minRadius + mGap * 2 * virtualFraction);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        contentWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        contentHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        resetCircles();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getSize(widthMeasureSpec, dp2px(DEFAULT_WIDTH));
        int height = getSize(heightMeasureSpec, dp2px(DEFAULT_HEIGHT));
        setMeasuredDimension(width, height);
    }

    private int getSize(int measureSpec, int defaultSize) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = 0;
        switch (mode) {
            case MeasureSpec.AT_MOST: {
                size = Math.min(MeasureSpec.getSize(measureSpec), defaultSize);
                break;
            }
            case MeasureSpec.EXACTLY: {
                size = MeasureSpec.getSize(measureSpec);
                break;
            }
            case MeasureSpec.UNSPECIFIED: {
                size = defaultSize;
                break;
            }
        }
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Circle circle : circleList) {
            paint.setColor(circle.color);
            canvas.drawCircle(circle.x + getPaddingLeft(), circle.y + getPaddingTop(), circle.radius, paint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    public void drag(float fraction) {
        if (originState == STATE_PREPARED) {
            return;
        }
        if (animator != null && animator.isRunning()) {
            return;
        }
        if (fraction > 1) {
            return;
        }
        circleList.get(LEFT).x = (int) (minRadius + mGap * (1f - fraction));
        circleList.get(RIGHT).x = (int) (contentWidth / 2 + mGap * fraction);
        postInvalidate();
    }

    private void resetCircles() {
        if (circleList.isEmpty()) {
            int x = contentWidth / 2;
            int y = contentHeight / 2;
            mGap = x - minRadius;
            Circle circleLeft = new Circle(x, y, minRadius, colorCircleLeft);
            Circle circleCenter = new Circle(x, y, maxRadius, colorCircleCenter);
            Circle circleRight = new Circle(x, y, minRadius, colorCircleRight);
            circleList.add(LEFT, circleLeft);
            circleList.add(RIGHT, circleRight);
            circleList.add(CENTER, circleCenter);
        }
        if (originState == STATE_ORIGIN) {
            int x = contentWidth / 2;
            int y = contentHeight / 2;
            for (int i = 0; i < circleList.size(); i++) {
                Circle circle = circleList.get(i);
                circle.x = x;
                circle.y = y;
                if (i == CENTER) {
                    circle.radius = maxRadius;
                } else {
                    circle.radius = minRadius;
                }
            }
        } else {
            resetToStart();
        }
    }

    /**
     * 设置圆球初始状态
     * {@link #STATE_ORIGIN}为原始状态（三个小球重合）,
     * {@link #STATE_PREPARED}为准备好可以刷新的状态，三个小球间距最大
     */
    public void setOriginState(int state) {
        if (state == 0) {
            originState = STATE_ORIGIN;
        } else {
            originState = STATE_PREPARED;
        }
    }

    public void start() {
        if (!animator.isRunning()) {
            animator.start();
        }
    }

    public void stop() {
        if (animator.isRunning()) {
            animator.cancel();
            resetCircles();
        }
    }

    public void setSpeed(long speed) {
        this.speed = speed;
        animator.setDuration(speed);
    }

    public long getSpeed() {
        return speed;
    }

    private int dp2px(float dpValue) {
        return ViewUtils.dp2px(getContext(), dpValue);
    }

}
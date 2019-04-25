package leavesc.hello.customview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import leavesc.hello.customview.utils.ViewUtils;

/**
 * 作者：leavesC
 * 时间：2019/4/25 10:36
 * 描述：
 */
public class BaseView extends View {

    public BaseView(Context context) {
        super(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected int getSize(int measureSpec, int defaultSize) {
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

    protected int dp2px(float dpValue) {
        return ViewUtils.dp2px(getContext(), dpValue);
    }

}

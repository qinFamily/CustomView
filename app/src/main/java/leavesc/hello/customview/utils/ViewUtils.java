package leavesc.hello.customview.utils;

import android.content.Context;

/**
 * 作者：leavesC
 * 时间：2019/4/23 17:25
 * 描述：
 */
public class ViewUtils {

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
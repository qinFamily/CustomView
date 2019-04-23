package leavesc.hello.customview.widget;

import android.widget.SeekBar;

/**
 * 作者：leavesC
 * 时间：2019/4/13 15:40
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public abstract class OnSeekBarChangeSimpleListener implements SeekBar.OnSeekBarChangeListener {

    @Override
    public abstract void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

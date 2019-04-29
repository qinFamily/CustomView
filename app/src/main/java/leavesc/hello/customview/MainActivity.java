package leavesc.hello.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import leavesc.hello.customview.view.circleRefresh.CircleRefreshViewActivity;
import leavesc.hello.customview.view.clock.ClockViewActivity;
import leavesc.hello.customview.view.percentage.PercentageViewActivity;
import leavesc.hello.customview.view.rain.RainViewActivity;
import leavesc.hello.customview.view.taiji.TaiJiViewActivity;
import leavesc.hello.customview.view.wave.WaveViewActivity;
import leavesc.hello.customview.view.waveLoading.WaveLoadingViewActivity;

/**
 * 作者：leavesC
 * 时间：2019/4/10 22:03
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(WaveLoadingViewActivity.class);
    }

    private void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void startClockActivity(View view) {
        startActivity(ClockViewActivity.class);
    }

    public void startPercentageViewActivity(View view) {
        startActivity(PercentageViewActivity.class);
    }

    public void startCircleRefreshViewActivity(View view) {
        startActivity(CircleRefreshViewActivity.class);
    }

    public void startRainViewActivity(View view) {
        startActivity(RainViewActivity.class);
    }

    public void startWaveViewActivity(View view) {
        startActivity(WaveViewActivity.class);
    }

    public void startTaiJiViewActivity(View view) {
        startActivity(TaiJiViewActivity.class);
    }

}
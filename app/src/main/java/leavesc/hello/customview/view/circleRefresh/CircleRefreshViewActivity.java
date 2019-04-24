package leavesc.hello.customview.view.circleRefresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import leavesc.hello.customview.R;
import leavesc.hello.customview.widget.OnSeekBarChangeSimpleListener;

/**
 * 作者：leavesC
 * 时间：2019/4/23 16:41
 * 描述：
 */
public class CircleRefreshViewActivity extends AppCompatActivity {

    private CircleRefreshView circleRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_refresh_view);
        circleRefreshView = findViewById(R.id.refresh_view);
        SeekBar seekBarDrag = findViewById(R.id.seekBarDrag);
        seekBarDrag.setMax(100);
        seekBarDrag.setOnSeekBarChangeListener(new OnSeekBarChangeSimpleListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                circleRefreshView.drag(progress / 100f);
            }
        });
        SeekBar seekBarSeed = findViewById(R.id.seekBarSeed);
        seekBarSeed.setMax(100);
        seekBarSeed.setOnSeekBarChangeListener(new OnSeekBarChangeSimpleListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                circleRefreshView.setSpeed(progress * 40);
            }
        });
        seekBarSeed.setProgress((int) (circleRefreshView.getSpeed() / 40));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                circleRefreshView.start();
                break;
            case R.id.btnStop:
                circleRefreshView.stop();
                break;
        }
    }

}
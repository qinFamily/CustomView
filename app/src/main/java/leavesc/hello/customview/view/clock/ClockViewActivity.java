package leavesc.hello.customview.view.clock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

import leavesc.hello.customview.R;
import leavesc.hello.customview.widget.OnSeekBarChangeSimpleListener;

/**
 * 作者：leavesC
 * 时间：2019/4/10 22:00
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class ClockViewActivity extends AppCompatActivity {

    private ClockView clockView;

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_view);
        clockView = findViewById(R.id.clockView);
        tv1 = findViewById(R.id.tv1);

        AppCompatSeekBar seekBar1 = findViewById(R.id.seekBar1);
        seekBar1.setMax(100);
        seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeSimpleListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Random myRandom = new Random();
                int ranColor = 0xff000000 | myRandom.nextInt(0x00ffffff);
                clockView.setAroundColor(ranColor);
            }
        });

        AppCompatSeekBar seekBar2 = findViewById(R.id.seekBar2);
        seekBar2.setMax(100);
        seekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeSimpleListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                clockView.setAroundStockWidth(progress);
            }
        });

        AppCompatSeekBar seekBar3 = findViewById(R.id.seekBar3);
        seekBar3.setMax(100);
        seekBar3.setOnSeekBarChangeListener(new OnSeekBarChangeSimpleListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                clockView.setClockTextSize(progress);
            }
        });

        AppCompatSeekBar seekBar4 = findViewById(R.id.seekBar4);
        seekBar4.setMax(100);
        seekBar4.setOnSeekBarChangeListener(new OnSeekBarChangeSimpleListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv1.getLayoutParams();
                layoutParams.weight = progress * 0.05f;
                tv1.setLayoutParams(layoutParams);
            }
        });
    }

}
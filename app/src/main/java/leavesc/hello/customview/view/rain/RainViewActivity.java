package leavesc.hello.customview.view.rain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;

import leavesc.hello.customview.R;

/**
 * 作者：leavesC
 * 时间：2019/4/24 18:07
 * 描述：
 */
public class RainViewActivity extends AppCompatActivity {

    private RainView rainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain_view);
        rainView = findViewById(R.id.rainView);
        final AppCompatSeekBar speedSeekBar = findViewById(R.id.speedSeekBar);
        speedSeekBar.setMax(100);
        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = RainView.DEFAULT_SPEED;
                int max = min + 80;
                int speed = min + (max - min) * (progress / speedSeekBar.getMax());
                rainView.setSpeed(speed);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        speedSeekBar.setProgress(40);

        final AppCompatSeekBar degreeSeekBar = findViewById(R.id.degreeSeekBar);
        degreeSeekBar.setMax(100);
        degreeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = RainView.DEFAULT_DEGREE;
                int max = min + 100;
                int degree = min + (max - min) * (progress / degreeSeekBar.getMax());
                rainView.setDegree(degree);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        degreeSeekBar.setProgress(40);
    }

}

package leavesc.hello.customview.view.rain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.widget.SeekBar;

import leavesc.hello.customview.R;
import leavesc.hello.customview.widget.OnSeekBarChangeSimpleListener;

/**
 * 作者：leavesC
 * 时间：2019/4/24 18:07
 * 描述：
 */
public class RainViewActivity extends AppCompatActivity {

    private static final String TAG = "RainViewActivity";

    private RainView rainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain_view);
        rainView = findViewById(R.id.rainView);
        final AppCompatSeekBar speedSeekBar = findViewById(R.id.speedSeekBar);
        speedSeekBar.setMax(100);
        speedSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeSimpleListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = 30;
                int max = 50;
                int speed = (int) (min + (max - min) * (progress * 1.0f / speedSeekBar.getMax()));
                rainView.setSpeed(speed);

                Log.e(TAG, "speed: " + speed);
            }
        });
        speedSeekBar.setProgress(30);

        final AppCompatSeekBar degreeSeekBar = findViewById(R.id.degreeSeekBar);
        degreeSeekBar.setMax(100);
        degreeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeSimpleListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = 40;
                int max = min + 30;
                int degree = (int) (min + (max - min) * (progress * 1.0f / degreeSeekBar.getMax()));
                rainView.setDegree(degree);

                Log.e(TAG, "degree: " + degree);
            }
        });
        degreeSeekBar.setProgress(20);
    }

}
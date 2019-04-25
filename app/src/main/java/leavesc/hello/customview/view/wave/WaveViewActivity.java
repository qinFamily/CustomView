package leavesc.hello.customview.view.wave;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import leavesc.hello.customview.R;

/**
 * 作者：leavesC
 * 时间：2019/4/25 10:53
 * 描述：
 */
public class WaveViewActivity extends AppCompatActivity {

    private WaveView waveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_view);
        waveView = findViewById(R.id.waveView);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                waveView.start();
                break;
            case R.id.btnStop:
                waveView.stop();
                break;
        }
    }

}
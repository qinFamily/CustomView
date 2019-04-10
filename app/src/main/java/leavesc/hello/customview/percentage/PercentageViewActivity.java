package leavesc.hello.customview.percentage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import leavesc.hello.customview.R;

/**
 * 作者：leavesC
 * 时间：2019/4/10 21:59
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class PercentageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage_view);
        final PercentageView percentageView = findViewById(R.id.percentageView);

        List<PercentageModel> percentageModelList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PercentageModel percentageModel = new PercentageModel();
            percentageModel.setValue((float) (10.4 * (i + 1)));
            percentageModelList.add(percentageModel);
        }

        percentageView.setData(percentageModelList);

        AppCompatSeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(360);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentageView.setStartAngle(progress);

                TextView tvTest = findViewById(R.id.tvTest);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvTest.getLayoutParams();
                layoutParams.weight = progress * 0.01f;
                tvTest.setLayoutParams(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
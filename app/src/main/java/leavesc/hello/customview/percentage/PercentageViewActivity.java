package leavesc.hello.customview.percentage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import leavesc.hello.customview.R;

/**
 * 作者：leavesC
 * 时间：2019/4/10 21:59
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class PercentageViewActivity extends AppCompatActivity {

    private PercentageView percentageView;

    private List<PercentageModel> percentageModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage_view);
        percentageView = findViewById(R.id.percentageView);
        percentageModelList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PercentageModel percentageModel = new PercentageModel();
            percentageModel.setValue((float) (new Random().nextInt(30) * (i + 1)));
            percentageModelList.add(percentageModel);
        }
        percentageView.setData(percentageModelList);
        AppCompatSeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(360);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentageView.setStartAngle(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void addData(View view) {
        PercentageModel percentageModel = new PercentageModel();
        percentageModel.setValue((float) (new Random().nextInt(30)));
        percentageModelList.add(percentageModel);
        percentageView.setData(percentageModelList);
    }

    public void removeData(View view) {
        if (percentageModelList.size() > 2) {
            percentageModelList.remove(percentageModelList.size() - 1);
            percentageView.setData(percentageModelList);
        }
    }

}
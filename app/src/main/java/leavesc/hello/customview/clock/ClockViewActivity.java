package leavesc.hello.customview.clock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import leavesc.hello.customview.R;
import leavesc.hello.customview.percentage.PercentageViewActivity;

/**
 * 作者：leavesC
 * 时间：2019/4/10 22:00
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class ClockViewActivity extends AppCompatActivity {

    private ClockView clockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_view);
        clockView = findViewById(R.id.clockView);
    }

    public void setVisible(View view) {
        clockView.setVisibility(View.VISIBLE);
    }

    public void setInvisible(View view) {
        clockView.setVisibility(View.INVISIBLE);
    }

    public void setInvisible2(View view) {
        startActivity(new Intent(this, PercentageViewActivity.class));
    }

}
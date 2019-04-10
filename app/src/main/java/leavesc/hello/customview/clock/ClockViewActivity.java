package leavesc.hello.customview.clock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import leavesc.hello.customview.R;

/**
 * 作者：leavesC
 * 时间：2019/4/10 22:00
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class ClockViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_view);
        findViewById(R.id.btnTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv1 = findViewById(R.id.tv1);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv1.getLayoutParams();
                layoutParams.weight = layoutParams.weight + 0.1f;
                findViewById(R.id.tv1).setLayoutParams(layoutParams);
            }
        });
    }

}
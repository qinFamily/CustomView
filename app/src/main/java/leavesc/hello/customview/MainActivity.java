package leavesc.hello.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

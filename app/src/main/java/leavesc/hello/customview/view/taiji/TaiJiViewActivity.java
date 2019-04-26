package leavesc.hello.customview.view.taiji;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import leavesc.hello.customview.R;

/**
 * 作者：leavesC
 * 时间：2019/4/26 9:49
 * 描述：
 */
public class TaiJiViewActivity extends AppCompatActivity {

    private TaiJiView taijiView;

    private float degrees;

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> scheduledFuture;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            degrees = degrees + 1;
            taijiView.setDegrees(degrees);
            if (degrees == 360) {
                degrees = 0;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_ji_view);
        taijiView = findViewById(R.id.taiJiView);
        start();
    }

    private void start() {
        clear();
        scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(runnable, 300, 10, TimeUnit.MILLISECONDS);
    }

    private void clear() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
            scheduledFuture = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clear();
    }

}
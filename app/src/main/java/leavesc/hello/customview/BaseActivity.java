package leavesc.hello.customview;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * 作者：leavesC
 * 时间：2019/5/25 12:30
 * 描述：
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}

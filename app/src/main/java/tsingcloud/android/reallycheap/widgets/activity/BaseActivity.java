package tsingcloud.android.reallycheap.widgets.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import tsingcloud.android.core.interfaces.IActivity;

/**
 * Created by admin on 2016/3/16.
 * 基类activity
 */
public abstract class BaseActivity extends AppCompatActivity implements IActivity {
    // log标签
    protected final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
    }

    public void ToastShow(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
        if ("网络异常".equals(content)) {
            refreshData();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

package tsingcloud.android.reallycheap.my.widgets.activity;

import android.widget.TextView;

import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.utils.ToolsUtil;


/**
 * @author admin 关于我们
 */
public class AboutUsActivity extends BaseActivity {
    private TextView tv_appName;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_about_us, R.string.about_us);
    }

    @Override
    protected void setUpView() {
        tv_appName = (TextView) findViewById(R.id.appName);
        tv_appName.setText("醉食汇" + ToolsUtil.getVersion(this));
    }

    @Override
    protected void setUpData() {

    }

}

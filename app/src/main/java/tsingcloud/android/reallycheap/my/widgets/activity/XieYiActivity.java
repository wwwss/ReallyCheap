package tsingcloud.android.reallycheap.my.widgets.activity;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.widgets.activity.BaseWebActivity;
import tsingcloud.android.reallycheap.R;

/**
 * Created by admin on 2016/6/1.
 * 用户协议页面
 */
public class XieYiActivity extends BaseWebActivity {

    @Override
    protected void setUpData() {
        titleBar.setTitle(R.string.xieyi);
        webView.loadUrl(Api.XIE_YI);
    }
}

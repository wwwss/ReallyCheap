package tsingcloud.android.reallycheap.widgets.activity;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.widgets.adapter.ViewPagerAdapter;
import tsingcloud.android.reallycheap.widgets.view.GuidePageView;

/**
 * @author admin App启动引导页
 */
public class GuidePageActivity extends BaseActivity {

    private ViewPager viewPager;
    private List<View> guidePageViews;
    private ViewPagerAdapter adapter;

    @Override
    protected void setUpContentView() {
        //版本判断
        if (Build.VERSION.SDK_INT >= 20) {
            //设置无标题
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            //设置全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_guide_page);
    }

    @Override
    protected void setUpView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        int[] arrayDrawable = {R.drawable.guide_page0_icon, R.drawable.guide_page1_icon, R.drawable.guide_page2_icon};
        guidePageViews = new ArrayList<>();
        for (int i = 0; i < arrayDrawable.length; i++) {
            GuidePageView guidePageView = new GuidePageView(this, this, arrayDrawable[i], i);
            guidePageViews.add(guidePageView.getView());
        }
        adapter = new ViewPagerAdapter(guidePageViews);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void setUpData() {

    }

}

package tsingcloud.android.reallycheap.widgets.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.widgets.adapter.ViewPagerAdapter;
import tsingcloud.android.reallycheap.widgets.view.GuidePageView;

/**
 * @author admin App启动引导页
 */
public class GuidePageActivity extends Activity {

    private ViewPager viewPager;
    private List<View> guidePageViews;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
    }

    public void setContentView() {
        setContentView(R.layout.activity_guide_page);

        initView();
    }

    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        int[] arrayDrawable = {R.drawable.guide_page0_icon, R.drawable.guide_page1_icon, R.drawable.guide_page2_icon};
        guidePageViews = new ArrayList<View>();
        for (int i = 0; i < arrayDrawable.length; i++) {
            GuidePageView guidePageView = new GuidePageView(this, this, arrayDrawable[i], i);
            guidePageViews.add(guidePageView.getView());
        }
        adapter = new ViewPagerAdapter(guidePageViews);
        viewPager.setAdapter(adapter);
    }

}

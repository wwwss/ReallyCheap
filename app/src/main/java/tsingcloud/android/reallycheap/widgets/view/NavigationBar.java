package tsingcloud.android.reallycheap.widgets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.model.bean.TabBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.core.interfaces.OnNavigationBarClickListener;
import tsingcloud.android.core.interfaces.OnTabSelectedListener;

public class NavigationBar extends LinearLayout implements
        OnTabSelectedListener {
    private Context context;
    private LinearLayout linearLayout;
    private OnNavigationBarClickListener listener;
    private List<TabView> tabViews;


    public NavigationBar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(context)
                .inflate(R.layout.view_navigation_bar, this);
        linearLayout = (LinearLayout) findViewById(R.id.layout);
        tabViews = new ArrayList<>();
    }

    public void initData(List<TabBean> list, int widthPixels, OnNavigationBarClickListener listener) {
        for (int i = 0; i < list.size(); i++) {
            TabBean tabBean = list.get(i);
            TabView tabView = new TabView(context, tabBean.getIndex(),
                    tabBean.getDrawableId(), tabBean.getTextId(), this);
            linearLayout.addView(tabView, widthPixels / list.size(),
                    LayoutParams.MATCH_PARENT);
            tabViews.add(tabView);
        }
        this.listener = listener;
    }

    @Override
    public void onTabSelected(int index) {
        for (int i = 0; i < tabViews.size(); i++) {
            if (i == index) {
                tabViews.get(i).setSelected(true);
            } else {
                tabViews.get(i).setSelected(false);
            }
        }
        listener.OnNavBarClick(index);
    }

}

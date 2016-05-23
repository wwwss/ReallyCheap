package tsingcloud.android.reallycheap.widgets.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.interfaces.OnNavigationBarClickListener;
import tsingcloud.android.core.interfaces.OnTabSwitchToListener;
import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.ClassifyBean;
import tsingcloud.android.model.bean.TabBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.classify.widgets.fragment.ClassifyFragment;
import tsingcloud.android.reallycheap.homepage.widgets.fragment.HomepageFragment;
import tsingcloud.android.reallycheap.my.widgets.fragment.MyFragment;
import tsingcloud.android.reallycheap.shoppingcart.widgets.fragment.ShoppingCartFragment;
import tsingcloud.android.reallycheap.widgets.view.NavigationBar;

public class MainActivity extends BaseActivity implements OnNavigationBarClickListener, OnTabSwitchToListener {

    private NavigationBar navigationBar;
    private HomepageFragment homePageFragment;
    private ClassifyFragment classifyFragment;
    private ShoppingCartFragment shoppingCartFragment;
    private MyFragment myFragment;
    // 当前fragment的index
    private int currentTabIndex;
    private Fragment[] fragments;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setUpView() {
        navigationBar = (NavigationBar) findViewById(R.id.navigationBar);
        homePageFragment = new HomepageFragment();
        homePageFragment.setOnTabSwitchToListener(this);
        classifyFragment = new ClassifyFragment();
        shoppingCartFragment = new ShoppingCartFragment();
        shoppingCartFragment.setOnTabSwitchToListener(this);
        myFragment = new MyFragment();
        fragments = new Fragment[]{homePageFragment, classifyFragment, shoppingCartFragment, myFragment};
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // 添加fragments显示第一个fragment
        fragmentTransaction.add(R.id.content, homePageFragment).show(homePageFragment).commit();
        // 检查提示更新
        UmengUpdateAgent.update(this);
    }

    @Override
    protected void setUpData() {
        int[] drawableArray = {R.drawable.tab_homepage_bg, R.drawable.tab_classify_bg,
                R.drawable.tab_shopping_cart_bg, R.drawable.tab_my_bg};
        int[] tableNameArray = {R.string.tab_homepage_text, R.string.tab_classify_text, R.string.tab_shopping_cart_text, R.string.tab_my_text};
        List<TabBean> tabBeans = new ArrayList<TabBean>();
        for (int i = 0; i < drawableArray.length; i++) {
            TabBean tabBean = new TabBean();
            tabBean.setDrawableId(drawableArray[i]);
            tabBean.setIndex(i);
            tabBean.setTextId(tableNameArray[i]);
            tabBeans.add(tabBean);
        }
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        navigationBar.initData(tabBeans, widthPixels, this);
        int type = getIntent().getIntExtra("type", -1);
        if (type == 2) {
            navigationBar.onTabSelected(type);
        }
    }


    @Override
    public void OnNavBarClick(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragments[currentTabIndex]);
            fragments[currentTabIndex].setUserVisibleHint(false);
            if (!fragments[index].isAdded()) {
                fragmentTransaction.add(R.id.content, fragments[index]);
            }
            fragmentTransaction.show(fragments[index]).commit();
            fragments[index].setUserVisibleHint(true);
        }
        currentTabIndex = index;
    }


    @Override
    public void onTabSwitch(int index, Object object) {
        navigationBar.onTabSelected(index);
        switch (index) {
            case 1:
                ClassifyBean classifyBean = (ClassifyBean) object;
                classifyFragment.setCategoryId(classifyBean.getId());
                break;
        }
    }
}

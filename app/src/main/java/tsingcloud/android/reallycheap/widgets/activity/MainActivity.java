package tsingcloud.android.reallycheap.widgets.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.interfaces.OnNavigationBarClickListener;
import tsingcloud.android.core.interfaces.OnTabListener;
import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.ClassifyBean;
import tsingcloud.android.model.bean.TabBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.classify.widgets.fragment.ClassifyFragment;
import tsingcloud.android.reallycheap.homepage.widgets.fragment.HomepageFragment;
import tsingcloud.android.reallycheap.my.widgets.fragment.MyFragment;
import tsingcloud.android.reallycheap.shoppingcart.widgets.fragment.ShoppingCartFragment;
import tsingcloud.android.reallycheap.widgets.view.NavigationBar;

public class MainActivity extends BaseActivity implements OnNavigationBarClickListener, OnTabListener {

    private NavigationBar navigationBar;
    private HomepageFragment homePageFragment;
    private ClassifyFragment classifyFragment;
    private ShoppingCartFragment shoppingCartFragment;
    private MyFragment myFragment;
    // 当前fragment的index
    private int currentTabIndex;
    private Fragment[] fragments;
    private long exitTime;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setUpView() {
        navigationBar = (NavigationBar) findViewById(R.id.navigationBar);
        homePageFragment = new HomepageFragment();
        homePageFragment.setOnTabListener(this);
        classifyFragment = new ClassifyFragment();
        classifyFragment.setOnTabListener(this);
        shoppingCartFragment = new ShoppingCartFragment();
        shoppingCartFragment.setOnTabListener(this);
        myFragment = new MyFragment();
        myFragment.setOnTabListener(this);
        fragments = new Fragment[]{homePageFragment, classifyFragment, shoppingCartFragment, myFragment};
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // 添加fragments显示第一个fragment
        fragmentTransaction.add(R.id.content, homePageFragment).show(homePageFragment).commit();
        // 检查提示更新
        UmengUpdateAgent.update(this);
        //registerBroadcast();
    }


//    private void registerBroadcast() {
//        IntentFilter intentFilter=new IntentFilter(NetworkListener.NETWORK_CHANGE);
//        intentFilter.addCategory(getPackageName());
//        registerReceiver(receiver,intentFilter);
//    }
//
//    private  BroadcastReceiver receiver=new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (NetworkListener.NETWORK_CHANGE.equals(intent.getAction())){
//
//            }
//
//        }
//    };


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

    @Override
    public void onTabRefresh(int index, Object object) {
        switch (index) {
            case 1:
                classifyFragment.refresh();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出醉食汇", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                           int[] grantResults) {
//        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
//    }
}

package tsingcloud.android.core.interfaces;

/**
 * Created by admin on 2016/4/14.
 * tab切换监听
 */
public interface OnTabListener {
    void onTabSwitch(int index,Object object);
    void onTabRefresh(int index,Object object);
}

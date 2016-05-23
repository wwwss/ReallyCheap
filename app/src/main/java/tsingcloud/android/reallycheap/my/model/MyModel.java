package tsingcloud.android.reallycheap.my.model;

import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.UserBean;

/**
 * Created by admin on 2016/4/18.
 */
public interface MyModel {
    void getUserIfo(String token,Map<String,String> map, OnNSURLRequestListener<UserBean> listener,String tag);
}

package tsingcloud.android.reallycheap.my.model;

import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;

/**
 * Created by admin on 2016/4/17.
 */
public interface PersonalCenterModel {
    void updateUserInfo(String key, String value, String token, OnNSURLRequestListener<UserBean> listener,String tag);
}

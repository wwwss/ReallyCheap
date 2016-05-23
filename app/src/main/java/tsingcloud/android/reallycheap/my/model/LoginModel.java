package tsingcloud.android.reallycheap.my.model;

import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;

/**
 * Created by admin on 2016/3/22.
 * 登录页面Model
 */
public interface LoginModel {
    void getYzm(String phoneNumber, OnNSURLRequestListener<String> listener, String tag);

    void login(String phoneNumber, String code, OnNSURLRequestListener<UserBean> listener, String tag);

    void bindPush(String clientId, String token,OnNSURLRequestListener<String> listener, String tag);
}

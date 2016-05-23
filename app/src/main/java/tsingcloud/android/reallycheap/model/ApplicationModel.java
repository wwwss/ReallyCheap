package tsingcloud.android.reallycheap.model;

import tsingcloud.android.model.bean.ApplicationBean;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;

/**
 * Created by admin on 2016/4/16.
 */
public interface ApplicationModel {
    void initApplicationVariables(String version,OnNSURLRequestListener<ApplicationBean> listener,String tag);
    void updateToken(String token,OnNSURLRequestListener<UserBean> listener,String tag);
    void bindPush(String clientId, String token,OnNSURLRequestListener<String> listener, String tag);
}

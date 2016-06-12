package tsingcloud.android.reallycheap.model;

import java.util.HashMap;
import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.ApplicationBean;
import tsingcloud.android.model.bean.UserBean;

/**
 * Created by admin on 2016/4/16.
 * 程序入口接口实现类
 */
public class ApplicationModelImpl extends BaseModelImpl implements ApplicationModel {

    private final String TAG = getClass().getName();

    @Override
    public void initApplicationVariables(String version, OnNSURLRequestListener<ApplicationBean> listener, String tag) {

        OkHttpUtils.get(Api.INIT_APPLICATION + "version=" + version, new AbstractResultCallback<ApiResponseBean<ApplicationBean>>(listener) {
            @Override
            public void onSuccess(ApiResponseBean<ApplicationBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
            }
        }, tag);

    }

    @Override
    public void updateToken(String token, OnNSURLRequestListener<UserBean> listener, String tag) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        OkHttpUtils.post(Api.UPDATE_TOKEN, new AbstractResultCallback<ApiResponseBean<UserBean>>(listener) {
            @Override
            public void onSuccess(ApiResponseBean<UserBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else
                    listener.onFailure(response.getErrmsg());
            }
        }, params, tag);
    }

    @Override
    public void bindPush(String clientId, String token, OnNSURLRequestListener<String> listener, String tag) {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", clientId);
        map.put("client_type", "android");
        OkHttpUtils.get(Api.BIND_PUSH + token, new AbstractResultCallback<ApiResponseBean<String>>(listener) {
        }, map, tag);
    }
}

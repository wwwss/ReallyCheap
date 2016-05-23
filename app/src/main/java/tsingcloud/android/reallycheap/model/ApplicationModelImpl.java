package tsingcloud.android.reallycheap.model;

import java.util.HashMap;
import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.ApplicationBean;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.utils.LogUtils;
import tsingcloud.android.core.okhttp.OkHttpUtils;

/**
 * Created by admin on 2016/4/16.
 */
public class ApplicationModelImpl implements ApplicationModel {

    private final String TAG = getClass().getName();
    @Override
    public void initApplicationVariables(String version, final OnNSURLRequestListener<ApplicationBean> listener,String tag) {

        OkHttpUtils.get(Api.INIT_APPLICATION + "version=" + version, new OkHttpUtils.ResultCallback<ApiResponseBean<ApplicationBean>>() {

            @Override
            public void onSuccess(ApiResponseBean<ApplicationBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
            }

            @Override
            public void onFailure(Exception e) {

            }
        },tag);

    }

    @Override
    public void updateToken(String token, final OnNSURLRequestListener<UserBean> listener,String tag) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        OkHttpUtils.post(Api.UPDATE_TOKEN, new OkHttpUtils.ResultCallback<ApiResponseBean<UserBean>>() {
            @Override
            public void onSuccess(ApiResponseBean<UserBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.e(TAG, "msg", e);
                listener.onFailure("更新Token失败");
            }
        }, params, tag);
    }

    @Override
    public void bindPush(String clientId, String token, final OnNSURLRequestListener<String> listener, String tag) {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", clientId);
        map.put("client_type", "android");
        OkHttpUtils.get(Api.BIND_PUSH + token, new OkHttpUtils.ResultCallback<ApiResponseBean<String>>() {
            @Override
            public void onSuccess(ApiResponseBean<String> response) {
                if (!response.isSuccess())
                    listener.onFailure(response.getErrmsg());

            }

            @Override
            public void onFailure(Exception e) {

            }
        }, map, tag);
    }
}

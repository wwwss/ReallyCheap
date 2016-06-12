package tsingcloud.android.reallycheap.my.model;

import java.util.HashMap;
import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.callback.ResultCallback;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.core.utils.LogUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.UserBean;

/**
 * Created by admin on 2016/3/22.
 */
public class LoginModelImpl implements LoginModel {

    private final String TAG = getClass().getName();

    @Override
    public void getYzm(String phoneNumber, final OnNSURLRequestListener<String> listener, String tag) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phoneNumber);
        OkHttpUtils.post(Api.SEND_SMS, new ResultCallback<ApiResponseBean<String>>() {
            @Override
            public void onSuccess(ApiResponseBean<String> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getErrmsg());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.e(TAG, "msg", e);
                listener.onFailure("获取验证码失败");
            }
        }, params, tag);


    }

    @Override
    public void login(String phoneNumber, String code, final OnNSURLRequestListener<UserBean> listener, String tag) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phoneNumber);
        params.put("rand_code", code);
        OkHttpUtils.post(Api.SIGN_IN, new ResultCallback<ApiResponseBean<UserBean>>() {
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
                listener.onFailure("登录失败");
            }
        }, params, tag);
    }

    @Override
    public void bindPush(String clientId, String token, final OnNSURLRequestListener<String> listener, String tag) {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", clientId);
        map.put("client_type", "android");
        OkHttpUtils.get(Api.BIND_PUSH + token, new ResultCallback<ApiResponseBean<String>>() {
            @Override
            public void onSuccess(ApiResponseBean<String> response) {
//                if (!response.isSuccess())
//                    listener.onFailure(response.getErrmsg());

            }

            @Override
            public void onFailure(Exception e) {

            }
        }, map, tag);
    }

}

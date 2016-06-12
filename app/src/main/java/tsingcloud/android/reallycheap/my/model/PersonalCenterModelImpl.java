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
 * Created by admin on 2016/4/17.
 * 昵称接口实现
 */
public class PersonalCenterModelImpl implements PersonalCenterModel {

    private final String TAG = getClass().getName();

    @Override
    public void updateUserInfo(String key, String value, String token, final OnNSURLRequestListener<UserBean> listener, String tag) {
        Map<String, String> params = new HashMap<>();
        params.put(key, value);
        params.put("token", token);
        OkHttpUtils.put(Api.UPDATE_USER_INFO, new ResultCallback<ApiResponseBean<UserBean>>() {

            @Override
            public void onSuccess(ApiResponseBean<UserBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else if (response.isTokenFailure())
                    listener.onTokenFailure();
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.e(TAG, "msg", e);
                listener.onFailure("更改用户昵称失败");
            }
        }, params, tag);
    }
}

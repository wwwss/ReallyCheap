package tsingcloud.android.reallycheap.my.model;

import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.core.okhttp.OkHttpUtils;

/**
 * Created by admin on 2016/4/18.
 */
public class MyModelImpl implements MyModel {
    @Override
    public void getUserIfo(String token,Map<String,String> map, final OnNSURLRequestListener<UserBean> listener,String tag) {
        OkHttpUtils.get(Api.GET_USER_INFO + token, new OkHttpUtils.ResultCallback<ApiResponseBean<UserBean>>() {
            @Override
            public void onSuccess(ApiResponseBean<UserBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("获取用户信息失败");
            }
        },map,tag);
    }
}
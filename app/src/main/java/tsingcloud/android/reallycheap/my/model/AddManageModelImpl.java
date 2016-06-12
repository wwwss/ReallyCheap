package tsingcloud.android.reallycheap.my.model;

import java.util.List;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.callback.ResultCallback;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.core.utils.LogUtils;
import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.model.bean.ApiResponseBean;

/**
 * Created by admin on 2016/4/17.
 */
public class AddManageModelImpl implements AddManageModel {

    private final String TAG = getClass().getName();

    @Override
    public void getAddressList(String token, final OnNSURLRequestListener<List<AddressBean>> listener,String tag) {
        OkHttpUtils.get(Api.ADDRESSES + "?token=" + token, new ResultCallback<ApiResponseBean<List<AddressBean>>>() {

            @Override
            public void onSuccess(ApiResponseBean<List<AddressBean>> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObjList());
                else if (response.isTokenFailure())
                    listener.onTokenFailure();
                else
                    listener.onFailure(response.getErrmsg());

            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.e(TAG, "msg", e);
                listener.onFailure("获取地址列表失败");
            }
        },tag);
    }
}

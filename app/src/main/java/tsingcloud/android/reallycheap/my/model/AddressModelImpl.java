package tsingcloud.android.reallycheap.my.model;

import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.core.okhttp.OkHttpUtils;

/**
 * Created by admin on 2016/4/28.
 */
public class AddressModelImpl implements AddressModel {

    @Override
    public void addAddress(Map<String, String> map, final OnNSURLRequestListener<AddressBean> listener,String tag) {
        OkHttpUtils.post(Api.ADDRESSES, new OkHttpUtils.ResultCallback<ApiResponseBean<AddressBean>>() {
            @Override
            public void onSuccess(ApiResponseBean<AddressBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("添加地址失败");
            }
        }, map,tag);
    }

    @Override
    public void updateAddress(Map<String, String> map, final OnNSURLRequestListener<AddressBean> listener,String tag) {
        OkHttpUtils.put(Api.ADDRESSES, new OkHttpUtils.ResultCallback<ApiResponseBean<AddressBean>>() {
            @Override
            public void onSuccess(ApiResponseBean<AddressBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("修改地址失败");
            }
        }, map,tag);
    }

    @Override
    public void deleteAddress(Map<String, String> map, final OnNSURLRequestListener<String> listener,String tag) {
        OkHttpUtils.delete(Api.ADDRESSES, new OkHttpUtils.ResultCallback<ApiResponseBean<String>>() {
            @Override
            public void onSuccess(ApiResponseBean<String> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getErrmsg());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("删除地址失败");
            }
        }, map,tag);
    }
}

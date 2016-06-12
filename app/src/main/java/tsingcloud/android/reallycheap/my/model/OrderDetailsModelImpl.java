package tsingcloud.android.reallycheap.my.model;

import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.callback.ResultCallback;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.OrderBean;

/**
 * Created by admin on 2016/5/6.
 * 订单详情页面数据接口实现类
 */
public class OrderDetailsModelImpl implements OrderDetailsModel {
    @Override
    public void getOrderDetailsData(String orderId, Map<String, String> map, final OnNSURLRequestListener<OrderBean> listener, String tag) {
        OkHttpUtils.get(Api.ORDERS_DETAILS + orderId, new ResultCallback<ApiResponseBean<OrderBean>>() {
            @Override
            public void onSuccess(ApiResponseBean<OrderBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else if (response.isTokenFailure())
                    listener.onTokenFailure();
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("获取订单详情失败");
            }
        }, map, tag);

    }

    @Override
    public void deleteOrder(Map<String, String> map, final OnNSURLRequestListener<String> listener, String tag) {
        OkHttpUtils.delete(Api.ORDERS, new ResultCallback<ApiResponseBean<String>>() {
            @Override
            public void onSuccess(ApiResponseBean<String> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getErrmsg());
                else if (response.isTokenFailure())
                    listener.onTokenFailure();
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("删除订单失败");
            }
        }, map, tag);
    }
}

package tsingcloud.android.reallycheap.shoppingcart.model;

import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.callback.ResultCallback;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.core.utils.LogUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.model.bean.OrderInfoBean;

/**
 * Created by admin on 2016/4/28.
 * 确认订单
 */
public class ConfirmOrderModelImpl implements ConfirmOrderModel {
    @Override
    public void submitOrder(Map<String, String> map, final OnNSURLRequestListener<OrderBean> listener,String tag) {
        OkHttpUtils.post(Api.ORDERS, new ResultCallback<ApiResponseBean<OrderBean>>() {
            @Override
            public void onSuccess(ApiResponseBean<OrderBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else if (response.isTokenFailure())
                    listener.onTokenFailure();
                else
                    listener.onFailure(response.getErrmsg());
                LogUtils.d("-----------------------","ConfirmOrderModelImpl");
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("提交订单失败");
            }
        }, map,tag);
    }

    @Override
    public void getPayInfo(Map<String, String> map, final OnNSURLRequestListener<OrderInfoBean> listener, String tag) {
        OkHttpUtils.get(Api.GET_ORDER_PAY_INFO, new ResultCallback<ApiResponseBean<OrderInfoBean>>() {
            @Override
            public void onSuccess(ApiResponseBean<OrderInfoBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else if (response.isTokenFailure())
                    listener.onTokenFailure();
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("获取支付信息失败");
            }
        }, map,tag);
    }
}

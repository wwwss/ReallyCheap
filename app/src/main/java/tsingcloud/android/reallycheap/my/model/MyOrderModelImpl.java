package tsingcloud.android.reallycheap.my.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.callback.ResultCallback;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.model.bean.OrderInfoBean;

/**
 * Created by admin on 2016/4/29.
 * 我的订单数据接口实现类
 */
public class MyOrderModelImpl implements MyOrderModel {


    @Override
    public void getOrderList(Map<String, String> map, final OnNSURLRequestListener<List<OrderBean>> listener, final OnSetListTotalPagesListener totalPagesListener, String tag) {
        OkHttpUtils.get(Api.ORDERS, new ResultCallback<ApiResponseBean<List<OrderBean>>>() {
            @Override
            public void onSuccess(ApiResponseBean<List<OrderBean>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList());
                    totalPagesListener.setTotalPages(response.getTotal_pages());
                }
                else if (response.isTokenFailure())
                    listener.onTokenFailure();else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("获取订单列表失败");
            }
        }, map, tag);
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

    @Override
    public void confirmReceipt(Map<String, String> map, final OnNSURLRequestListener<String> listener, String tag) {
        OkHttpUtils.get(Api.CONFIRM_RECEIPT, new ResultCallback<ApiResponseBean<String>>() {
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
                listener.onFailure("确认收货失败");
            }
        }, map, tag);
    }
}

package tsingcloud.android.reallycheap.my.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.reallycheap.my.model.OrderDetailsModel;
import tsingcloud.android.reallycheap.my.model.OrderDetailsModelImpl;
import tsingcloud.android.reallycheap.my.view.OrderDetailsView;

/**
 * Created by admin on 2016/5/6.
 * 订单详情页面控制器
 */
public class OrderDetailsPresenter extends BasePresenter{

    private OrderDetailsView orderDetailsView;
    private OrderDetailsModel orderDetailsModel;

    public OrderDetailsPresenter(OrderDetailsView orderDetailsView) {
        super(orderDetailsView);
        this.orderDetailsView = orderDetailsView;
        orderDetailsModel = new OrderDetailsModelImpl();
    }

    public void getOrderDetailsData(String orderId) {
        if (TextUtils.isEmpty(orderDetailsView.getToken()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", orderDetailsView.getToken());
        orderDetailsModel.getOrderDetailsData(orderId,map, new AbstractOnNSURLRequestListener<OrderBean>() {
            @Override
            public void onSuccess(OrderBean response) {
                orderDetailsView.setOrderData(response);
            }

        }, orderDetailsView.getTAG());

    }

    public void deleteOrder(String orderId){
        if (TextUtils.isEmpty(orderDetailsView.getToken()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", orderDetailsView.getToken());
        map.put("id",orderId);
        orderDetailsModel.deleteOrder(map, new AbstractOnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                orderDetailsView.showToast(response);
                orderDetailsView.deleteOrderComplete();
            }
        },orderDetailsView.getTAG());
    }
}

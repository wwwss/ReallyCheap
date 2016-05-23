package tsingcloud.android.reallycheap.shoppingcart.model;

import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.model.bean.OrderInfoBean;

/**
 * Created by admin on 2016/4/28.
 * 提交订单
 */
public interface ConfirmOrderModel {

    void submitOrder(Map<String,String> map,OnNSURLRequestListener<OrderBean> listener,String tag);

    void getPayInfo(Map<String,String> map,OnNSURLRequestListener<OrderInfoBean> listener,String tag);
}

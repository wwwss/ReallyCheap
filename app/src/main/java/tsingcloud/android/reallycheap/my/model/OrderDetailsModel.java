package tsingcloud.android.reallycheap.my.model;

import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.OrderBean;

/**
 * Created by admin on 2016/5/6.
 * 订单详情页面数据接口
 */
public interface OrderDetailsModel {

    void getOrderDetailsData(String orderId,Map<String,String> map,OnNSURLRequestListener<OrderBean> listener,String tag);

    void deleteOrder(Map<String,String> map,OnNSURLRequestListener<String> listener,String tag);
}

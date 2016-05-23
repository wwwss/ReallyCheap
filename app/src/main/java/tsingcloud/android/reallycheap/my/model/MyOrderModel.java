package tsingcloud.android.reallycheap.my.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.model.bean.OrderInfoBean;

/**
 * Created by admin on 2016/4/29.
 * 我的订单数据接口类
 */
public interface MyOrderModel {

    void getOrderList(Map<String,String> map,OnNSURLRequestListener<List<OrderBean>> listener,OnSetListTotalPagesListener totalPagesListener,String tag);

    void getPayInfo(Map<String,String> map,OnNSURLRequestListener<OrderInfoBean> listener,String tag);

    void confirmReceipt(Map<String,String> map,OnNSURLRequestListener<String> listener,String tag);
}

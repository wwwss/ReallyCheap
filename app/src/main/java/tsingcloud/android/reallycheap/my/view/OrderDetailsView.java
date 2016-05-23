package tsingcloud.android.reallycheap.my.view;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.OrderBean;

/**
 * Created by admin on 2016/5/3.
 * 订单详情页面接口
 */
public interface OrderDetailsView extends BaseView{

    void setOrderData(OrderBean orderBean);

    void deleteOrderComplete();
}

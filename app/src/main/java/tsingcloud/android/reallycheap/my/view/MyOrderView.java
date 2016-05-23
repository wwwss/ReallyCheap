package tsingcloud.android.reallycheap.my.view;

import android.app.Activity;

import java.util.List;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.OrderBean;

/**
 * Created by admin on 2016/4/29.
 * 我的订单页面接口类
 */
public interface MyOrderView extends BaseView {
    /**
     * 刷新订单列表完成
     *
     * @param orderBeans
     */
    void refreshOrderListCompleted(List<OrderBean> orderBeans);

    /**
     * 获取订单状态
     *
     * @return
     */
    int getOrderStatus();

    /**
     * 设置页面总数
     *
     * @return
     */
    void setTotalPages(int totalPages);

    /**
     * 确认收货完成
     *
     * @return
     */
    void confirmReceiptComplete();

    /**
     * 支付完成
     *
     * @return
     */
    void payComplete();

    Activity getActivity();
}

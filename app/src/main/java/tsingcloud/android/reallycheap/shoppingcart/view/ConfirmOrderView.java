package tsingcloud.android.reallycheap.shoppingcart.view;

import tsingcloud.android.core.view.BaseView;

/**
 * Created by admin on 2016/4/28.
 * 确认订单
 */
public interface ConfirmOrderView extends BaseView {

    void submitOrderComplete(String orderId);

    void payComplete();
}

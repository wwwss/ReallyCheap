package tsingcloud.android.reallycheap.shoppingcart.view;

import java.util.List;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.model.bean.ShoppingCartBean;

/**
 * Created by admin on 2016/3/28.
 * 购物车
 */
public interface ShoppingCartView extends BaseView{
    void setShippingAddress(AddressBean shippingAddress);

    void setShoppingCartList(List<ShoppingCartBean> shoppingCartBeanList);

    void deleteShopCartItemsSucceed(List<ShoppingCartBean> newShoppingCartBeanList);

    void updateShopCartItemNumberSucceed(ShoppingCartBean newShoppingCartBean, int position);

    void delete();

    void toConfirmOrderActivity(OrderBean orderBean);

    void updateTotalPrice(double productsPrice);


}

package tsingcloud.android.reallycheap.shoppingcart.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.model.bean.ShoppingCartBean;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;

/**
 * Created by admin on 2016/3/28.
 */
public interface ShoppingCartModel {
    void getShippingAddress(String token,OnNSURLRequestListener<AddressBean> listener,String tag);
    void getShoppingCartList(String token,String shopId,OnNSURLRequestListener<List<ShoppingCartBean>> listener,String tag);
    void delete(Map<String,String> map,OnNSURLRequestListener<String> listener,String tag);
    void updateShopCartItemNumber(Map<String,String> map,OnNSURLRequestListener<String> listener,String tag);
}

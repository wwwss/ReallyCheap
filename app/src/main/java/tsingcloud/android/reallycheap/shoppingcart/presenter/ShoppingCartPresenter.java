package tsingcloud.android.reallycheap.shoppingcart.presenter;

import android.text.TextUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.model.bean.ShoppingCartBean;
import tsingcloud.android.reallycheap.shoppingcart.model.ShoppingCartModel;
import tsingcloud.android.reallycheap.shoppingcart.model.ShoppingCartModelImpl;
import tsingcloud.android.reallycheap.shoppingcart.view.ShoppingCartView;

/**
 * Created by admin on 2016/3/28.
 * 购物车控制器
 */
public class ShoppingCartPresenter {
    private ShoppingCartView shoppingCartView;
    private ShoppingCartModel shoppingCartModel;

    public ShoppingCartPresenter(ShoppingCartView shoppingCartView) {
        this.shoppingCartView = shoppingCartView;
        this.shoppingCartModel = new ShoppingCartModelImpl();
    }

    /**
     * 获取收货地址
     */
    public void getShippingAddress() {
        if (TextUtils.isEmpty(shoppingCartView.getToken()))
            return;
        shoppingCartModel.getShippingAddress(shoppingCartView.getToken(), new OnNSURLRequestListener<AddressBean>() {
            @Override
            public void onSuccess(AddressBean response) {
                shoppingCartView.setShippingAddress(response);
            }

            @Override
            public void onFailure(String msg) {
                shoppingCartView.setShippingAddress(null);
                shoppingCartView.showToast(msg);
            }
        }, shoppingCartView.getTAG());
    }

    /**
     * 获取购物车列表
     */
    public void getShoppingCartList() {
        if (TextUtils.isEmpty(shoppingCartView.getToken()) || TextUtils.isEmpty(shoppingCartView.getShopId()))
            return;
        shoppingCartModel.getShoppingCartList(shoppingCartView.getToken(), shoppingCartView.getShopId(), new OnNSURLRequestListener<List<ShoppingCartBean>>() {
            @Override
            public void onSuccess(List<ShoppingCartBean> response) {
                shoppingCartView.setShoppingCartList(response);
            }

            @Override
            public void onFailure(String msg) {
                shoppingCartView.showToast(msg);
            }
        }, shoppingCartView.getTAG());
    }

    /**
     * 删除购物车条目
     */
    public void deleteShoppingCartItem(List<ShoppingCartBean> shoppingCartBeanList) {
        if (TextUtils.isEmpty(shoppingCartView.getToken()))
            return;
        JSONArray jsonArray = new JSONArray();
        final List<ShoppingCartBean> newShoppingCartBeanList = new ArrayList<>();
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(i);
            if (shoppingCartBean.isSelected()) {
                jsonArray.put(shoppingCartBean.getCart_id());
            } else {
                newShoppingCartBeanList.add(shoppingCartBean);
            }
        }
        if (jsonArray.length() == 0) {
            shoppingCartView.showToast("您还未选中任何商品");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", shoppingCartView.getToken());
        map.put("cart_ids", jsonArray.toString());
        map.put("shop_id", shoppingCartView.getShopId());
        shoppingCartModel.delete(map, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                shoppingCartView.deleteShopCartItemsSucceed(newShoppingCartBeanList);
                shoppingCartView.showToast(response);
            }

            @Override
            public void onFailure(String msg) {
                shoppingCartView.showToast(msg);
            }
        }, shoppingCartView.getTAG());
    }

    /**
     * 修改购物车条目数量
     */
    public void updateShopCartItemNumber(final ShoppingCartBean shoppingCartBean, final int number, final int position) {
        if (TextUtils.isEmpty(shoppingCartView.getToken()) || TextUtils.isEmpty(shoppingCartView.getShopId()))
            return;
//        final ShoppingCartBean newShoppingCartBean = shoppingCartBean;
        Map<String, String> map = new HashMap<>();
        map.put("token", shoppingCartView.getToken());
        map.put("product_num", number + "");
        map.put("cart_id", shoppingCartBean.getCart_id());
        map.put("shop_id", shoppingCartView.getShopId());
        shoppingCartModel.updateShopCartItemNumber(map, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                shoppingCartBean.getProductBean().setNumber(number);
                shoppingCartView.updateShopCartItemNumberSucceed(shoppingCartBean, position);
                shoppingCartView.showToast(response);
            }

            @Override
            public void onFailure(String msg) {
                shoppingCartView.showToast(msg);
            }
        }, shoppingCartView.getTAG());

    }

    /**
     * 去结算
     *
     * @param shoppingCartBeanList 购物车列表
     * @param shippingAddressBean  收货地址
     * @param productsPrice        商品价格
     * @param totalPrice           订单总价
     * @param freight              运费
     */
    public void goSettlement(List<ShoppingCartBean> shoppingCartBeanList, AddressBean shippingAddressBean, double productsPrice, double totalPrice, double freight) {
        if (shippingAddressBean == null) {
            shoppingCartView.showToast("请先选择收货地址");
            return;
        }
        if (totalPrice <= 0) return;
        if (null == shoppingCartBeanList || shoppingCartBeanList.isEmpty()) return;
        OrderBean orderBean = new OrderBean();
        orderBean.setAddress_id(shippingAddressBean.getId());
        orderBean.setReceive_name(shippingAddressBean.getReceive_name());
        orderBean.setReceive_phone(shippingAddressBean.getReceive_phone());
        orderBean.setArea(shippingAddressBean.getArea());
        orderBean.setDetail(shippingAddressBean.getDetail());
        orderBean.setTotal_price(totalPrice + "");
        orderBean.setFreight(freight + "");
        orderBean.setShop_id(shoppingCartView.getShopId());
        List<ProductBean> productBeanList = new ArrayList<>();
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            productBeanList.add(shoppingCartBeanList.get(i).getProductBean());
        }
        orderBean.setProducts(productBeanList);
        orderBean.setProducts_price(productsPrice + "");
        shoppingCartView.toConfirmOrderActivity(orderBean);
    }

    public void updateTotalPrice(double totalPrice) {
        shoppingCartView.updateTotalPrice(totalPrice);
    }

}

package tsingcloud.android.reallycheap.model;

import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;

/**
 * Created by admin on 2016/5/24.
 * 产品父类Model
 */
public interface ProductBaseModel {
    void addShoppingCart(Map<String, String> map, OnNSURLRequestListener<String> listener, String tag);
}

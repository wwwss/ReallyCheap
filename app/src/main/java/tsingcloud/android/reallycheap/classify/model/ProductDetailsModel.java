package tsingcloud.android.reallycheap.classify.model;

import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.ProductBean;

/**
 * Created by admin on 2016/5/6.
 * 产品详情数据接口类
 */
public interface ProductDetailsModel {

    /**
     * 获取产品详情接口
     * @param map
     * @param listener
     * @param tag
     */
    void getProductDetailsData(String productId,Map<String, String> map, OnNSURLRequestListener<ProductBean> listener, String tag);

    /**
     * 收藏接口
     * @param map
     * @param listener
     * @param tag
     */
    void collectionProduct(Map<String, String> map, OnNSURLRequestListener<ProductBean> listener, String tag);

    /**
     * 取消收藏接口
     * @param map
     * @param listener
     * @param tag
     */
    void cancelCollectionProduct(Map<String, String> map, OnNSURLRequestListener<String> listener, String tag);

    /**
     * 添加到购物车
     * @param map
     * @param listener
     * @param tag
     */
    void addShoppingCart(Map<String, String> map, OnNSURLRequestListener<String> listener, String tag);

}

package tsingcloud.android.reallycheap.classify.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.classify.model.ProductDetailsModel;
import tsingcloud.android.reallycheap.classify.model.ProductDetailsModelImpl;
import tsingcloud.android.reallycheap.classify.view.ProductDetailsView;
import tsingcloud.android.reallycheap.presenter.ProductBasePresenter;

/**
 * Created by admin on 2016/5/6.
 * 产品详情页面控制器
 */
public class ProductDetailsPresenter extends ProductBasePresenter{

    private ProductDetailsView productDetailsView;
    private ProductDetailsModel productDetailsModel;

    public ProductDetailsPresenter(ProductDetailsView productDetailsView) {
        super(productDetailsView);
        this.productDetailsView = productDetailsView;
        productDetailsModel = new ProductDetailsModelImpl();
    }

    public void getProductDetailsData(String productId) {
        if (TextUtils.isEmpty(productDetailsView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(productDetailsView.getToken()))
            map.put("token", productDetailsView.getToken());
        map.put("shop_id", productDetailsView.getShopId());
        productDetailsModel.getProductDetailsData(productId, map, new OnNSURLRequestListener<ProductBean>() {
            @Override
            public void onSuccess(ProductBean response) {
                productDetailsView.setProductDetailsData(response);
            }

            @Override
            public void onFailure(String msg) {
                productDetailsView.showToast(msg);
            }
        }, productDetailsView.getTAG());

    }

    public void collectionProduct(String productId) {
        if (TextUtils.isEmpty(productDetailsView.getToken()) || TextUtils.isEmpty(productDetailsView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", productDetailsView.getToken());
        map.put("shop_id", productDetailsView.getShopId());
        map.put("product_id", productId);
        productDetailsModel.collectionProduct(map, new OnNSURLRequestListener<ProductBean>() {
            @Override
            public void onSuccess(ProductBean response) {
                productDetailsView.collectionSuccess(response.getFavorite_id());
            }

            @Override
            public void onFailure(String msg) {
                productDetailsView.showToast(msg);
            }
        }, productDetailsView.getTAG());
    }

    public void cancelCollectionProduct(String favoriteId) {
        if (TextUtils.isEmpty(productDetailsView.getToken()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", productDetailsView.getToken());
        map.put("id", favoriteId);
        productDetailsModel.cancelCollectionProduct(map, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                productDetailsView.showToast(response);
                productDetailsView.cancelCollectionSuccess();
            }

            @Override
            public void onFailure(String msg) {
                productDetailsView.showToast(msg);
            }
        }, productDetailsView.getTAG());

    }

//    /**
//     * 添加产品到购物车
//     *
//     * @param productId 产品ID
//     */
//    public void addShoppingCart(String productId) {
//        if (TextUtils.isEmpty(productDetailsView.getToken()) || TextUtils.isEmpty(productDetailsView.getShopId()))
//            return;
//        Map<String, String> map = new HashMap<>();
//        map.put("token", productDetailsView.getToken());
//        map.put("shop_id", productDetailsView.getShopId());
//        map.put("product_id", productId);
//        map.put("product_num", "1");
//        productDetailsModel.addShoppingCart(map, new OnNSURLRequestListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                productDetailsView.showToast(response);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                productDetailsView.showToast(msg);
//            }
//        }, productDetailsView.getTAG());
//    }


}

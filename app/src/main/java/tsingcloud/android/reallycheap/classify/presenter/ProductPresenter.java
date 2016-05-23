package tsingcloud.android.reallycheap.classify.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.classify.model.ProductModel;
import tsingcloud.android.reallycheap.classify.model.ProductModelImpl;
import tsingcloud.android.reallycheap.classify.view.ProductView;

/**
 * Created by admin on 2016/3/24.
 */
public class ProductPresenter {

    private ProductView productView;
    private ProductModel productModel;

    public ProductPresenter(ProductView productView) {
        this.productView = productView;
        productModel = new ProductModelImpl();
    }

    /**
     * 获取产品数据
     */
    public void getProductsData(String url, int pageNum) {
        Map<String, String> map = new HashMap<>();
        map.put("shop_id", productView.getShopId());
        map.put("page_num", pageNum + "");
        productModel.getProductList(url, map, new OnNSURLRequestListener<List<ProductBean>>() {
            @Override
            public void onSuccess(List<ProductBean> productBeans) {
                productView.setProductsData(productBeans);
            }

            @Override
            public void onFailure(String msg) {
                productView.showToast(msg);
            }
        }, new OnSetListTotalPagesListener() {
            @Override
            public void setTotalPages(int totalPages) {
                productView.setTotalPages(totalPages);
            }
        }, productView.getTAG());
    }

    /**
     * 添加产品到购物车
     *
     * @param productId 产品ID
     */
    public void addShoppingCart(String productId) {
        if (TextUtils.isEmpty(productView.getToken()) || TextUtils.isEmpty(productView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", productView.getToken());
        map.put("shop_id", productView.getShopId());
        map.put("product_id", productId);
        map.put("product_num", "1");
        productModel.addShoppingCart(map, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                productView.showToast(response);
            }

            @Override
            public void onFailure(String msg) {
                productView.showToast(msg);
            }
        }, productView.getTAG());
    }
}

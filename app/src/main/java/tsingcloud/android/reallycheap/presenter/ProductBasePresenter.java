package tsingcloud.android.reallycheap.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.reallycheap.model.ProductBaseModel;
import tsingcloud.android.reallycheap.model.ProductBaseModelImpl;

/**
 * Created by admin on 2016/5/24.
 * 产品父类控制器
 */
public class ProductBasePresenter extends BasePresenter{

    private BaseView baseView;
    private ProductBaseModel productBaseModel;

    public ProductBasePresenter(BaseView baseView) {
        super(baseView);
        this.baseView = baseView;
        productBaseModel = new ProductBaseModelImpl();
    }
    /**
     * 添加产品到购物车
     *
     * @param productId 产品ID
     */
    public void addShoppingCart(String productId) {
        if (TextUtils.isEmpty(baseView.getToken()) || TextUtils.isEmpty(baseView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", baseView.getToken());
        map.put("shop_id", baseView.getShopId());
        map.put("product_id", productId);
        map.put("product_num", "1");
        productBaseModel.addShoppingCart(map, new AbstractOnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                baseView.showToast(response);
            }
        }, baseView.getTAG());
    }
}

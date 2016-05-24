package tsingcloud.android.reallycheap.classify.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.classify.model.ProductModel;
import tsingcloud.android.reallycheap.classify.model.ProductModelImpl;
import tsingcloud.android.reallycheap.classify.view.ProductView;
import tsingcloud.android.reallycheap.presenter.ProductBasePresenter;

/**
 * Created by admin on 2016/3/24.
 */
public class ProductPresenter extends ProductBasePresenter {

    private ProductView productView;
    private ProductModel productModel;

    public ProductPresenter(ProductView productView) {
        super(productView);
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


}

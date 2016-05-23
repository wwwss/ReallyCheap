package tsingcloud.android.reallycheap.classify.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.core.okhttp.OkHttpUtils;

/**
 * Created by admin on 2016/3/24.
 * 产品数据实现类
 */
public class ProductModelImpl implements ProductModel {
    private final String TAG = getClass().getName();

    @Override
    public void getProductList(String url, Map<String, String> map, final OnNSURLRequestListener<List<ProductBean>> listener, final OnSetListTotalPagesListener totalPagesListener,String tag) {
        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<ApiResponseBean<List<ProductBean>>>() {

            @Override
            public void onSuccess(ApiResponseBean<List<ProductBean>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList());
                    totalPagesListener.setTotalPages(response.getTotal_pages());
                } else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("获取产品列表失败");
            }
        }, map,tag);

    }

    @Override
    public void addShoppingCart(Map<String, String> map, final OnNSURLRequestListener<String> listener,String tag) {
        OkHttpUtils.post(Api.ADD_SHOPPING_CART, new OkHttpUtils.ResultCallback<ApiResponseBean<String>>() {
            @Override
            public void onSuccess(ApiResponseBean<String> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getErrmsg());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("添加购物车失败");
            }
        }, map,tag);
    }
}

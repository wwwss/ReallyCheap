package tsingcloud.android.reallycheap.classify.model;

import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.ProductBean;

/**
 * Created by admin on 2016/5/6.
 * 产品详情数据接口实现类
 */
public class ProductDetailsModelImpl implements ProductDetailsModel {
    @Override
    public void getProductDetailsData(String productId, Map<String, String> map, final OnNSURLRequestListener<ProductBean> listener, String tag) {
        OkHttpUtils.get(Api.PRODUCTS_DETAILS + productId, new OkHttpUtils.ResultCallback<ApiResponseBean<ProductBean>>() {
            @Override
            public void onSuccess(ApiResponseBean<ProductBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("获取产品详情失败");
            }
        },map, tag);

    }

    @Override
    public void collectionProduct(Map<String, String> map, final OnNSURLRequestListener<ProductBean> listener, String tag) {
        OkHttpUtils.post(Api.COLLECT_BABY, new OkHttpUtils.ResultCallback<ApiResponseBean<ProductBean>>() {
            @Override
            public void onSuccess(ApiResponseBean<ProductBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("收藏产品失败");
            }
        }, map, tag);

    }

    @Override
    public void cancelCollectionProduct(Map<String, String> map, final OnNSURLRequestListener<String> listener, String tag) {
        OkHttpUtils.delete(Api.COLLECT_BABY, new OkHttpUtils.ResultCallback<ApiResponseBean<String>>() {
            @Override
            public void onSuccess(ApiResponseBean<String> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getErrmsg());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("取消收藏失败");
            }
        }, map, tag);
    }

    @Override
    public void addShoppingCart(Map<String, String> map, final OnNSURLRequestListener<String> listener, String tag) {
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

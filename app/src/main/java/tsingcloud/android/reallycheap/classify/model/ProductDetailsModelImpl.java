package tsingcloud.android.reallycheap.classify.model;

import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.model.BaseModelImpl;

/**
 * Created by admin on 2016/5/6.
 * 产品详情数据接口实现类
 */
public class ProductDetailsModelImpl extends BaseModelImpl implements ProductDetailsModel {
    @Override
    public void getProductDetailsData(String productId, Map<String, String> map, OnNSURLRequestListener<ProductBean> listener, String tag) {
        OkHttpUtils.get(Api.PRODUCTS_DETAILS + productId, new AbstractResultCallback<ApiResponseBean<ProductBean>>(listener) {
            @Override
            public void onSuccess(ApiResponseBean<ProductBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else
                    listener.onFailure(response.getErrmsg());
            }
        }, map, tag);

    }

    @Override
    public void collectionProduct(Map<String, String> map, final OnNSURLRequestListener<ProductBean> listener, String tag) {
        OkHttpUtils.post(Api.COLLECT_BABY, new AbstractResultCallback<ApiResponseBean<ProductBean>>(listener) {
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
        OkHttpUtils.delete(Api.COLLECT_BABY, new AbstractResultCallback<ApiResponseBean<String>>(listener) {
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
}

package tsingcloud.android.reallycheap.model;

import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;

/**
 * Created by admin on 2016/5/24.
 * 产品父类Model实现类
 */
public class ProductBaseModelImpl extends BaseModelImpl implements ProductBaseModel {
    @Override
    public void addShoppingCart(Map<String, String> map, final OnNSURLRequestListener<String> listener, String tag) {
        OkHttpUtils.post(Api.ADD_SHOPPING_CART, new AbstractResultCallback<ApiResponseBean<String>>(listener) {
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
        }, map, tag);
    }
}

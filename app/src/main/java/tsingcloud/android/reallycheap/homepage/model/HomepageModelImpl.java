package tsingcloud.android.reallycheap.homepage.model;

import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.HomepageDataBean;

/**
 * Created by admin on 2016/4/13.
 * 获取首页数据
 */
public class HomepageModelImpl implements HomepageModel {
    @Override
    public void getHomepageData(String shopId, final OnNSURLRequestListener<HomepageDataBean> listener, String tag) {
        OkHttpUtils.get(Api.GET_HOMEPAGE_DATA + "shop_id=" + shopId, new OkHttpUtils.ResultCallback<ApiResponseBean<HomepageDataBean>>() {


            @Override
            public void onSuccess(ApiResponseBean<HomepageDataBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                //listener.onFailure("获取首页数据失败");
            }
        }, tag);

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
        }, map, tag);
    }


}

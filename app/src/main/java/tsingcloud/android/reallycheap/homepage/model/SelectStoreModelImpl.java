package tsingcloud.android.reallycheap.homepage.model;

import java.util.List;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.ShopBean;
import tsingcloud.android.reallycheap.model.BaseModelImpl;

/**
 * Created by admin on 2016/5/24.
 * 选择店面数据接口实现类
 */
public class SelectStoreModelImpl extends BaseModelImpl implements SelectStoreModel {
    @Override
    public void getShopsData(final OnNSURLRequestListener<List<ShopBean>> listener, String tag) {
        OkHttpUtils.get(Api.GET_SHOPS, new AbstractResultCallback<ApiResponseBean<List<ShopBean>>>(listener) {
            @Override
            public void onSuccess(ApiResponseBean<List<ShopBean>> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObjList());
                else
                    listener.onFailure(response.getErrmsg());
            }
        }, tag);
    }
}
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
//        ApiImpl api = retrofit.create(ApiImpl.class);
//        Call<ApiResponseBean<List<ShopBean>>> call = api.getShops();
//        call.enqueue(new Callback<ApiResponseBean<List<ShopBean>>>() {
//            @Override
//            public void onResponse(Call<ApiResponseBean<List<ShopBean>>> call, Response<ApiResponseBean<List<ShopBean>>> response) {
//                if (response.body().isSuccess())
//                    listener.onSuccess(response.body().getObjList());
//                else
//                    listener.onFailure(response.body().getErrmsg());
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponseBean<List<ShopBean>>> call, Throwable t) {
//                //listener.onFailure("获取店铺列表失败");
//            }
//        });

package tsingcloud.android.reallycheap.homepage.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tsingcloud.android.api.Api;
import tsingcloud.android.api.ApiImpl;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.ShopBean;

/**
 * Created by admin on 2016/5/24.
 */
public class SelectStoreModelImpl implements SelectStoreModel{
    @Override
    public void getShopsData(final OnNSURLRequestListener<List<ShopBean>> listener, String tag) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
        ApiImpl api = retrofit.create(ApiImpl.class);
        Call<ApiResponseBean<List<ShopBean>>> call = api.getShops();
        call.enqueue(new Callback<ApiResponseBean<List<ShopBean>>>() {
            @Override
            public void onResponse(Call<ApiResponseBean<List<ShopBean>>> call, Response<ApiResponseBean<List<ShopBean>>> response) {
                if (response.body().isSuccess())
                    listener.onSuccess(response.body().getObjList());
                else
                    listener.onFailure(response.body().getErrmsg());
            }

            @Override
            public void onFailure(Call<ApiResponseBean<List<ShopBean>>> call, Throwable t) {
                //listener.onFailure("获取店铺列表失败");
            }
        });
//        OkHttpUtils.get(Api.GET_SHOPS, new OkHttpUtils.ResultCallback<ApiResponseBean<List<ShopBean>>>() {
//
//            @Override
//            public void onSuccess(ApiResponseBean<List<ShopBean>> response) {
//                if (response.isSuccess())
//                    listener.onSuccess(response.getObjList());
//                else
//                    listener.onFailure(response.getErrmsg());
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                listener.onFailure("获取店铺列表失败");
//            }
//        },tag);
    }
}

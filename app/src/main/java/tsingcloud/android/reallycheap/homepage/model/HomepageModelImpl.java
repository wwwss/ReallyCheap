package tsingcloud.android.reallycheap.homepage.model;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tsingcloud.android.api.Api;
import tsingcloud.android.api.ApiImpl;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.HomepageDataBean;
import tsingcloud.android.model.bean.ShopBean;

/**
 * Created by admin on 2016/4/13.
 * 获取首页数据
 */
public class HomepageModelImpl implements HomepageModel {
    @Override
    public void getShopsData(final OnNSURLRequestListener<List<ShopBean>> listener,String tag) {
        System.out.println("--------------------------------------0");
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Api.SERVER).addConverterFactory(GsonConverterFactory.create()).build();
        ApiImpl api=retrofit.create(ApiImpl.class);
        Call<ApiResponseBean<List<ShopBean>>> call=api.getShops();
        call.enqueue(new Callback<ApiResponseBean<List<ShopBean>>>() {
            @Override
            public void onResponse(Call<ApiResponseBean<List<ShopBean>>> call, Response<ApiResponseBean<List<ShopBean>>> response) {
                System.out.println("--------------------------------------"+response.body());
                if (response.body().isSuccess())
                    listener.onSuccess(response.body().getObjList());
                else
                    listener.onFailure(response.body().getErrmsg());
                System.out.println("--------------------------------------1");
            }

            @Override
            public void onFailure(Call<ApiResponseBean<List<ShopBean>>> call, Throwable t) {
                listener.onFailure("获取店铺列表失败");
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

    @Override
    public void getHomepageData(String shopId, final OnNSURLRequestListener<HomepageDataBean> listener,String tag) {
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
                listener.onFailure("获取首页数据失败");
            }
        },tag);

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

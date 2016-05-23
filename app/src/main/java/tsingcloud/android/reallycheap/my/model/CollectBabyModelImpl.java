package tsingcloud.android.reallycheap.my.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.CollectBabyBean;
import tsingcloud.android.core.okhttp.OkHttpUtils;

/**
 * Created by admin on 2016/4/29.
 * 收藏宝贝数据实现类
 */
public class CollectBabyModelImpl implements CollectBabyModel {
    @Override
    public void getCollectBabyList(Map<String, String> map, final OnNSURLRequestListener<List<CollectBabyBean>> listener, final OnSetListTotalPagesListener totalPagesListener, String tag) {
        OkHttpUtils.get(Api.COLLECT_BABY, new OkHttpUtils.ResultCallback<ApiResponseBean<List<CollectBabyBean>>>() {
            @Override
            public void onSuccess(ApiResponseBean<List<CollectBabyBean>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList());
                    totalPagesListener.setTotalPages(response.getTotal_pages());
                } else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("获取收藏列表失败");
            }
        }, map, tag);

    }

    @Override
    public void deleteCollectBaby(Map<String, String> map, final OnNSURLRequestListener<String> listener, String tag) {
        OkHttpUtils.delete(Api.COLLECT_BABY, new OkHttpUtils.ResultCallback<ApiResponseBean<List<CollectBabyBean>>>() {
            @Override
            public void onSuccess(ApiResponseBean<List<CollectBabyBean>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getErrmsg());
                } else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("删除收藏宝贝失败");
            }
        }, map, tag);
    }

    @Override
    public void clearCollectBaby(Map<String, String> map, final OnNSURLRequestListener<String> listener, String tag) {
        OkHttpUtils.get(Api.DELETE_ALL_COLLECT_BABY, new OkHttpUtils.ResultCallback<ApiResponseBean<List<CollectBabyBean>>>() {
            @Override
            public void onSuccess(ApiResponseBean<List<CollectBabyBean>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getErrmsg());
                } else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("清空收藏宝贝失败");
            }
        }, map, tag);
    }
}

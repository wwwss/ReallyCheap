package tsingcloud.android.reallycheap.classify.model;

import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.ClassifyDataBean;
import tsingcloud.android.model.bean.SmallClassifyBean;
import tsingcloud.android.core.okhttp.OkHttpUtils;

/**
 * Created by admin on 2016/3/24.
 */
public class ClassifyModelImpl implements ClassifyModel {

    @Override
    public void getClassifyList(String shopId, String categoryId, final OnNSURLRequestListener<ClassifyDataBean> listener, String tag) {
        String url = Api.GET_CLASSIFY + "shop_id=" + shopId;
        if (!TextUtils.isEmpty(categoryId))
            url += "&category_id=" + categoryId;
        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<ApiResponseBean<ClassifyDataBean>>() {

            @Override
            public void onSuccess(ApiResponseBean<ClassifyDataBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                //listener.onFailure("获取分类列表失败");
            }
        }, tag);

    }

    @Override
    public void getSmallClassifyList(Map<String, String> map, final OnNSURLRequestListener<List<SmallClassifyBean>> listener, String tag) {
        OkHttpUtils.get(Api.GET_SMALL_CLASSIFY, new OkHttpUtils.ResultCallback<ApiResponseBean<List<SmallClassifyBean>>>() {

            @Override
            public void onSuccess(ApiResponseBean<List<SmallClassifyBean>> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObjList());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                //listener.onFailure("获取具体分类列表失败");
            }
        }, map, tag);
    }
}

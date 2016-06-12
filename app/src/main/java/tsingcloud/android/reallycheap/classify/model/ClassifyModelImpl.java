package tsingcloud.android.reallycheap.classify.model;

import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.ClassifyDataBean;
import tsingcloud.android.model.bean.SmallClassifyBean;
import tsingcloud.android.reallycheap.model.BaseModelImpl;

/**
 * Created by admin on 2016/3/24.
 */
public class ClassifyModelImpl extends BaseModelImpl implements ClassifyModel {

    @Override
    public void getClassifyList(String shopId, String categoryId, OnNSURLRequestListener<ClassifyDataBean> listener, String tag) {
        String url = Api.GET_CLASSIFY + "shop_id=" + shopId;
        if (!TextUtils.isEmpty(categoryId))
            url += "&category_id=" + categoryId;
        OkHttpUtils.get(url, new AbstractResultCallback<ApiResponseBean<ClassifyDataBean>>(listener) {
            @Override
            public void onSuccess(ApiResponseBean<ClassifyDataBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else
                    listener.onFailure(response.getErrmsg());
            }
        }, tag);

    }

    @Override
    public void getSmallClassifyList(Map<String, String> map, OnNSURLRequestListener<List<SmallClassifyBean>> listener, String tag) {
        OkHttpUtils.get(Api.GET_SMALL_CLASSIFY, new AbstractResultCallback<ApiResponseBean<List<SmallClassifyBean>>>(listener) {

            @Override
            public void onSuccess(ApiResponseBean<List<SmallClassifyBean>> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObjList());
                else
                    listener.onFailure(response.getErrmsg());
            }
        }, map, tag);
    }
}

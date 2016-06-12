package tsingcloud.android.reallycheap.homepage.model;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.HomepageDataBean;
import tsingcloud.android.reallycheap.model.BaseModelImpl;

/**
 * Created by admin on 2016/4/13.
 * 获取首页数据
 */
public class HomepageModelImpl extends BaseModelImpl implements HomepageModel {
    @Override
    public void getHomepageData(String shopId, OnNSURLRequestListener<HomepageDataBean> listener, String tag) {
        OkHttpUtils.get(Api.GET_HOMEPAGE_DATA + "shop_id=" + shopId, new AbstractResultCallback<ApiResponseBean<HomepageDataBean>>(listener) {
            @Override
            public void onSuccess(ApiResponseBean<HomepageDataBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else
                    listener.onFailure(response.getErrmsg());
            }
        }, tag);
    }

}

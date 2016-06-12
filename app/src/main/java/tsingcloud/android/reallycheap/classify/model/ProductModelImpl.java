package tsingcloud.android.reallycheap.classify.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.model.BaseModelImpl;

/**
 * Created by admin on 2016/3/24.
 * 产品数据实现类
 */
public class ProductModelImpl extends BaseModelImpl implements ProductModel {
    private final String TAG = getClass().getName();

    @Override
    public void getProductList(String url, Map<String, String> map, OnNSURLRequestListener<List<ProductBean>> listener, final OnSetListTotalPagesListener totalPagesListener, String tag) {
        OkHttpUtils.get(url, new AbstractResultCallback<ApiResponseBean<List<ProductBean>>>(listener) {

            @Override
            public void onSuccess(ApiResponseBean<List<ProductBean>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList());
                    totalPagesListener.setTotalPages(response.getTotal_pages());
                } else
                    listener.onFailure(response.getErrmsg());
            }
        }, map, tag);

    }


}

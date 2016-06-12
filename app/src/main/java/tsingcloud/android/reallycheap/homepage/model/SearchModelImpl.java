package tsingcloud.android.reallycheap.homepage.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.HotSearchBean;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.model.BaseModelImpl;

/**
 * Created by admin on 2016/3/24.
 * 搜索接口实现
 */
public class SearchModelImpl extends BaseModelImpl implements SearchModel {

    private final String TAG = getClass().getName();

    @Override
    public void getHotSearchData(OnNSURLRequestListener<List<HotSearchBean>> listener, String tag) {
        OkHttpUtils.get(Api.HOT_SEARCH, new AbstractResultCallback<ApiResponseBean<List<HotSearchBean>>>(listener) {
            @Override
            public void onSuccess(ApiResponseBean<List<HotSearchBean>> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObjList());
                else
                    listener.onFailure(response.getErrmsg());
            }
        }, tag);
    }

    @Override
    public void search(Map<String, String> map, final OnNSURLRequestListener<List<ProductBean>> listener, final OnSetListTotalPagesListener totalPagesListener,final ErrCodeListener errCodeListener, String tag) {
        OkHttpUtils.get(Api.SEARCH, new AbstractResultCallback<ApiResponseBean<List<ProductBean>>>(listener) {
            @Override
            public void onSuccess(ApiResponseBean<List<ProductBean>> response) {
                switch (response.getErrcode()) {
                    case "0":
                    case "1":
                        errCodeListener.setErrCode(response.getErrcode());
                        listener.onSuccess(response.getObjList());
                        totalPagesListener.setTotalPages(response.getTotal_pages());
                        break;
                    default:
                        listener.onFailure(response.getErrmsg());
                        break;

                }
            }
        }, map, tag);
    }
    public interface ErrCodeListener {
        void setErrCode(String errCode);
    }
}

package tsingcloud.android.reallycheap.homepage.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.core.utils.LogUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.HotSearchBean;
import tsingcloud.android.model.bean.ProductBean;

/**
 * Created by admin on 2016/3/24.
 * 搜索接口实现
 */
public class SearchModelImpl implements SearchModel {

    private final String TAG = getClass().getName();

    @Override
    public void getHotSearchData(final OnNSURLRequestListener<List<HotSearchBean>> listener, String tag) {
        OkHttpUtils.get(Api.HOT_SEARCH, new OkHttpUtils.ResultCallback<ApiResponseBean<List<HotSearchBean>>>() {
            @Override
            public void onSuccess(ApiResponseBean<List<HotSearchBean>> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObjList());
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("获取热门搜索失败");
                LogUtils.e(TAG, "获取热门搜索失败", e);
            }
        }, tag);
    }

    @Override
    public void search(Map<String, String> map, final OnNSURLRequestListener<List<ProductBean>> listener, final OnSetListTotalPagesListener totalPagesListener,final ErrCodeListener errCodeListener, String tag) {
        OkHttpUtils.get(Api.SEARCH, new OkHttpUtils.ResultCallback<ApiResponseBean<List<ProductBean>>>() {
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

            @Override
            public void onFailure(Exception e) {

            }
        }, map, tag);
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

    public interface ErrCodeListener {
        void setErrCode(String errCode);
    }
}

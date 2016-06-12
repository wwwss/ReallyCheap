package tsingcloud.android.reallycheap.homepage.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.model.bean.HotSearchBean;
import tsingcloud.android.model.bean.ProductBean;

/**
 * Created by admin on 2016/3/24.
 */
public interface SearchModel {
    void getHotSearchData(OnNSURLRequestListener<List<HotSearchBean>> listener, String tag);

    void search(Map<String, String> map, OnNSURLRequestListener<List<ProductBean>> listener, OnSetListTotalPagesListener totalPagesListener,SearchModelImpl.ErrCodeListener errCodeListener, String tag);

    //void addShoppingCart(Map<String, String> map, OnNSURLRequestListener<String> listener, String tag);


}

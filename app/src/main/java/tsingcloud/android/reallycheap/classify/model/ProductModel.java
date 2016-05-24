package tsingcloud.android.reallycheap.classify.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.model.bean.ProductBean;

/**
 * Created by admin on 2016/3/24.
 */
public interface ProductModel {
    void getProductList(String url,Map<String, String> map,OnNSURLRequestListener<List<ProductBean>> listener,OnSetListTotalPagesListener totalPagesListener,String tag);
}

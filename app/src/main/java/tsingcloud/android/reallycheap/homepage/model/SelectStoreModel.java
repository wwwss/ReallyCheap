package tsingcloud.android.reallycheap.homepage.model;

import java.util.List;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.ShopBean;

/**
 * Created by admin on 2016/5/24.
 */
public interface SelectStoreModel {
    void getShopsData(OnNSURLRequestListener<List<ShopBean>> listener, String tag);
}

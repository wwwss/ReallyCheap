package tsingcloud.android.reallycheap.homepage.model;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.HomepageDataBean;

/**
 * Created by admin on 2016/4/13.
 */
public interface HomepageModel {
  //  void addShoppingCart(Map<String, String> map, OnNSURLRequestListener<String> listener, String tag);

    void getHomepageData(String shopId, OnNSURLRequestListener<HomepageDataBean> listener, String tag);
//    void getBannerData(OnNSURLRequestListener<List<BannerBean>> listener);
//    void getHotClassifyData(OnNSURLRequestListener<List<ClassifyBean>> listener);
//    void getHotProductData(OnNSURLRequestListener<List<ProductBean>> listener);
}

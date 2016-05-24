package tsingcloud.android.reallycheap.homepage.presenter;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.HomepageDataBean;
import tsingcloud.android.reallycheap.homepage.model.HomepageModel;
import tsingcloud.android.reallycheap.homepage.model.HomepageModelImpl;
import tsingcloud.android.reallycheap.homepage.view.HomepageView;

/**
 * Created by admin on 2016/3/24.
 * 首页控制器
 */
public class HomepagePresenter extends SelectStorePresenter {
    private HomepageView homePageView;
    private HomepageModel homepageModel;
    public HomepagePresenter(HomepageView homePageView) {
        super(homePageView);
        this.homePageView = homePageView;
        homepageModel = new HomepageModelImpl();
    }
    public void getHomepageData(String shopId) {
        homepageModel.getHomepageData(shopId, new OnNSURLRequestListener<HomepageDataBean>() {

            @Override
            public void onSuccess(HomepageDataBean homepageDataBean) {
                homePageView.setBannerData(homepageDataBean.getAdvertlist());
                homePageView.setHotClassifyData(homepageDataBean.getCategorylist());
                homePageView.setHotProductData(homepageDataBean.getProductlist());
            }

            @Override
            public void onFailure(String msg) {
                homePageView.showToast(msg);
            }
        }, homePageView.getTAG());
    }



//    /**
//     * 添加产品到购物车
//     */
//    public void addShoppingCart(Map<String, String> map) {
//        if (TextUtils.isEmpty(homePageView.getToken()))
//            return;
//        map.put("token", homePageView.getToken());
//        if (TextUtils.isEmpty(homePageView.getShopId()))
//            return;
//        map.put("shop_id", homePageView.getShopId());
//        homepageModel.addShoppingCart(map, new OnNSURLRequestListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                homePageView.showToast(response);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                homePageView.showToast(msg);
//            }
//        }, homePageView.getTAG());
//    }




}

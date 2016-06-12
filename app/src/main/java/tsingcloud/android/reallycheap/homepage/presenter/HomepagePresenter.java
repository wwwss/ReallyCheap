package tsingcloud.android.reallycheap.homepage.presenter;

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
        homepageModel.getHomepageData(shopId, new AbstractOnNSURLRequestListener<HomepageDataBean>() {

            @Override
            public void onSuccess(HomepageDataBean homepageDataBean) {
                homePageView.setBannerData(homepageDataBean.getAdvertlist());
                homePageView.setHotClassifyData(homepageDataBean.getCategorylist());
                homePageView.setHotProductData(homepageDataBean.getProductlist());
            }
        }, homePageView.getTAG());
    }
}

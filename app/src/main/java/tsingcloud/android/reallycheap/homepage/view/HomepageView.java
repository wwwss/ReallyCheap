package tsingcloud.android.reallycheap.homepage.view;

import java.util.List;

import tsingcloud.android.model.bean.BannerBean;
import tsingcloud.android.model.bean.ClassifyBean;
import tsingcloud.android.model.bean.HotClassifyBean;

/**
 * Created by admin on 2016/3/24.
 * 首页接口
 */
public interface HomepageView extends SelectStoreView {


//    /**
//     * 设置首页数据
//     *
//     * @param homepageDataBeans 首页数据实体对象
//     */
//    void setHomepageData(HomepageDataBean homepageDataBeans);

    /**
     * 设置广告数据
     *
     * @param bannerBeans 广告实体对象列表
     */
    void setBannerData(List<BannerBean> bannerBeans);

    /**
     * 设置热门分类数据
     *
     * @param classifyBeans 热门分类实体对象列表
     */
    void setHotClassifyData(List<ClassifyBean> classifyBeans);

    /**
     * 设置热门产品数据
     *
     * @param hotClassifyBeans 热门产品实体对象列表
     */
    void setHotProductData(List<HotClassifyBean> hotClassifyBeans);

//    /**
//     * 去搜索页面
//     */
//    void toSearchActivity();


}

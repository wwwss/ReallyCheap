package tsingcloud.android.model.bean;

import java.util.List;

/**
 * Created by admin on 2016/4/18.
 * 首页数据实体对象
 */
public class HomepageDataBean extends BaseBean {

    private List<BannerBean> advertlist;

    private List<ClassifyBean> categorylist;

    private List<HotClassifyBean> productlist;

    public List<BannerBean> getAdvertlist() {
        return advertlist;
    }

    public void setAdvertlist(List<BannerBean> advertlist) {
        this.advertlist = advertlist;
    }

    public List<ClassifyBean> getCategorylist() {
        return categorylist;
    }

    public void setCategorylist(List<ClassifyBean> categorylist) {
        this.categorylist = categorylist;
    }

    public List<HotClassifyBean> getProductlist() {
        return productlist;
    }

    public void setProductlist(List<HotClassifyBean> productlist) {
        this.productlist = productlist;
    }
}

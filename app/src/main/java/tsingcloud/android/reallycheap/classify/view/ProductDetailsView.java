package tsingcloud.android.reallycheap.classify.view;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.ProductBean;

/**
 * Created by admin on 2016/3/24.
 * 产品详情页面接口
 */
public interface ProductDetailsView extends BaseView {
    /**
     * 设置产品详情数据
     *
     * @param productBean
     */
    void setProductDetailsData(ProductBean productBean);

    /**
     * 收藏成功
     */
    void collectionSuccess(String favoriteId);

    /**
     * 取消收藏成功
     */
    void cancelCollectionSuccess();
}

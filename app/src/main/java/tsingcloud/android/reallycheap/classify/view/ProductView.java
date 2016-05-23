package tsingcloud.android.reallycheap.classify.view;

import java.util.List;

import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.core.view.BaseView;

/**
 * Created by admin on 2016/3/24.
 */
public interface ProductView extends BaseView{
    void setProductsData(List<ProductBean> products);
    void toProductDetailsActivity(int position);
    void setTotalPages(int totalPages);
}

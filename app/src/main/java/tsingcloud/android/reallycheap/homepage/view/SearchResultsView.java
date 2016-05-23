package tsingcloud.android.reallycheap.homepage.view;

import java.util.List;

import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.core.view.BaseView;

/**
 * Created by admin on 2016/3/24.
 */
public interface SearchResultsView extends BaseView {
    String getSearchInput();

    void setSearchResultsData(List<ProductBean> productBeanList);

    void setTotalPages(int totalPages);

    void setErrCode(String errCode);

}

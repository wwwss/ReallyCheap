package tsingcloud.android.reallycheap.homepage.view;

import java.util.List;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.HotSearchBean;

/**
 * Created by admin on 2016/3/24.
 */
public interface SearchView extends BaseView {
 //   String getSearchInput();

    void setHotSearchData(List<HotSearchBean> list);

    void setHistorySearchData(List<String> list);

//    void searchComplete(List<ProductBean> productBeanList);

    void clearSearchHistory();

    void toSearchResultsActivity(String searchContent);

//    void setTotalPages(int totalPages);
}

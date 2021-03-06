package tsingcloud.android.reallycheap.homepage.presenter;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.homepage.model.SearchModel;
import tsingcloud.android.reallycheap.homepage.model.SearchModelImpl;
import tsingcloud.android.reallycheap.homepage.view.SearchResultsView;
import tsingcloud.android.reallycheap.presenter.ProductBasePresenter;

/**
 * Created by admin on 2016/3/24.
 * 搜索结果页面控制器
 */
public class SearchResultsPresenter extends ProductBasePresenter {
    private SearchResultsView searchResultsView;
    private SearchModel searchModel;

    public SearchResultsPresenter(SearchResultsView searchResultsView) {
        super(searchResultsView);
        this.searchResultsView = searchResultsView;
        searchModel = new SearchModelImpl();
    }


    public void search(String searchContent, int pageNum) {
        if (TextUtils.isEmpty(searchResultsView.getShopId())) return;
        Map<String, String> map = new HashMap<>();
        map.put("shop_id", searchResultsView.getShopId());
        map.put("page_num", pageNum + "");
        if (TextUtils.isEmpty(searchContent)) {
            searchResultsView.showToast("搜索内容不能为空");
            return;
        }
        map.put("name_like", searchContent);
        searchModel.search(map, new AbstractOnNSURLRequestListener<List<ProductBean>>() {
            @Override
            public void onSuccess(List<ProductBean> productBeanList) {
                searchResultsView.setSearchResultsData(productBeanList);
            }
        }, new OnSetListTotalPagesListener() {
            @Override
            public void setTotalPages(int totalPages) {
                searchResultsView.setTotalPages(totalPages);
            }
        }, new SearchModelImpl.ErrCodeListener() {
            @Override
            public void setErrCode(String errCode) {
                searchResultsView.setErrCode(errCode);
            }
        }, searchResultsView.getTAG());
    }

    public void saveHistorySearch(Context context, String searchContent) {
        JSONArray jsonArray = LocalCache.get(context).getAsJSONArray("historySearch");
        if (jsonArray == null)
            jsonArray = new JSONArray();
        List<String> historySearchList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            historySearchList.add(jsonArray.optString(i));
        }
        if (!historySearchList.contains(searchContent)) {
            jsonArray.put(searchContent);
            for (int i = jsonArray.length(); i > 10; i--) {
                jsonArray.remove(i);
            }
            LocalCache.get(context).put("historySearch", jsonArray);
        }
    }

}

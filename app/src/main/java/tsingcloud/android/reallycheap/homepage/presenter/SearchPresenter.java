package tsingcloud.android.reallycheap.homepage.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.iflytek.cloud.SpeechRecognizer;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.model.bean.HotSearchBean;
import tsingcloud.android.reallycheap.homepage.model.SearchModel;
import tsingcloud.android.reallycheap.homepage.model.SearchModelImpl;
import tsingcloud.android.reallycheap.homepage.view.SearchView;

/**
 * Created by admin on 2016/3/24.
 * 搜索页面控制器
 */
public class SearchPresenter extends BasePresenter{
    private SearchView searchView;
    private SearchModel searchModel;
    // 语音听写对象
    private SpeechRecognizer speechRecognizer;

    public SearchPresenter(SearchView searchView) {
        super(searchView);
        this.searchView = searchView;
        searchModel = new SearchModelImpl();
    }

    /**
     * 获取热门搜索
     */
    public void getHotSearchData() {
        searchModel.getHotSearchData(new AbstractOnNSURLRequestListener<List<HotSearchBean>>() {

            @Override
            public void onSuccess(List<HotSearchBean> response) {
                searchView.setHotSearchData(response);
            }
        }, searchView.getTAG());

    }
    /**
     * 获取历史搜索
     */
    public void getHistorySearchData(Context context){
     List<String>   historySearchList=new ArrayList<>();
        JSONArray jsonArray= LocalCache.get(context).getAsJSONArray("historySearch");
        if (jsonArray==null)
            return;
        for (int i = 0; i <jsonArray.length(); i++) {
            String historySearch=jsonArray.optString(i);
            if (TextUtils.isEmpty(historySearch))
                continue;
            historySearchList.add(historySearch);
        }
        searchView.setHistorySearchData(historySearchList);
    }



}

package tsingcloud.android.reallycheap.homepage.widgets.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.custom.vg.list.CustomListView;
import com.custom.vg.list.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.HotSearchBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.homepage.presenter.SearchPresenter;
import tsingcloud.android.reallycheap.homepage.view.SearchView;
import tsingcloud.android.reallycheap.homepage.widgets.adapter.HistorySearchAdapter;
import tsingcloud.android.reallycheap.homepage.widgets.adapter.HotSearchAdapter;
import tsingcloud.android.reallycheap.utils.ListViewUitls;

/**
 * Created by admin on 2016/3/24.
 * 搜索一级页面
 */
public class SearchActivity extends BaseActivity implements SearchView, View.OnClickListener {

    private EditText etSearchInput;
    private CustomListView hotSearchListView;
    private HotSearchAdapter hotSearchAdapter;
    private List<HotSearchBean> hotSearchList;
    private SearchPresenter searchPresenter;
    private ListView historySearchListView;
    private HistorySearchAdapter historySearchAdapter;
    private List<String> historySearchList;


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void setUpView() {
        searchPresenter = new SearchPresenter(this);
        findViewById(R.id.back).setOnClickListener(this);
        etSearchInput = (EditText) findViewById(R.id.searchInput);
        hotSearchListView = (CustomListView) findViewById(R.id.hotSearchListView);
        hotSearchListView.setDividerWidth(30);
        hotSearchListView.setDividerHeight(10);
        hotSearchList = new ArrayList<>();
        HotSearchBean hotSearchBean = new HotSearchBean();
        hotSearchBean.setName("");
        hotSearchList.add(hotSearchBean);
        hotSearchAdapter = new HotSearchAdapter(this, hotSearchList);
        hotSearchListView.setAdapter(hotSearchAdapter);
        historySearchListView = (ListView) findViewById(R.id.historySearchListView);
        historySearchList = new ArrayList<>();
        historySearchAdapter = new HistorySearchAdapter(this, historySearchList);
        historySearchListView.setAdapter(historySearchAdapter);
        findViewById(R.id.clearSearchHistory).setOnClickListener(this);
        setSearchListener();

    }

    private void setSearchListener() {
        hotSearchListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchContent = hotSearchList.get(position).getName();
                toSearchResultsActivity(searchContent);
            }
        });
        historySearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchContent = (String) parent.getItemAtPosition(position);
                toSearchResultsActivity(searchContent);
            }
        });
        etSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    String searchContent = etSearchInput.getText().toString();
                    if (TextUtils.isEmpty(searchContent)) {
                        return false;
                    }
                    toSearchResultsActivity(searchContent);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void setUpData() {
        searchPresenter.getHotSearchData();
    }

    @Override
    public void onResume() {
        super.onResume();
        searchPresenter.getHistorySearchData(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clearSearchHistory:
                clearSearchHistory();
                break;
        }
    }

    @Override
    public void setHotSearchData(List<HotSearchBean> list) {
        if (list != null) {
            hotSearchList.clear();
            hotSearchList.addAll(list);
            hotSearchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setHistorySearchData(List<String> list) {
        if (list.size() > 0) {
            historySearchList.clear();
            historySearchList.addAll(list);
            historySearchAdapter.notifyDataSetChanged();
            findViewById(R.id.clearSearchHistory).setVisibility(View.VISIBLE);
            ListViewUitls.setListViewHeightBasedOnChildren(historySearchListView);
        }
    }

    @Override
    public void clearSearchHistory() {
        //清除缓存
        historySearchList.clear();
        historySearchAdapter.notifyDataSetChanged();
        findViewById(R.id.clearSearchHistory).setVisibility(View.GONE);
        LocalCache.get(this).remove("historySearch");

    }

    @Override
    public void toSearchResultsActivity(String searchContent) {
        Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);
        intent.putExtra("searchContent", searchContent);
        startActivity(intent);
    }
}

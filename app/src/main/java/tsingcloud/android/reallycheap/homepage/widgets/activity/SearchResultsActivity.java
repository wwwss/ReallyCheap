package tsingcloud.android.reallycheap.homepage.widgets.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.classify.widgets.activity.ProductDetailsActivity;
import tsingcloud.android.reallycheap.classify.widgets.adapter.ProductAdapter;
import tsingcloud.android.reallycheap.homepage.presenter.SearchResultsPresenter;
import tsingcloud.android.reallycheap.homepage.view.SearchResultsView;

/**
 * Created by admin on 2016/3/24.
 * 搜索结果
 */
public class SearchResultsActivity extends BaseActivity implements TextView.OnEditorActionListener, SearchResultsView, View.OnClickListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private static final int PRODUCT_DETAILS = 1001;
    private EditText etSearchInput;
    private SearchResultsPresenter searchResultsPresenter;
    private List<ProductBean> productBeanList;
    private ListView listView;
    private int pageNum = 1;
    private int totalPages;
    private String searchContent;
    private ProductAdapter adapter;
    private String errCode;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_search_results);
    }

    @Override
    protected void setUpView() {
        searchResultsPresenter = new SearchResultsPresenter(this);
        findViewById(R.id.back).setOnClickListener(this);
        etSearchInput = (EditText) findViewById(R.id.searchInput);
        etSearchInput.setOnEditorActionListener(this);
        listView = (ListView) findViewById(R.id.searchResultsListView);
        listView.setOnScrollListener(this);
        productBeanList = new ArrayList<>();
        adapter = new ProductAdapter(this, productBeanList, searchResultsPresenter, ProductAdapter.PresenterEnum.SEARCH);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void setUpData() {
        searchContent = getIntent().getStringExtra("searchContent");
        searchResultsPresenter.saveHistorySearch(this,searchContent);
        searchResultsPresenter.search(getSearchInput(), pageNum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            InputMethodManager imm = (InputMethodManager) v.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
            String searchContent = getSearchInput();
            if (TextUtils.isEmpty(searchContent)) {
                return false;
            }
            searchResultsPresenter.saveHistorySearch(this,searchContent);
            pageNum = 1;
            productBeanList.clear();
            searchResultsPresenter.search(searchContent, pageNum);
            return true;
        }
        return false;
    }

    @Override
    public String getSearchInput() {
        if (TextUtils.isEmpty(etSearchInput.getText().toString()))
            return searchContent;
        else
            return etSearchInput.getText().toString();
    }

    @Override
    public void setSearchResultsData(List<ProductBean> productBeanList) {
        if ("0".equals(errCode) && productBeanList != null && productBeanList.size() > 0) {
            findViewById(R.id.noSearchResultsHint).setVisibility(View.GONE);
        } else {
            findViewById(R.id.noSearchResultsHint).setVisibility(View.VISIBLE);
        }
        this.productBeanList.addAll(productBeanList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 当不滚动时
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                // 加载更多功能的代码
                if (pageNum <= totalPages) {
                    pageNum++;
                    searchResultsPresenter.search(getSearchInput(), pageNum);
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("id", productBeanList.get(position).getId());
        startActivityForResult(intent, PRODUCT_DETAILS);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case PRODUCT_DETAILS:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
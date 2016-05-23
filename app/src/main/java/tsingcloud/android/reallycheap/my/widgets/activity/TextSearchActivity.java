package tsingcloud.android.reallycheap.my.widgets.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;

import tsingcloud.android.core.widgets.activity.BaseListActivity;
import tsingcloud.android.core.widgets.pull.BaseViewHolder;
import tsingcloud.android.core.widgets.pull.PullRecycler;
import tsingcloud.android.reallycheap.R;

/**
 * Created by admin on 2016/5/5.
 * 文字搜索页面
 */
public class  TextSearchActivity extends BaseListActivity<PoiItem> implements PoiSearch.OnPoiSearchListener {

    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private PoiResult poiResult;
    // 当前页面，从0开始计数
    private int currentPage = 0;
    private PullRecycler recycler;
    protected String address;
    private EditText etSearchInput;
    private Intent intent;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_text_search);
    }

    @Override
    protected void setUpView() {
        recycler = (PullRecycler) findViewById(R.id.pullRecycler);
        etSearchInput = (EditText) findViewById(R.id.searchInput);
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
                    doSearchQuery(searchContent);
                    return true;
                }
                return false;
            }
        });
        etSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString()))
                    doSearchQuery(s.toString());
            }
        });

    }

    /**
     * 开始进行poi搜索
     */
    private void doSearchQuery(String keyWord) {
        currentPage = 0;
        mDataList.clear();
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(keyWord, "12", "021");
        query.setPageSize(10);// 设置每页最多返回多少条
        query.setPageNum(currentPage);// 设置查第一页
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_seach_location, parent, false);
        return new PoiViewHolder(view);
    }

    @Override
    public void onRefresh(int action) {
        if (poiResult.getPageCount() - 1 > currentPage) {
            currentPage++;
            // 设置查后一页
            query.setPageNum(currentPage);
            poiSearch.searchPOIAsyn();
            recycler.enableLoadMore(true);
        } else {
            recycler.enableLoadMore(false);
            recycler.onRefreshCompleted();
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        this.poiResult = poiResult;
        recycler.onRefreshCompleted();
        if (i == 1000) {
            // 搜索poi的结果
            if (poiResult != null && poiResult.getQuery() != null) {
                // 是否是同一条poiResult = poiResult;
                if (poiResult.getQuery().equals(query)) {
                    mDataList.addAll(poiResult.getPois());
                    if (mDataList.size() > 0)
                        adapter.notifyDataSetChanged();
                }
            }
        } else {
            showToast("搜索失败，请重新输入地址");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    protected void setUpData() {
        adapter = new ListAdapter();
        mDataList = new ArrayList<>();
        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        recycler.setAdapter(adapter);
        recycler.enablePullToRefresh(false);
        recycler.enableLoadMore(true);
    }

    private class PoiViewHolder extends BaseViewHolder {
        public TextView tvName;// 地名
        public TextView tvAddress;// 详细地址

        public PoiViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvAddress = (TextView) itemView.findViewById(R.id.address);
        }

        @Override
        public void onBindViewHolder(int position) {
            PoiItem poiItem = mDataList.get(position);
            tvName.setText(poiItem.getTitle());
            tvAddress.setText(poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet());
        }

        @Override
        public void onItemClick(View view, int position) {
            PoiItem poiItem = mDataList.get(position);
            intent = new Intent();
            intent.putExtra("address", poiItem.getTitle());
            intent.putExtra("lat", poiItem.getLatLonPoint().getLatitude());
            intent.putExtra("lng", poiItem.getLatLonPoint().getLongitude());
            finish();
        }

    }

    @Override
    public void finish() {
        setResult(RESULT_OK, intent);
        super.finish();
    }
}

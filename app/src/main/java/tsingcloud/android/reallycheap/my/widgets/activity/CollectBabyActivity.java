package tsingcloud.android.reallycheap.my.widgets.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.CollectBabyBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.classify.widgets.activity.ProductDetailsActivity;
import tsingcloud.android.reallycheap.my.presenter.CollectBabyPresenter;
import tsingcloud.android.reallycheap.my.view.CollectBabyView;
import tsingcloud.android.reallycheap.my.widgets.adapter.CollectBabyAdapter;
import tsingcloud.android.reallycheap.widgets.view.SilderListView;

public class CollectBabyActivity extends BaseActivity implements OnItemClickListener, OnScrollListener, CollectBabyView {

    private int pageNum = 1;
    private SilderListView silderListView;
    private List<CollectBabyBean> collectBabyBeanList;
    private CollectBabyAdapter adapter;
    private int totalPages;
    private CollectBabyPresenter collectBabyPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_collect_baby, -1);
    }

    @Override
    protected void setUpView() {
        collectBabyPresenter = new CollectBabyPresenter(this);
        titleBar.setTitle(R.string.collection_product);
        titleBar.setRightText(R.string.empty);
        silderListView = (SilderListView) findViewById(R.id.listView);
        collectBabyBeanList = new ArrayList<>();
        adapter = new CollectBabyAdapter(this, collectBabyBeanList, collectBabyPresenter);
        silderListView.setAdapter(adapter);
        silderListView.setOnItemClickListener(this);
        silderListView.setOnScrollListener(this);
        silderListView.setEmptyView(findViewById(R.id.empty));
    }

    @Override
    protected void setUpData() {
        collectBabyPresenter.getCollectBabyList(pageNum);
    }

    @Override
    public void clickRight() {
        collectBabyPresenter.clearCollectBaby();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CollectBabyBean collectBabyBean = (CollectBabyBean) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("id", collectBabyBean.getProduct_id());
        startActivity(intent);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 当不滚动时
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                // 加载更多功能的代码
                if (pageNum <= totalPages) {
                    pageNum++;
                    collectBabyPresenter.getCollectBabyList(pageNum);
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void setCollectBabyList(List<CollectBabyBean> collectBabyBeans) {
        if (null == collectBabyBeans || collectBabyBeans.size() == 0)
            return;
        collectBabyBeanList.addAll(collectBabyBeans);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void clearCollectComplete() {
        collectBabyBeanList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteCollectComplete(int position) {
        collectBabyBeanList.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

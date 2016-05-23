package tsingcloud.android.reallycheap.my.widgets.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.presenter.MyOrderPresenter;
import tsingcloud.android.reallycheap.my.view.MyOrderView;
import tsingcloud.android.reallycheap.my.widgets.adapter.MyOrderAdapter;

/**
 * Created by admin on 2016/4/29.
 * 我的订单页面
 */
public class MyOrderActivity extends BaseActivity implements View.OnClickListener, MyOrderView, AbsListView.OnScrollListener {

    private TextView tvAll;
    private TextView tvStayPay;
    private TextView tvStayReceiving;
    private TextView tvCompleted;
    private TextView tvRefund;
    private TextView[] textViews;
    private ListView listView;
    private List<OrderBean> orderBeanList;
    private MyOrderAdapter adapter;
    private MyOrderPresenter myOrderPresenter;
    private int pageNum = 1;
    private int totalPages;
    /**
     * 订单状态：
     * paying待付款receipt待收货
     * finished已完成refund退款
     * 不传表示全部订单
     */
    private int orderStatus;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_my_order, R.string.my_order);
    }

    @Override
    protected void setUpView() {
        myOrderPresenter = new MyOrderPresenter(this);
        tvAll = (TextView) findViewById(R.id.all);
        tvStayPay = (TextView) findViewById(R.id.stayPay);
        tvStayReceiving = (TextView) findViewById(R.id.stayReceiving);
        tvCompleted = (TextView) findViewById(R.id.completed);
        tvRefund = (TextView) findViewById(R.id.refund);
        textViews = new TextView[]{tvAll, tvStayPay, tvStayReceiving, tvCompleted, tvRefund};
        tvAll.setOnClickListener(this);
        tvStayPay.setOnClickListener(this);
        tvStayReceiving.setOnClickListener(this);
        tvCompleted.setOnClickListener(this);
        tvRefund.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnScrollListener(this);
        orderBeanList = new ArrayList<>();
        adapter = new MyOrderAdapter(this, orderBeanList, this, myOrderPresenter);
        listView.setAdapter(adapter);
    }

    @Override
    protected void setUpData() {
        orderStatus = getIntent().getIntExtra("orderStatus", 0);
        refreshOrderList(orderStatus);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all:
                refreshOrderList(0);
                break;
            case R.id.stayPay:
                refreshOrderList(1);
                break;
            case R.id.stayReceiving:
                refreshOrderList(2);
                break;
            case R.id.completed:
                refreshOrderList(3);
                break;
            case R.id.refund:
                refreshOrderList(4);
                break;

        }
    }

    private void refreshOrderList(int orderStatus) {
        this.orderStatus = orderStatus;
        switch (orderStatus) {
            case 0:
                refreshSelected(tvAll);
                break;
            case 1:
                refreshSelected(tvStayPay);
                break;
            case 2:
                refreshSelected(tvStayReceiving);
                break;
            case 3:
                refreshSelected(tvCompleted);
                break;
            case 4:
                refreshSelected(tvRefund);
                break;
        }
    }

    private void refreshSelected(TextView textView) {
        for (int i = 0; i < textViews.length; i++) {
            if (textView.getId() == textViews[i].getId())
                textViews[i].setSelected(true);
            else
                textViews[i].setSelected(false);
        }
        pageNum = 1;
        myOrderPresenter.getOrderList(pageNum);
    }

    @Override
    public void refreshOrderListCompleted(List<OrderBean> orderBeans) {
        if (null == orderBeans)
            return;
        if (pageNum == 1)
            orderBeanList.clear();
        orderBeanList.addAll(orderBeans);
        adapter.notifyDataSetChanged();
        handler.sendEmptyMessage(1);

    }

    @Override
    public int getOrderStatus() {
        return orderStatus;
    }

    @Override
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public void confirmReceiptComplete() {
        pageNum = 1;
        myOrderPresenter.getOrderList(pageNum);
    }

    @Override
    public void payComplete() {

    }

    @Override
    public Activity getActivity() {
        return this;
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
                    myOrderPresenter.getOrderList(pageNum);
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case MyOrderAdapter.ORDER_DETAILS:
                pageNum = 1;
                myOrderPresenter.getOrderList(pageNum);
                break;
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    boolean isNeedCountTime = false;
                    //①：其实在这块需要精确计算当前时间
                    for (int index = 0; index < orderBeanList.size(); index++) {
                        OrderBean orderBean = orderBeanList.get(index);
                        int time = orderBean.getExpiration_time();
                        if (time > 0) {//判断是否还有条目能够倒计时，如果能够倒计时的话，延迟一秒，让它接着倒计时
                            isNeedCountTime = true;
                            orderBean.setExpiration_time(time - 1);
                        } else {
                            orderBean.setExpiration_time(0);
                        }
                    }
                    //②：for循环执行的时间
                    adapter.notifyDataSetChanged();
                    if (isNeedCountTime) {
                        //TODO 然后用1000-（②-①），就赢延迟的时间
                        handler.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
            }
        }

    };
}

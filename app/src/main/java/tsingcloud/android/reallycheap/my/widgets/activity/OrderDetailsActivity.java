package tsingcloud.android.reallycheap.my.widgets.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import tsingcloud.android.core.utils.LogUtils;
import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.presenter.OrderDetailsPresenter;
import tsingcloud.android.reallycheap.my.view.OrderDetailsView;
import tsingcloud.android.reallycheap.shoppingcart.widgets.adapter.OrderProductAdapter;
import tsingcloud.android.reallycheap.utils.ListViewUtils;

/**
 * Created by admin on 2016/5/3.
 * 订单详情页面
 */
public class OrderDetailsActivity extends BaseActivity implements OrderDetailsView {

    private TextView tvOrderStatus;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvShopName;
    private ListView listView;
    private TextView tvProductsPrice;
    private TextView tvFreight;
    private TextView tvCopeWith;
    private TextView tvOrderNum;
    private TextView tvOrderTime;
    private TextView tvPayWay;
    private TextView tvRemarks;
    private OrderDetailsPresenter orderDetailsPresenter;
    private String orderId;
    private OrderProductAdapter adapter;
    private OrderBean orderBean;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_order_details, R.string.order_details);
    }

    @Override
    protected void setUpView() {
        orderDetailsPresenter = new OrderDetailsPresenter(this);
        tvOrderStatus = (TextView) findViewById(R.id.orderStatus);
        tvName = (TextView) findViewById(R.id.name);
        tvAddress = (TextView) findViewById(R.id.address);
        tvShopName = (TextView) findViewById(R.id.shopName);
        tvProductsPrice = (TextView) findViewById(R.id.productsPrice);
        tvFreight = (TextView) findViewById(R.id.freight);
        tvCopeWith = (TextView) findViewById(R.id.copeWith);
        tvOrderNum = (TextView) findViewById(R.id.orderNum);
        tvOrderTime = (TextView) findViewById(R.id.orderTime);
        tvPayWay = (TextView) findViewById(R.id.payWay);
        tvRemarks = (TextView) findViewById(R.id.remarks);
        listView = (ListView) findViewById(R.id.listView);
        findViewById(R.id.callPhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    PermissionGen.with(OrderDetailsActivity.this)
                            .addRequestCode(100)
                            .permissions(Manifest.permission.CALL_PHONE)
                            .request();
                } else {
                    openCallPhone();
                }
            }
        });
    }

    @Override
    protected void setUpData() {
        orderId = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(orderId))
            return;
        orderDetailsPresenter.getOrderDetailsData(orderId);
    }

    @Override
    public void setOrderData(OrderBean orderBean) {
        this.orderBean = orderBean;
        if (orderBean == null)
            return;
        switch (orderBean.getState()) {
            case "0":
                tvOrderStatus.setText("待付款");
                break;
            case "1":
                tvOrderStatus.setText("货到付款");
                break;
            case "2":
                tvOrderStatus.setText("已支付待收货");
                break;
            case "3":
                tvOrderStatus.setText("交易成功");
                titleBar.setRightDrawable(R.drawable.delete_grey_icon);
                break;
            case "4":
                tvOrderStatus.setText("退款中");
                break;
            case "5":
                tvOrderStatus.setText("交易关闭");
                titleBar.setRightDrawable(R.drawable.delete_grey_icon);
                break;
            case "6":
                if (orderBean.getOrder_type() == 0)
                    tvOrderStatus.setText("货到付款未接单");
                else if (orderBean.getOrder_type() == 1)
                    tvOrderStatus.setText("已支付未接单");
                break;
        }
        tvName.setText(orderBean.getReceive_name());
        tvAddress.setText(orderBean.getArea() + orderBean.getDetail());
        tvShopName.setText(orderBean.getShop_name());
        if (orderBean.getProducts() != null && orderBean.getProducts().size() > 0) {
            adapter = new OrderProductAdapter(this, orderBean.getProducts());
            listView.setAdapter(adapter);
            ListViewUtils.setListViewHeightBasedOnChildren(listView);
        }
        tvProductsPrice.setText("¥" + orderBean.getTotal_price());
        tvFreight.setText("¥" + orderBean.getFreight());
        tvCopeWith.setText("¥" + (orderBean.getTotal_price() + orderBean.getFreight()));
        tvOrderNum.setText("订单编号：" + orderBean.getOrder_no());
        tvOrderTime.setText("订单时间：" + orderBean.getCreated_at());
        if (orderBean.getOrder_type() == 0)
            tvPayWay.setText("支付方式：货到付款");
        else if (orderBean.getOrder_type() == 1 || orderBean.getOrder_type() == 2)
            tvPayWay.setText("支付方式：支付宝");
        if (!TextUtils.isEmpty(orderBean.getRemarks()))
            tvRemarks.setText("备注：" + orderBean.getRemarks());
        else
            tvRemarks.setText("备注：");
    }

    @Override
    public void deleteOrderComplete() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void clickRight() {
        //super.clickRight();
        if (orderBean == null)
            return;
        orderDetailsPresenter.deleteOrder(orderBean.getId());
    }

    @PermissionSuccess(requestCode = 100)
    public void openCallPhone() {
        LogUtils.d(TAG, "=============================");
        if (orderBean == null)
            return;
        if (!TextUtils.isEmpty(orderBean.getShop_phone())) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + orderBean.getShop_phone()));
            if (intent.resolveActivity(this.getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    @PermissionFail(requestCode = 100)
    public void failCallPhone() {
        LogUtils.d(TAG, "------------------------------");
        showToast("权限请求被拒绝");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}

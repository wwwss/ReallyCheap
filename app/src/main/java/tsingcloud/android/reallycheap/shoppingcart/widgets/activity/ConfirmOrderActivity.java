package tsingcloud.android.reallycheap.shoppingcart.widgets.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.widgets.activity.MyOrderActivity;
import tsingcloud.android.reallycheap.shoppingcart.presenter.ConfirmOrderPresenter;
import tsingcloud.android.reallycheap.shoppingcart.view.ConfirmOrderView;
import tsingcloud.android.reallycheap.shoppingcart.widgets.adapter.OrderProductAdapter;
import tsingcloud.android.reallycheap.utils.ListViewUitls;

/**
 * Created by admin on 2016/3/28.
 * 确认订单页面
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener, ConfirmOrderView {

    private ScrollView scrollView;
    private TextView tvName;//收货人姓名
    private TextView tvPhoneNumber;//收货人手机号码
    private TextView tvAddressInfo;//收货人地址信息
    private ImageView ivZfb;//支付宝
    private ImageView ivCashOnDelivery;//货到付款
    private TextView tvProductsPrice;//商品金额
    private TextView tvFreight;//运费
    private TextView tvCopeWith;//应付
    private TextView tvPayPrice;//应付金额
    private TextView tvGoPay;//去付款&提交订单
    private ListView listView;
    protected OrderBean orderBean;
    protected OrderProductAdapter adapter;
    protected ConfirmOrderPresenter confirmOrderPresenter;


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_confirm_order, R.string.confirm_order);
    }

    @Override
    protected void setUpView() {
        confirmOrderPresenter = new ConfirmOrderPresenter(this);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        tvName = (TextView) findViewById(R.id.name);
        tvPhoneNumber = (TextView) findViewById(R.id.phoneNumber);
        tvAddressInfo = (TextView) findViewById(R.id.addressInfo);
        listView = (ListView) findViewById(R.id.shoppingCartListView);
        tvProductsPrice = (TextView) findViewById(R.id.totalPrice);
        ivZfb = (ImageView) findViewById(R.id.zfb);
        ivCashOnDelivery = (ImageView) findViewById(R.id.cashOnDelivery);
        tvProductsPrice = (TextView) findViewById(R.id.productsPrice);
        tvFreight = (TextView) findViewById(R.id.freight);
        tvCopeWith = (TextView) findViewById(R.id.copeWith);
        tvPayPrice = (TextView) findViewById(R.id.payPrice);
        tvGoPay = (TextView) findViewById(R.id.goPay);
        ivZfb.setOnClickListener(this);
        ivCashOnDelivery.setOnClickListener(this);
        tvGoPay.setOnClickListener(this);
    }

    @Override
    protected void setUpData() {
        orderBean = (OrderBean) getIntent().getSerializableExtra("orderBean");
        if (orderBean == null)
            return;
        tvName.setText(orderBean.getReceive_name());
        tvPhoneNumber.setText(orderBean.getReceive_phone());
        tvAddressInfo.setText(orderBean.getArea() + orderBean.getDetail());
        adapter = new OrderProductAdapter(this, orderBean.getProducts());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ListViewUitls.setListViewHeightBasedOnChildren(listView);
        ivZfb.setSelected(true);
        tvProductsPrice.setText("¥" + orderBean.getTotal_price());
        tvFreight.setText("¥" + orderBean.getFreight());
        tvCopeWith.setText("¥" + orderBean.getTotal_price());
        tvPayPrice.setText("应付金额：¥" + orderBean.getTotal_price());
        scrollView.scrollTo(0, 0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zfb:
            case R.id.cashOnDelivery:
                ivZfb.setSelected(!ivZfb.isSelected());
                ivCashOnDelivery.setSelected(!ivCashOnDelivery.isSelected());
                if (ivZfb.isSelected()) {
                    orderBean.setOrder_type(1);
                    tvGoPay.setText("去付款");
                } else {
                    orderBean.setOrder_type(0);
                    tvGoPay.setText("提交订单");
                }
                break;
            case R.id.goPay:
                if ("提交订单".equals(tvGoPay.getText().toString()))
                    orderBean.setOrder_type(0);
                else
                    orderBean.setOrder_type(1);
                confirmOrderPresenter.submitOrder(orderBean);
                break;
        }
    }


    @Override
    public void submitOrderComplete(String orderId) {
        if ("提交订单".equals(tvGoPay.getText().toString())) {
            //去订单列表
            Intent intent = new Intent(this, MyOrderActivity.class);
            intent.putExtra("orderStatus", 2);
            startActivity(intent);
            finish();
        } else {
            //调起支付
            confirmOrderPresenter.goPay(orderId, ConfirmOrderActivity.this);
        }
    }

    @Override
    public void payComplete() {
        //去订单列表
        Intent intent = new Intent(this, MyOrderActivity.class);
        intent.putExtra("orderStatus", 2);
        startActivity(intent);
        finish();
    }
}

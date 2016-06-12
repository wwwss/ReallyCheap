package tsingcloud.android.reallycheap.shoppingcart.widgets.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.model.bean.ShopBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.widgets.activity.MyOrderActivity;
import tsingcloud.android.reallycheap.shoppingcart.presenter.ConfirmOrderPresenter;
import tsingcloud.android.reallycheap.shoppingcart.view.ConfirmOrderView;
import tsingcloud.android.reallycheap.shoppingcart.widgets.adapter.OrderProductAdapter;
import tsingcloud.android.reallycheap.utils.ListViewUtils;

/**
 * Created by admin on 2016/3/28.
 * 确认订单页面
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener, ConfirmOrderView {

    private static final int REMARKS = 1001;
    private ScrollView scrollView;
    private TextView tvName;//收货人姓名
    private TextView tvPhoneNumber;//收货人手机号码
    private TextView tvAddressInfo;//收货人地址信息
    private ImageView ivZfb;//支付宝
    private ImageView ivCashOnDelivery;//货到付款
    private TextView tvBusinessHours;//营业时间
    private ImageView ivTakeTheir;//到店自提
    private TextView tvRemarks;//商品金额
    //    private TextView tvFreight;//运费
//    private TextView tvCopeWith;//应付
    private TextView tvPayPrice;//应付金额
    private TextView tvGoPay;//去付款&提交订单
    private ListView listView;
    private OrderBean orderBean;
    private OrderProductAdapter adapter;
    private ConfirmOrderPresenter confirmOrderPresenter;
    private ShopBean shopBean;
    //   private double totalPrice;


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
        ivZfb = (ImageView) findViewById(R.id.zfb);
        ivCashOnDelivery = (ImageView) findViewById(R.id.cashOnDelivery);
        tvBusinessHours = (TextView) findViewById(R.id.businessHours);
        ivTakeTheir = (ImageView) findViewById(R.id.takeTheir);
        tvRemarks = (TextView) findViewById(R.id.remarks);
//        tvFreight = (TextView) findViewById(R.id.freight);
//        tvCopeWith = (TextView) findViewById(R.id.copeWith);
        tvPayPrice = (TextView) findViewById(R.id.payPrice);
        tvGoPay = (TextView) findViewById(R.id.goPay);
        ivZfb.setOnClickListener(this);
        ivCashOnDelivery.setOnClickListener(this);
        ivTakeTheir.setOnClickListener(this);
        tvGoPay.setOnClickListener(this);
        tvRemarks.setOnClickListener(this);
        findViewById(R.id.enter).setOnClickListener(this);
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
        ListViewUtils.setListViewHeightBasedOnChildren(listView);
//        tvFreight.setText("¥" + orderBean.getFreight());
//        tvCopeWith.setText("¥" + orderBean.getTotal_price());
        shopBean = (ShopBean) LocalCache.get(this).getAsObject("shopBean");
        if (shopBean == null) {
            showToast("请重新选择店铺");
            return;
        }
        tvBusinessHours.setText("到店自提（" + shopBean.getBusiness_hours() + ")");
        scrollView.scrollTo(0, 0);
        setOrderType(1);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zfb:
                setOrderType(1);
                break;
            case R.id.cashOnDelivery:
                setOrderType(0);
                break;
            case R.id.takeTheir:
                setOrderType(2);
                break;
            case R.id.remarks:
            case R.id.enter:
                Intent intent = new Intent(this, RemarksActivity.class);
                intent.putExtra("remarks", orderBean.getRemarks());
                startActivityForResult(intent, REMARKS);
                break;
            case R.id.goPay:
                confirmOrderPresenter.submitOrder(orderBean);
                break;
        }
    }

    /**
     * @param type 0货到付款 1支付宝 2到店自提
     */
    private void setOrderType(int type) {
        orderBean.setOrder_type(type);
        switch (type) {
            case 1:
                ivZfb.setSelected(true);
                ivCashOnDelivery.setSelected(false);
                ivTakeTheir.setSelected(false);
                tvGoPay.setText("去付款");
                break;
            case 0:
                ivZfb.setSelected(false);
                ivCashOnDelivery.setSelected(true);
                ivTakeTheir.setSelected(false);
                tvGoPay.setText("提交订单");
                break;
            case 2:
                ivZfb.setSelected(false);
                ivCashOnDelivery.setSelected(false);
                ivTakeTheir.setSelected(true);
                tvGoPay.setText("去付款");
                break;
        }
        setTotalPrice(type);
    }

    private void setTotalPrice(int type) {
        if (type == 0 || type == 1) {
            if (orderBean.getProducts_price() >= shopBean.getSend_price()) {
                //totalPrice = orderBean.getProducts_price();
                tvPayPrice.setText("合计：¥" + orderBean.getProducts_price());
            } else {
                //totalPrice = orderBean.getTotal_price();
                tvPayPrice.setText("合计：¥" + orderBean.getTotal_price() + "（含运费" + shopBean.getFreight() + "元）");
            }
        } else if (type == 2) {
            tvPayPrice.setText("合计：¥" + orderBean.getProducts_price());
        }
    }


    @Override
    public void submitOrderComplete(String orderId) {
        if ("提交订单".equals(tvGoPay.getText().toString())) {
            payComplete();
//            //去订单列表
//            Intent intent = new Intent(this, MyOrderActivity.class);
//            intent.putExtra("orderStatus", 2);
//            startActivity(intent);
//            finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REMARKS) {
            String remarks = data.getStringExtra("remarks");
            orderBean.setRemarks(remarks);
            if (!TextUtils.isEmpty(remarks))
                tvRemarks.setText(remarks);
        }


    }
}

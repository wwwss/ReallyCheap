package tsingcloud.android.reallycheap.shoppingcart.widgets.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.interfaces.OnTabListener;
import tsingcloud.android.core.widgets.fragment.BaseFragment;
import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.model.bean.ShopBean;
import tsingcloud.android.model.bean.ShoppingCartBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.widgets.activity.LoginActivity;
import tsingcloud.android.reallycheap.shoppingcart.presenter.ShoppingCartPresenter;
import tsingcloud.android.reallycheap.shoppingcart.view.ShoppingCartView;
import tsingcloud.android.reallycheap.shoppingcart.widgets.activity.ConfirmOrderActivity;
import tsingcloud.android.reallycheap.shoppingcart.widgets.activity.SelectAddressActivity;
import tsingcloud.android.reallycheap.shoppingcart.widgets.adapter.ShoppingCartAdapter;
import tsingcloud.android.reallycheap.utils.ListViewUtils;
import tsingcloud.android.reallycheap.widgets.view.CustomAlertDialog;

/**
 * Created by admin on 2016/3/16.
 * 购物车
 */
public class ShoppingCartFragment extends BaseFragment implements ShoppingCartView, View.OnClickListener {

    private static final int SELECT_ADDRESS = 0;
    private ImageView ivDelete;
    private TextView tvName;//收货人姓名
    private TextView tvPhoneNumber;//收货人手机号码
    private TextView tvAddressInfo;//收货人地址信息
    private ListView listView;
    private TextView tvTotalPrice;
    private TextView tvTotalHint;
    private ShoppingCartPresenter shoppingCartPresenter;
    private List<ShoppingCartBean> shoppingCartBeans;
    private ShoppingCartAdapter adapter;
    private AddressBean shippingAddressBean;
    private CustomAlertDialog alertDialog;
    private double productsPrice;//商品总价
    private double totalPrice;//订单总价
    private OnTabListener onTabListener;
    private ShopBean shopBean;


    public void setOnTabListener(OnTabListener onTabListener) {
        this.onTabListener = onTabListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void setUpView() {
        shoppingCartPresenter = new ShoppingCartPresenter(this);
        ivDelete = (ImageView) view.findViewById(R.id.delete);
        tvName = (TextView) view.findViewById(R.id.name);
        tvPhoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
        tvAddressInfo = (TextView) view.findViewById(R.id.addressInfo);
        listView = (ListView) view.findViewById(R.id.shoppingCartListView);
        tvTotalPrice = (TextView) view.findViewById(R.id.totalPrice);
        tvTotalHint = (TextView) view.findViewById(R.id.totalHint);
        view.findViewById(R.id.selectShippingAddress).setOnClickListener(this);
        view.findViewById(R.id.shoppingCartEmptyHint).setOnClickListener(this);
        view.findViewById(R.id.operation).setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        shoppingCartBeans = new ArrayList<>();
        adapter = new ShoppingCartAdapter(context, shoppingCartBeans, shoppingCartPresenter);
        listView.setAdapter(adapter);
    }

    @Override
    protected void setUpData() {
        //获取购物车收货地址
        shoppingCartPresenter.getShippingAddress();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            shopBean = (ShopBean) LocalCache.get(context).getAsObject("shopBean");
            //获取购物车列表
            shoppingCartPresenter.getShoppingCartList();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (shoppingCartPresenter == null || !isVisibleToUser)
            return;
        shopBean = (ShopBean) LocalCache.get(context).getAsObject("shopBean");
        //获取购物车收货地址
        shoppingCartPresenter.getShippingAddress();
        //获取购物车列表
        shoppingCartPresenter.getShoppingCartList();
    }

    @Override
    public void setShippingAddress(AddressBean shippingAddress) {
        this.shippingAddressBean = shippingAddress;
        if (shippingAddress == null) {
            view.findViewById(R.id.noShippingAddressHint).setVisibility(View.VISIBLE);
            view.findViewById(R.id.shippingAddress).setVisibility(View.INVISIBLE);
        } else {
            view.findViewById(R.id.noShippingAddressHint).setVisibility(View.GONE);
            view.findViewById(R.id.shippingAddress).setVisibility(View.VISIBLE);
            tvName.setText(shippingAddress.getReceive_name());
            tvPhoneNumber.setText(shippingAddress.getReceive_phone());
            tvAddressInfo.setText(shippingAddress.getArea() + shippingAddress.getDetail());
        }
    }

    @Override
    public void setShoppingCartList(List<ShoppingCartBean> shoppingCartBeanList) {
        if (null != shoppingCartBeanList && shoppingCartBeanList.size() > 0) {
            shoppingCartBeans.clear();
            ivDelete.setVisibility(View.VISIBLE);
            view.findViewById(R.id.shoppingCartEmptyHint).setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            shoppingCartBeans.addAll(shoppingCartBeanList);
            adapter.notifyDataSetChanged();
            ListViewUtils.setListViewHeightBasedOnChildren(listView);
            view.findViewById(R.id.shoppingCartFootView).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.shoppingCartEmptyHint).setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            view.findViewById(R.id.shoppingCartFootView).setVisibility(View.GONE);
            ivDelete.setVisibility(View.GONE);
            tvTotalHint.setVisibility(View.GONE);
        }

    }

    @Override
    public void deleteShopCartItemsSucceed(List<ShoppingCartBean> newShoppingCartBeanList) {
        setShoppingCartList(newShoppingCartBeanList);
    }

    @Override
    public void updateShopCartItemNumberSucceed(ShoppingCartBean newShoppingCartBean, int position) {
        shoppingCartBeans.set(position, newShoppingCartBean);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void delete() {
        if (alertDialog == null) {
            alertDialog = new CustomAlertDialog(context).builder();
            alertDialog.setTitle("您确定要删除这些商品吗？")
                    .setPositiveButton("是", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            shoppingCartPresenter.deleteShoppingCartItem(shoppingCartBeans);
                        }
                    }).setNegativeButton("否", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }
        alertDialog.show();

    }


    @Override
    public void toConfirmOrderActivity(OrderBean orderBean) {
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        intent.putExtra("orderBean", orderBean);
        startActivity(intent);
    }

    @Override
    public void updateTotalPrice(double productsPrice) {
        this.productsPrice = productsPrice;
        if (shopBean == null) {
            showToast("请重新选择店铺");
            return;
        }
        double difference = shopBean.getSend_price() - productsPrice;
        if (difference > 0) {
            totalPrice = productsPrice + shopBean.getFreight();
            tvTotalHint.setText("满" + shopBean.getSend_price() + "免运费,还差" + difference + "元" + "(运费" + shopBean.getFreight() + "元)");
            tvTotalHint.setVisibility(View.VISIBLE);
        } else {
            totalPrice = productsPrice;
            tvTotalHint.setVisibility(View.GONE);
        }
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        String totalPriceValue = decimalFormat.format(totalPrice);
        tvTotalPrice.setText("¥" + totalPriceValue);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete:
                delete();
                break;
            case R.id.selectShippingAddress:
                if (TextUtils.isEmpty(getToken()))
                    startActivity(new Intent(context, LoginActivity.class));
                else {
                    Intent intent = new Intent(context, SelectAddressActivity.class);
                    startActivityForResult(intent, SELECT_ADDRESS);
                }
                break;
            case R.id.operation:
                if (shopBean != null)
                    shoppingCartPresenter.goSettlement(shoppingCartBeans, shopBean, shippingAddressBean, productsPrice, totalPrice);
                else
                    showToast("请重新选择店铺");
                break;
            case R.id.shoppingCartEmptyHint:
                onTabListener.onTabSwitch(0, null);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case SELECT_ADDRESS:
                if (data == null)
                    return;
                AddressBean addressBean = (AddressBean) data.getSerializableExtra("addressBean");
                setShippingAddress(addressBean);
                break;
        }
    }
}

package tsingcloud.android.reallycheap.my.widgets.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.presenter.MyOrderPresenter;
import tsingcloud.android.reallycheap.my.widgets.activity.OrderDetailsActivity;
import tsingcloud.android.reallycheap.shoppingcart.widgets.activity.ConfirmOrderActivity;
import tsingcloud.android.reallycheap.shoppingcart.widgets.adapter.OrderProductAdapter;
import tsingcloud.android.reallycheap.utils.ListViewUitls;
import tsingcloud.android.reallycheap.utils.TimeUitls;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;
import tsingcloud.android.reallycheap.widgets.view.AlertDialog;

/**
 * Created by admin on 2016/4/17.
 * 订单列表适配器
 */
public class MyOrderAdapter extends BaseAdapter<OrderBean> {
    public static final int ORDER_DETAILS = 0;
    private OrderProductAdapter adapter;
    private MyOrderPresenter myOrderPresenter;
    private Activity activity;

    public MyOrderAdapter(Context context, List<OrderBean> list, Activity activity, MyOrderPresenter myOrderPresenter) {
        super(context, list);
        this.myOrderPresenter = myOrderPresenter;
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_order, null);
            ItemCache itemCache = new ItemCache();
            itemCache.rl_order = (RelativeLayout) convertView.findViewById(R.id.order);
            itemCache.tvShopName = (TextView) convertView.findViewById(R.id.shopName);
            itemCache.tvExpirationTime = (TextView) convertView.findViewById(R.id.expirationTime);
            itemCache.tvOrderStatus = (TextView) convertView.findViewById(R.id.orderStatus);
            itemCache.listView = (ListView) convertView.findViewById(R.id.listView);
            itemCache.tvProductNum = (TextView) convertView.findViewById(R.id.productNum);
            itemCache.tvTotalPrice = (TextView) convertView.findViewById(R.id.totalPrice);
            itemCache.tvOperation = (TextView) convertView.findViewById(R.id.operation);
            convertView.setTag(itemCache);
        }
        final ItemCache itemCache = (ItemCache) convertView.getTag();
        final OrderBean orderBean = list.get(position);
        itemCache.tvShopName.setText(orderBean.getShop_name());
        switch (orderBean.getState()) {
            case "0":
                itemCache.tvOrderStatus.setText("待付款");
                itemCache.tvOperation.setText("立即支付");
                itemCache.tvExpirationTime.setText("还有" + TimeUitls.secToTime(orderBean.getExpiration_time()) + "交易关闭");
                itemCache.tvOperation.setVisibility(View.VISIBLE);
                itemCache.tvExpirationTime.setVisibility(View.VISIBLE);
                break;
            case "1":
                itemCache.tvOrderStatus.setText("货到付款");
                itemCache.tvOperation.setText("确认收货");
                itemCache.tvOperation.setVisibility(View.VISIBLE);
                itemCache.tvExpirationTime.setVisibility(View.GONE);
                break;
            case "2":
                itemCache.tvOrderStatus.setText("已支付待收货");
                itemCache.tvOperation.setText("确认收货");
                itemCache.tvOperation.setVisibility(View.VISIBLE);
                itemCache.tvExpirationTime.setVisibility(View.GONE);
                break;
            case "3":
                itemCache.tvOrderStatus.setText("交易成功");
                itemCache.tvOperation.setText("再次购买");
                itemCache.tvOperation.setVisibility(View.VISIBLE);
                itemCache.tvExpirationTime.setVisibility(View.GONE);
                break;
            case "4":
                itemCache.tvOrderStatus.setText("退款中");
                itemCache.tvOperation.setText("");
                itemCache.tvOperation.setVisibility(View.GONE);
                itemCache.tvExpirationTime.setVisibility(View.GONE);
                break;
            case "5":
                itemCache.tvOrderStatus.setText("交易关闭");
                itemCache.tvOperation.setText("");
                itemCache.tvOperation.setVisibility(View.GONE);
                itemCache.tvExpirationTime.setVisibility(View.GONE);
                break;
            case "6":
                if (orderBean.getOrder_type() == 0)
                    itemCache.tvOrderStatus.setText("未接单");
                else if (orderBean.getOrder_type() == 1)
                    itemCache.tvOrderStatus.setText("未接单");
                itemCache.tvOperation.setText("");
                itemCache.tvOperation.setVisibility(View.GONE);
                itemCache.tvExpirationTime.setVisibility(View.GONE);
                break;
        }
        itemCache.tvProductNum.setText("共计" + orderBean.getPro_count() + "件产品");
        itemCache.tvTotalPrice.setText("合计：¥" + orderBean.getTotal_price());
        adapter = new OrderProductAdapter(context, orderBean.getProducts());
        itemCache.listView.setAdapter(adapter);
        ListViewUitls.setListViewHeightBasedOnChildren(itemCache.listView);
        itemCache.tvOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("立即支付".equals(itemCache.tvOperation.getText().toString())) {
                    final AlertDialog alertDialog = new AlertDialog(context).builder();
                    alertDialog.setTitle("您是否立即支付")
                            .setPositiveButton("是", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                    myOrderPresenter.goPay(orderBean.getId());
                                }
                            }).setNegativeButton("否", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else if ("确认收货".equals(itemCache.tvOperation.getText().toString())) {
                    final AlertDialog alertDialog = new AlertDialog(context).builder();
                    alertDialog.setTitle("您是否确认收货")
                            .setPositiveButton("是", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                    myOrderPresenter.confirmReceipt(orderBean.getId());
                                }
                            }).setNegativeButton("否", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else if ("再次购买".equals(itemCache.tvOperation.getText().toString())) {
                    final AlertDialog alertDialog = new AlertDialog(context).builder();
                    alertDialog.setTitle("您是否再次购买")
                            .setPositiveButton("是", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                    Intent intent = new Intent(context, ConfirmOrderActivity.class);
                                    intent.putExtra("orderBean", orderBean);
                                    context.startActivity(intent);
                                }
                            }).setNegativeButton("否", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
        itemCache.rl_order.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("id", orderBean.getId());
                activity.startActivityForResult(intent, ORDER_DETAILS);
            }
        });
        itemCache.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("id", orderBean.getId());
                activity.startActivityForResult(intent, ORDER_DETAILS);
            }
        });
        return convertView;
    }

    private class ItemCache {
        private TextView tvShopName;//店名
        private TextView tvExpirationTime;//有效时间
        private TextView tvOrderStatus;//订单状态
        private ListView listView;//产品列表
        private TextView tvProductNum;//产品数量
        private TextView tvTotalPrice;//总价
        private TextView tvOperation;//用户操作
        private RelativeLayout rl_order;
    }


}

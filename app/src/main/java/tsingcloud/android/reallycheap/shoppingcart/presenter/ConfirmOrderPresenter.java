package tsingcloud.android.reallycheap.shoppingcart.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.model.bean.OrderInfoBean;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.shoppingcart.model.ConfirmOrderModel;
import tsingcloud.android.reallycheap.shoppingcart.model.ConfirmOrderModelImpl;
import tsingcloud.android.reallycheap.shoppingcart.view.ConfirmOrderView;
import tsingcloud.android.reallycheap.utils.PayResult;

/**
 * Created by admin on 2016/4/28.
 * 确认订单适配器
 */
public class ConfirmOrderPresenter extends BasePresenter {
    private ConfirmOrderModel confirmOrderModel;
    private ConfirmOrderView confirmOrderView;
    public static final int SDK_PAY_FLAG = 1000;

    public ConfirmOrderPresenter(ConfirmOrderView confirmOrderView) {
        super(confirmOrderView);
        this.confirmOrderView = confirmOrderView;
        confirmOrderModel = new ConfirmOrderModelImpl();
    }

    public void submitOrder(OrderBean orderBean) {
        if (TextUtils.isEmpty(confirmOrderView.getToken()) || orderBean == null)
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", confirmOrderView.getToken());
        map.put("shop_id", orderBean.getShop_id());
        map.put("receive_phone", orderBean.getReceive_phone());
        map.put("receive_name", orderBean.getReceive_name());
        map.put("area", orderBean.getArea());
        map.put("detail", orderBean.getDetail());
//        map.put("address_id", orderBean.getAddress_id());
        map.put("money", orderBean.getTotal_price());
        map.put("order_type", orderBean.getOrder_type() + "");
        JSONArray jsonArray = new JSONArray();
        List<ProductBean> productBeanList = orderBean.getProducts();
        if (null == productBeanList || productBeanList.size() == 0)
            return;
        for (int i = 0; i < productBeanList.size(); i++) {
            ProductBean productBean = productBeanList.get(i);
            Map<String, String> map2 = new HashMap<>();
            map2.put("id", productBean.getId());
            map2.put("number", productBean.getNumber() + "");
            JSONObject jsonObject = new JSONObject(map2);
            jsonArray.put(jsonObject);
        }
        map.put("products", jsonArray.toString());
        loadingDialog.show();
        confirmOrderModel.submitOrder(map, new OnNSURLRequestListener<OrderBean>() {
            @Override
            public void onSuccess(OrderBean response) {
                loadingDialog.dismiss();
                confirmOrderView.submitOrderComplete(response.getId());
            }

            @Override
            public void onFailure(String msg) {
                loadingDialog.dismiss();
                confirmOrderView.showToast(msg);
            }
        }, confirmOrderView.getTAG());
    }

    public void goPay(String orderId, final Activity context) {
        if (TextUtils.isEmpty(confirmOrderView.getToken()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", confirmOrderView.getToken());
        map.put("order_id", orderId);
        loadingDialog.show();
        confirmOrderModel.getPayInfo(map, new OnNSURLRequestListener<OrderInfoBean>() {
            @Override
            public void onSuccess(final OrderInfoBean response) {
                loadingDialog.dismiss();
                if (TextUtils.isEmpty(response.getOrder_info())) return;
                //异步调用
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask payTask = new PayTask(context);
                        // 调用支付接口，获取支付结果
                        String result = payTask.pay(response.getOrder_info(), true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                }).start();
            }

            @Override
            public void onFailure(String msg) {
                loadingDialog.dismiss();
                confirmOrderView.showToast(msg);
            }
        }, confirmOrderView.getTAG());

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    PayResult payResult = new PayResult((String) msg.obj);
                    // String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        confirmOrderView.showToast("支付成功");
                        confirmOrderView.payComplete();
//                        startActivity(new Intent(RechargeActivity.this, PaySuccessActivity.class));
                        // finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            confirmOrderView.showToast("支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            confirmOrderView.showToast("支付失败");
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

}

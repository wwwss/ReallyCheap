package tsingcloud.android.reallycheap.my.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.model.bean.OrderBean;
import tsingcloud.android.model.bean.OrderInfoBean;
import tsingcloud.android.reallycheap.my.model.MyOrderModel;
import tsingcloud.android.reallycheap.my.model.MyOrderModelImpl;
import tsingcloud.android.reallycheap.my.view.MyOrderView;
import tsingcloud.android.reallycheap.utils.PayResult;

/**
 * Created by admin on 2016/4/29.
 * 我的订单控制器
 */
public class MyOrderPresenter extends BasePresenter {

    private MyOrderModel myOrderModel;
    private MyOrderView myOrderView;
    private static final int SDK_PAY_FLAG = 1000;

    public MyOrderPresenter(MyOrderView myOrderView) {
        super(myOrderView);
        this.myOrderView = myOrderView;
        myOrderModel = new MyOrderModelImpl();
    }

    public void getOrderList(int pageNum) {
        if (TextUtils.isEmpty(myOrderView.getToken()) || TextUtils.isEmpty(myOrderView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", myOrderView.getToken());
        map.put("shop_id", myOrderView.getShopId());
        map.put("state", myOrderView.getOrderStatus() + "");
        map.put("page_num", pageNum + "");
        loadingDialog.show();
        myOrderModel.getOrderList(map, new OnNSURLRequestListener<List<OrderBean>>() {
            @Override
            public void onSuccess(List<OrderBean> response) {
                loadingDialog.dismiss();
                myOrderView.refreshOrderListCompleted(response);
            }

            @Override
            public void onFailure(String msg) {
                loadingDialog.dismiss();
                myOrderView.showToast(msg);
            }
        }, new OnSetListTotalPagesListener() {
            @Override
            public void setTotalPages(int totalPages) {
                myOrderView.setTotalPages(totalPages);
            }
        }, myOrderView.getTAG());

    }

    public void goPay(String orderId) {
        if (TextUtils.isEmpty(myOrderView.getToken()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", myOrderView.getToken());
        map.put("order_id", orderId);
        loadingDialog.show();
        myOrderModel.getPayInfo(map, new OnNSURLRequestListener<OrderInfoBean>() {
            @Override
            public void onSuccess(final OrderInfoBean response) {
                loadingDialog.dismiss();
                if (TextUtils.isEmpty(response.getOrder_info())) return;
                //异步调用
                new Thread(new Runnable() {


                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask payTask = new PayTask(myOrderView.getActivity());
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
                myOrderView.showToast(msg);
            }
        }, myOrderView.getTAG());

    }

    public void confirmReceipt(String orderId) {
        if (TextUtils.isEmpty(myOrderView.getToken()))
            return;
        final Map<String, String> map = new HashMap<>();
        map.put("token", myOrderView.getToken());
        map.put("id", orderId);
        //map.put("shop_id", shopId);
        myOrderModel.confirmReceipt(map, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                myOrderView.showToast(response);
                myOrderView.confirmReceiptComplete();
            }

            @Override
            public void onFailure(String msg) {
                myOrderView.showToast(msg);
            }
        }, myOrderView.getTAG());

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
                        myOrderView.showToast("支付成功");
                        myOrderView.confirmReceiptComplete();
//                        startActivity(new Intent(RechargeActivity.this, PaySuccessActivity.class));
                        // finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            myOrderView.showToast("支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            myOrderView.showToast("支付失败");
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

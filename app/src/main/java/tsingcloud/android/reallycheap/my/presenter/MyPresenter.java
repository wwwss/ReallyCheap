package tsingcloud.android.reallycheap.my.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.reallycheap.my.model.MyModel;
import tsingcloud.android.reallycheap.my.model.MyModelImpl;
import tsingcloud.android.reallycheap.my.view.MyView;

/**
 * Created by admin on 2016/4/18.
 * 我的页面控制器
 */
public class MyPresenter extends BasePresenter {

    private MyModel myModel;
    private MyView myView;

    public MyPresenter(MyView myView) {
        super(myView);
        this.myView = myView;
        myModel = new MyModelImpl();
    }

    public void getUserInfo() {
        if (TextUtils.isEmpty(myView.getToken()) || TextUtils.isEmpty(myView.getShopId()))
            return;
        loadingDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("shop_id", myView.getShopId());
        myModel.getUserIfo(myView.getToken(), map, new OnNSURLRequestListener<UserBean>() {
            @Override
            public void onSuccess(UserBean response) {
                loadingDialog.dismiss();
                myView.setUserInfo(response);
            }

            @Override
            public void onFailure(String msg) {
                loadingDialog.dismiss();
                myView.showToast(msg);
            }
        }, myView.getTAG());
    }
}

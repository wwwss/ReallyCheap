package tsingcloud.android.reallycheap.my.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.reallycheap.my.model.AddressModel;
import tsingcloud.android.reallycheap.my.model.AddressModelImpl;
import tsingcloud.android.reallycheap.my.view.AddressView;

/**
 * Created by admin on 2016/4/28.
 * 地址编辑控制器
 */
public class AddressPresenter {
    private AddressModel addressModel;
    private AddressView addressView;

    public AddressPresenter(AddressView addressView) {
        this.addressView = addressView;
        addressModel = new AddressModelImpl();
    }

    public void addAddress() {
        if (null == addressView.getMap())
            return;
        addressModel.addAddress(addressView.getMap(), new OnNSURLRequestListener<AddressBean>() {
            @Override
            public void onSuccess(AddressBean response) {
                addressView.addAddressCompleted(response);
                addressView.showToast("添加地址成功");
            }

            @Override
            public void onFailure(String msg) {
                addressView.showToast(msg);
            }
        },addressView.getTAG());
    }

    public void updateAddress() {
        if (null == addressView.getMap())
            return;
        addressModel.updateAddress(addressView.getMap(), new OnNSURLRequestListener<AddressBean>() {
            @Override
            public void onSuccess(AddressBean response) {
                addressView.addAddressCompleted(response);
                addressView.showToast("修改地址成功");
            }

            @Override
            public void onFailure(String msg) {
                addressView.showToast(msg);
            }
        },addressView.getTAG());
    }

    public void deleteAddress(String id) {
        if (TextUtils.isEmpty(addressView.getToken()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("token", addressView.getToken());
        addressModel.deleteAddress(map, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                addressView.deleteAddressCompleted();
                addressView.showToast(response);
            }

            @Override
            public void onFailure(String msg) {
                addressView.showToast(msg);
            }
        },addressView.getTAG());
    }

}

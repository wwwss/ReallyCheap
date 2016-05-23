package tsingcloud.android.reallycheap.my.model;

import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.AddressBean;

/**
 * Created by admin on 2016/4/28.
 * 地址页面Model
 */
public interface AddressModel {
    void addAddress(Map<String, String> map, OnNSURLRequestListener<AddressBean> listener,String tag);

    void updateAddress(Map<String, String> map, OnNSURLRequestListener<AddressBean> listener,String tag);

    void deleteAddress(Map<String, String> map, OnNSURLRequestListener<String> listener,String tag);
}

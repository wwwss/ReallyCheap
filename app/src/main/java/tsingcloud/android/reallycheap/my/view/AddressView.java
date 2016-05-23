package tsingcloud.android.reallycheap.my.view;

import java.util.Map;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.AddressBean;

/**
 * Created by admin on 2016/4/18.
 */
public interface AddressView extends BaseView {
    void addAddressCompleted(AddressBean addressBean);

    void updateAddressCompleted(AddressBean addressBean);

    void deleteAddressCompleted();

    /**
     * 获取网络请求参数集合
     * @return
     */
    Map<String, String> getMap();
}

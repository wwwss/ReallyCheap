package tsingcloud.android.reallycheap.my.view;

import java.util.List;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.AddressBean;

/**
 * Created by admin on 2016/4/17.
 */
public interface AddManageView extends BaseView {

    void setAddressList(List<AddressBean> addressBeans);

    void toAddressActivity(int type,int position);
}

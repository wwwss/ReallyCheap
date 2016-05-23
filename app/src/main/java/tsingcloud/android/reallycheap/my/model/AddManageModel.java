package tsingcloud.android.reallycheap.my.model;

import java.util.List;

import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;

/**
 * Created by admin on 2016/4/17.
 */
public interface AddManageModel {
   void getAddressList(String token,OnNSURLRequestListener<List<AddressBean>> listener,String tag);
}

package tsingcloud.android.reallycheap.my.presenter;

import android.text.TextUtils;

import java.util.List;

import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.reallycheap.my.model.AddManageModel;
import tsingcloud.android.reallycheap.my.model.AddManageModelImpl;
import tsingcloud.android.reallycheap.my.view.AddManageView;

/**
 * Created by admin on 2016/4/17.
 * 地址管理控制器
 */
public class AddManagePresenter extends BasePresenter{

    private AddManageModel addManageModel;
    private AddManageView addManageView;

    public AddManagePresenter(AddManageView addManageView) {
        super(addManageView);
        this.addManageView = addManageView;
        addManageModel = new AddManageModelImpl();
    }

    public void getAddressList() {
        if (TextUtils.isEmpty(addManageView.getToken())) {
            return;
        }
        addManageModel.getAddressList(addManageView.getToken(), new AbstractOnNSURLRequestListener<List<AddressBean>>() {
            @Override
            public void onSuccess(List<AddressBean> response) {
                addManageView.setAddressList(response);
            }
        },addManageView.getTAG());
    }
}

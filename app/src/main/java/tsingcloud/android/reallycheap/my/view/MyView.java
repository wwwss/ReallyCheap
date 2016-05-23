package tsingcloud.android.reallycheap.my.view;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.UserBean;

/**
 * Created by admin on 2016/4/18.
 */
public interface MyView extends BaseView{

    void  setUserInfo(UserBean userBean);
}

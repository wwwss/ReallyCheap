package tsingcloud.android.reallycheap.my.view;

import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.core.view.BaseView;

/**
 * Created by admin on 2016/3/22.
 */
public interface LoginView extends BaseView {
    String getInputPhoneNumber();

    String getInputYzm();

    void toMainActivity(UserBean userBean);

    void setLoginIsClick(boolean isClick);

    void setGetYzmText(boolean isClick,String text);
}

package tsingcloud.android.reallycheap.my.view;

import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.core.view.BaseView;

/**
 * Created by admin on 2016/4/17.
 */
public interface NicknameView extends BaseView {
    String getInputNickname();

    void updateComplete(UserBean userBean);

    void clearIsShow(boolean isShow);
}

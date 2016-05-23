package tsingcloud.android.reallycheap.my.view;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.UserBean;

/**
 * Created by admin on 2016/4/16.
 */
public interface PersonalCenterView extends BaseView {
    void updateUserInfo(UserBean userBean);

    void updateAvatar();

    String getImagePath();
}

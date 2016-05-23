package tsingcloud.android.reallycheap.my.presenter;

import android.text.TextUtils;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.reallycheap.my.model.PersonalCenterModel;
import tsingcloud.android.reallycheap.my.model.PersonalCenterModelImpl;
import tsingcloud.android.reallycheap.my.view.PersonalCenterView;

/**
 * Created by admin on 2016/4/17.
 * 用户昵称
 */
public class PersonalCenterPresenter {

    private PersonalCenterModel personalCenterModel;
    private PersonalCenterView personalCenterView;

    public PersonalCenterPresenter(PersonalCenterView personalCenterView) {
        this.personalCenterView = personalCenterView;
        personalCenterModel = new PersonalCenterModelImpl();
    }

    public void updateAvatar() {
        if (TextUtils.isEmpty(personalCenterView.getToken())) {
            return;
        }
        if (TextUtils.isEmpty(personalCenterView.getImagePath())) {
            personalCenterView.showToast("用户头像路径不能为空");
            return;
        }
        personalCenterModel.updateUserInfo("key", personalCenterView.getImagePath(), personalCenterView.getToken(), new OnNSURLRequestListener<UserBean>() {
            @Override
            public void onSuccess(UserBean userBean) {
                personalCenterView.updateUserInfo(userBean);
            }

            @Override
            public void onFailure(String msg) {
                personalCenterView.showToast(msg);
            }
        },personalCenterView.getTAG());
    }


}

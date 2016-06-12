package tsingcloud.android.reallycheap.my.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.reallycheap.my.model.PersonalCenterModel;
import tsingcloud.android.reallycheap.my.model.PersonalCenterModelImpl;
import tsingcloud.android.reallycheap.my.view.NicknameView;

/**
 * Created by admin on 2016/4/17.
 * 用户昵称
 */
public class NicknamePresenter extends BasePresenter{

    private PersonalCenterModel personalCenterModel;
    private NicknameView nicknameView;

    public NicknamePresenter(NicknameView nicknameView) {
        super(nicknameView);
        this.nicknameView = nicknameView;
        personalCenterModel = new PersonalCenterModelImpl();
    }

    public void updateNickname() {
        if (TextUtils.isEmpty(nicknameView.getToken())) {
            //nicknameView.toLoginActivity();
            return;
        }
        if (TextUtils.isEmpty(nicknameView.getInputNickname())) {
            nicknameView.showToast("用户昵称不能为空");
            return;
        }
        personalCenterModel.updateUserInfo("name", nicknameView.getInputNickname(), nicknameView.getToken(), new AbstractOnNSURLRequestListener<UserBean>() {
            @Override
            public void onSuccess(UserBean response) {
                nicknameView.updateComplete(response);
            }

        },nicknameView.getTAG());
    }

    public void clearIsShow() {
        handler.sendEmptyMessage(1001);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    if (TextUtils.isEmpty(nicknameView.getInputNickname()))
                        nicknameView.clearIsShow(false);
                    else
                        nicknameView.clearIsShow(true);
                    break;
            }
        }
    };

}

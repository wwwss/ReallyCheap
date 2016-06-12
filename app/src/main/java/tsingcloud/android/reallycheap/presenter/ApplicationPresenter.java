package tsingcloud.android.reallycheap.presenter;

import android.text.TextUtils;

import tsingcloud.android.reallycheap.view.WelcomeView;
import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.model.bean.ApplicationBean;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.reallycheap.model.ApplicationModel;
import tsingcloud.android.reallycheap.model.ApplicationModelImpl;

/**
 * Created by admin on 2016/4/16.
 * 程序入口
 */
public class ApplicationPresenter extends BasePresenter {
    private ApplicationModel applicationModel;
    private WelcomeView listener;

    public ApplicationPresenter(WelcomeView listener) {
        super(listener);
        this.listener = listener;
        applicationModel = new ApplicationModelImpl();
    }

    public void initApplication(final String version, String tag) {
        applicationModel.initApplicationVariables(version, new AbstractOnNSURLRequestListener<ApplicationBean>() {
            @Override
            public void onSuccess(ApplicationBean response) {
                if (!version.equals(response.getVersion()))
                    listener.updateApplicationVariable(response);
            }
        }, tag);
    }

    public void updateToken(String token, String tag) {
        applicationModel.updateToken(token, new AbstractOnNSURLRequestListener<UserBean>() {
            @Override
            public void onSuccess(UserBean response) {
                listener.updateToken(response.getToken());
            }
        }, tag);
    }


    public void bindPush(String clientId, String token) {
        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(clientId))
            return;
        applicationModel.bindPush(clientId, token, new AbstractOnNSURLRequestListener<String>() {
        }, listener.getTAG());
    }


}

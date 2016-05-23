package tsingcloud.android.reallycheap.presenter;

import android.text.TextUtils;

import tsingcloud.android.model.bean.ApplicationBean;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.UpdateApplicationVariableListener;
import tsingcloud.android.reallycheap.model.ApplicationModel;
import tsingcloud.android.reallycheap.model.ApplicationModelImpl;

/**
 * Created by admin on 2016/4/16.
 * 程序入口
 */
public class ApplicationPresenter {
    private ApplicationModel applicationModel;
    private UpdateApplicationVariableListener listener;

    public ApplicationPresenter(UpdateApplicationVariableListener listener) {
        this.listener = listener;
        applicationModel = new ApplicationModelImpl();
    }

    public void initApplication(final String version, String tag) {
        applicationModel.initApplicationVariables(version, new OnNSURLRequestListener<ApplicationBean>() {
            @Override
            public void onSuccess(ApplicationBean response) {
                if (!version.equals(response.getVersion()))
                    listener.updateApplicationVariable(response);
            }

            @Override
            public void onFailure(String msg) {

            }
        }, tag);
    }

    public void updateToken(String token, String tag) {
        applicationModel.updateToken(token, new OnNSURLRequestListener<UserBean>() {
            @Override
            public void onSuccess(UserBean response) {
                listener.updateToken(response.getToken());
            }

            @Override
            public void onFailure(String msg) {
                listener.showToast(msg);
            }
        }, tag);
    }


    public void bindPush(String clientId, String token) {
        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(clientId))
            return;
        applicationModel.bindPush(clientId, token, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailure(String msg) {
            }
        }, listener.getTAG());
    }


}

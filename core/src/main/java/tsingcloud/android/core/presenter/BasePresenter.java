package tsingcloud.android.core.presenter;

import android.app.Dialog;

import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.core.view.BaseView;

/**
 * Created by admin on 2016/4/19.
 */
public class BasePresenter {

    public Dialog loadingDialog;

    public BasePresenter(BaseView baseView) {
        loadingDialog = baseView.getDialog();
    }

    /**
     * 当页面被销毁取消网络请求
     *
     * @param TAG 请求的TAG
     */
    public void cancelRequest(String TAG) {
        OkHttpUtils.cancelRequest(TAG);
    }
}

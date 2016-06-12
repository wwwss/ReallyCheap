package tsingcloud.android.core.presenter;

import android.app.Dialog;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.core.view.BaseView;

/**
 * Created by admin on 2016/4/19.
 */
public class BasePresenter {

    public Dialog loadingDialog;
    private BaseView baseView;

    public BasePresenter(BaseView baseView) {
        this.baseView = baseView;
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


    public abstract class AbstractOnNSURLRequestListener<T> implements OnNSURLRequestListener<T> {
        @Override
        public void onSuccess(T response) {
        }
        @Override
        public void onFailure(String msg) {
            if (loadingDialog != null && loadingDialog.isShowing())
                loadingDialog.dismiss();
            baseView.showToast(msg);
        }

        @Override
        public void onTokenFailure() {
            if (loadingDialog != null && loadingDialog.isShowing())
                loadingDialog.dismiss();
            baseView.TokenFailure();
        }
    }

}

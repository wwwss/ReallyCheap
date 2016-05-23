package tsingcloud.android.core.view;

import android.app.Dialog;

/**
 * Created by admin on 2016/3/24.
 */
public interface BaseView {
    void showToast(String msg);

    String getToken();

    Dialog getDialog();

//    void toLoginActivity();

    String getShopId();

    String getTAG();
}

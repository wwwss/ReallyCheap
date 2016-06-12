package tsingcloud.android.core.widgets.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import tsingcloud.android.core.R;
import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.ShopBean;

/**
 * Created by Stay on 22/10/15.
 * Powered by www.stay4it.com
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    // log标签
    protected final String TAG = getClass().getName();
    protected Context context;
    protected View view;
    protected Dialog loadingHintDialog;
    protected Dialog loadingDialog;
    protected BasePresenter basePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getActivity();
        this.view = view;
        basePresenter = new BasePresenter(this);
        setUpView();
        setUpData();
    }

    /**
     * 初始化控件
     */
    protected abstract void setUpView();

    /**
     * 初始化数据
     */
    protected abstract void setUpData();

    @Override
    public String getToken() {
        return LocalCache.get(context).getAsString("token");
    }

    @Override
    public String getShopId() {
        ShopBean shopBean = (ShopBean) LocalCache.get(context).getAsObject("shopBean");
        if (shopBean == null)
            return null;
        else
            return shopBean.getId();
    }

//    @Override
//    public void toLoginActivity() {
//    }

    @Override
    public void TokenFailure() {
        LocalCache.get(context).remove("token");
        Toast.makeText(context, "登录已过期，请重新登录", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Dialog getDialog() {
        if (loadingDialog == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.widget_loading_dialog, null); // 得到加载view
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view); // 加载布局
            loadingDialog = new Dialog(context, R.style.loading_dialog); // 创建自定义样式dialog
            loadingDialog.setCancelable(false); // 不可以用"返回键"取消
            loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        return loadingDialog;
    }

    protected void showDialog(String message) {
        if (loadingHintDialog == null) {
            // 创建自定义样式dialog
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.widget_loading_hint_dialog, null); // 得到加载view
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view); // 加载布局
            TextView tvTip = (TextView) view.findViewById(R.id.tipTextView);// 提示文字
            if (!TextUtils.isEmpty(message))
                tvTip.setText(message);
            loadingHintDialog = new Dialog(context, R.style.loading_hint_dialog); // 创建自定义样式dialog
            loadingHintDialog.setCancelable(false); // 不可以用"返回键"取消
            loadingHintDialog.setContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        }
        loadingHintDialog.show();
    }

    protected void dismissDialog() {
        if (loadingHintDialog != null && loadingHintDialog.isShowing())
            loadingHintDialog.dismiss();
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void onDestroy() {
        //basePresenter.cancelRequest(TAG);
        super.onDestroy();
    }




}

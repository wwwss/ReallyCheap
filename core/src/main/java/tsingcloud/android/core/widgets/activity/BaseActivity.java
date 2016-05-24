package tsingcloud.android.core.widgets.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import tsingcloud.android.core.R;
import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.interfaces.TitleBarListener;
import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.core.widgets.TitleBar;


/**
 * Created by Stay on 2/2/16.
 * Powered by www.stay4it.com
 */
public abstract class BaseActivity extends AppCompatActivity implements TitleBarListener, BaseView {
    // log标签
    protected final String TAG = getClass().getName();
    protected TitleBar titleBar;
    protected TextView toolbar_title;
    protected Dialog loadingHintDialog;
    protected Dialog loadingDialog;
    protected PopupWindow popupWindow;
    protected BasePresenter basePresenter;
    public static final int MODE_BACK = 0;
    public static final int MODE_CANCEL = 4;
    public static final int MODE_DRAWER = 1;
    public static final int MODE_NONE = 2;
    public static final int MODE_HOME = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpContentView();
        setUpView();
        setUpData();
    }

    protected abstract void setUpContentView();

    protected abstract void setUpView();

    protected abstract void setUpData();

    @Override
    public void setContentView(int layoutResID) {
        setContentView(layoutResID, -1, MODE_NONE);
    }

    public void setContentView(int layoutResID, int titleResId) {
        setContentView(layoutResID, titleResId, MODE_BACK);
    }

    public void setContentView(int layoutResID, int titleResId, int mode) {
        super.setContentView(layoutResID);
        basePresenter = new BasePresenter(this);
        setUpToolbar(titleResId, mode);
    }

    protected void setUpToolbar(int titleResId, int mode) {
        if (mode != MODE_NONE) {
            titleBar = (TitleBar) findViewById(R.id.title_bar);
            titleBar.setListener(this);
            setUpTitle(titleResId);
            if (mode == MODE_BACK) {
                titleBar.setLeftDrawable(R.drawable.back_black_icon);
            } else if (mode == MODE_CANCEL) {
                titleBar.setLeftDrawable(R.drawable.cancel_icon);
            }
        }
    }

    protected void setUpTitle(int titleResId) {
        if (titleResId > 0 && titleBar != null) {
            titleBar.setTitle(titleResId);
        }
    }

    protected void setUpTitle(String title) {
        if (!TextUtils.isEmpty(title) && titleBar != null) {
            titleBar.setTitle(title);
        }
    }


    @Override
    public void clickLeft() {
        finish();
    }

    @Override
    public void clickRight() {

    }

    @Override
    public String getToken() {
        return LocalCache.get(this).getAsString("token");
    }


//    @Override
//    public void toLoginActivity() {
//
//    }

    @Override
    public Dialog getDialog() {
        if (loadingDialog == null) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.widget_loading_dialog, null); // 得到加载view
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view); // 加载布局
            loadingDialog = new Dialog(this, R.style.loading_dialog); // 创建自定义样式dialog
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
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.widget_loading_hint_dialog, null); // 得到加载view
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view); // 加载布局
            TextView tvTip = (TextView) view.findViewById(R.id.tipTextView);// 提示文字
            if (!TextUtils.isEmpty(message))
                tvTip.setText(message);
            loadingHintDialog = new Dialog(this, R.style.loading_hint_dialog); // 创建自定义样式dialog
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
    public void showToast(String msg) {
        if ("登录已过期，请重新登录".equals(msg))
            LocalCache.get(this).remove("token");
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getShopId() {
        return LocalCache.get(this).getAsString("shopId");
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 当页面被销毁取消网络请求
     */
    @Override
    protected void onDestroy() {
        //basePresenter.cancelRequest(TAG);
        super.onDestroy();
    }


}

package tsingcloud.android.reallycheap.widgets.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.igexin.sdk.PushManager;

import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.interfaces.UpdateApplicationVariableListener;
import tsingcloud.android.core.utils.LogUtils;
import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.ApplicationBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.presenter.ApplicationPresenter;

/**
 * Created by admin on 2016/5/10
 * 欢迎页面
 */
public class WelcomeActivity extends BaseActivity implements Animation.AnimationListener, UpdateApplicationVariableListener {

    private ApplicationPresenter applicationPresenter;

    @Override
    protected void setUpContentView() {
        applicationPresenter = new ApplicationPresenter(this);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 初始化布局文件
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_welcome, null);
        setContentView(rootView);
        // mHandler = new Handler();
        // 初始化渐变动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
        animation.setFillEnabled(true); // 启动Fill保持
        animation.setFillAfter(true); // 设置动画的最后一帧是保持在View上面
        animation.setAnimationListener(this);
        // 开始播放动画
        rootView.startAnimation(animation);
        PushManager.getInstance().initialize(this.getApplicationContext());
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {

    }

    @Override
    public void onAnimationStart(Animation animation) {
        String version = LocalCache.get(this).getAsString("version");
        LogUtils.d("version", LocalCache.get(this).getAsString("version"));
        if (TextUtils.isEmpty(version))
            version = "0";
        applicationPresenter.initApplication(version, TAG);
        String token = LocalCache.get(this).getAsString("token");
        if (!TextUtils.isEmpty(token)) {
            applicationPresenter.updateToken(token, TAG);
            String clientId = PushManager.getInstance().getClientid(this);
            applicationPresenter.bindPush(token, clientId);
        }

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        String isFirst = LocalCache.get(getApplicationContext()).getAsString("isFirst");
        if (TextUtils.isEmpty(isFirst)) {
            startActivity(new Intent(this, GuidePageActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 在欢迎界面屏蔽BACK键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }


    @Override
    public void updateApplicationVariable(ApplicationBean applicationBean) {
        LocalCache.get(this).put("version", applicationBean.getVersion());
        LocalCache.get(this).put("send_price", applicationBean.getSend_price());
        LocalCache.get(this).put("phone_num", applicationBean.getPhone_num());
        LogUtils.d("version", LocalCache.get(this).getAsString("version"));

    }

    @Override
    public void updateToken(String token) {
        LocalCache.get(this).put("token", token);
    }
}

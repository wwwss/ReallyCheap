package tsingcloud.android.reallycheap.my.presenter;

import android.os.Handler;
import android.text.TextUtils;

import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.reallycheap.my.model.LoginModel;
import tsingcloud.android.reallycheap.my.model.LoginModelImpl;
import tsingcloud.android.reallycheap.my.view.LoginView;

/**
 * Created by admin on 2016/3/22.
 * 登录页面控制器
 */
public class LoginPresenter extends BasePresenter{

    private LoginView loginView;
    private LoginModel loginModel;
    private int countdown = 120;//倒计时

    public LoginPresenter(LoginView loginView) {
        super(loginView);
        this.loginView = loginView;
        loginModel = new LoginModelImpl();
    }

    /**
     * 获取验证码
     */
    public void getYzm() {
        if (TextUtils.isEmpty(loginView.getInputPhoneNumber())) {
            loginView.showToast("手机号码不能为空");
            return;
        }
        loginModel.getYzm(loginView.getInputPhoneNumber(), new AbstractOnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String result) {
                loginView.showToast(result);
                loginView.setGetYzmText(false, countdown-- + "秒");
                handler.sendEmptyMessage(1002);
            }

        }, loginView.getTAG());
    }

    /**
     * 登录
     */
    public void login() {
        if (TextUtils.isEmpty(loginView.getInputPhoneNumber())) {
            loginView.showToast("手机号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(loginView.getInputYzm())) {
            loginView.showToast("验证码不能为空");
            return;
        }
        loginModel.login(loginView.getInputPhoneNumber(), loginView.getInputYzm(), new AbstractOnNSURLRequestListener<UserBean>() {
            @Override
            public void onSuccess(UserBean userBean) {
                loginView.toMainActivity(userBean);
                loginView.showToast("登录成功");
            }
        }, loginView.getTAG());
    }

    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1001:
                    if (!TextUtils.isEmpty(loginView.getInputPhoneNumber())
                            && !TextUtils.isEmpty(loginView.getInputYzm())) {
                        loginView.setLoginIsClick(true);
                    } else {
                        loginView.setLoginIsClick(false);
                    }
                    break;
                case 1002:
                    handler.post(runnable);
                    break;
                default:
                    break;
            }
        }

        ;

    };
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (countdown == 0) {
                loginView.setGetYzmText(true, "获取验证码");
                countdown = 120;
                return;
            }
            loginView.setGetYzmText(false, countdown + "秒后重试");
            countdown--;
            handler.postDelayed(this, 1000L);

        }
    };

    public void isCanLogin() {
        handler.sendEmptyMessage(1001);

    }

    public void removeCallbacks() {
        handler.removeCallbacks(runnable);
    }


    public void bindPush(String clientId, String token) {
        loginModel.bindPush(clientId, token, new AbstractOnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailure(String msg) {
            }
        },loginView.getTAG());
    }
}

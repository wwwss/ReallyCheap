package tsingcloud.android.reallycheap.my.widgets.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.presenter.LoginPresenter;
import tsingcloud.android.reallycheap.my.view.LoginView;

/**
 * Created by admin on 2016/3/22.
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements LoginView, TextWatcher, View.OnClickListener {

    private EditText etPhoneNumber;
    private EditText etYzm;
    private TextView tvGetYzm;
    private TextView tvLogin;
    private LoginPresenter loginPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_login, R.string.login_yz, MODE_CANCEL);
    }

    @Override
    protected void setUpView() {
        loginPresenter = new LoginPresenter(this);
        etPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        etYzm = (EditText) findViewById(R.id.yzm);
        tvGetYzm = (TextView) findViewById(R.id.getYzm);
        tvGetYzm.setOnClickListener(this);
        tvGetYzm.setSelected(true);
        tvLogin = (TextView) findViewById(R.id.login);
        tvLogin.setOnClickListener(this);
        findViewById(R.id.protocol).setOnClickListener(this);
        etPhoneNumber.addTextChangedListener(this);
        etYzm.addTextChangedListener(this);
    }

    @Override
    protected void setUpData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.getYzm:
                loginPresenter.getYzm();
                break;
            case R.id.login:
                loginPresenter.login();
                break;
            case R.id.protocol:
                break;


        }
    }

    @Override
    public String getInputPhoneNumber() {
        return etPhoneNumber.getText().toString();
    }

    @Override
    public String getInputYzm() {
        return etYzm.getText().toString();
    }

    @Override
    public void toMainActivity(UserBean userBean) {
        LocalCache.get(this).put("token", userBean.getToken());
        String clientId = LocalCache.get(this).getAsString("clientId");
        if (!TextUtils.isEmpty(clientId)&&!TextUtils.isEmpty(userBean.getToken()))
            loginPresenter.bindPush(clientId, userBean.getToken());
        finish();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }


    @Override
    public void setLoginIsClick(boolean isClick) {
        tvLogin.setClickable(isClick);
        tvLogin.setSelected(isClick);
    }

    @Override
    public void setGetYzmText(boolean isClick, String text) {
        tvGetYzm.setText(text);
        tvGetYzm.setClickable(isClick);
        tvGetYzm.setSelected(isClick);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        loginPresenter.isCanLogin();
    }

    @Override
    public void finish() {
        loginPresenter.removeCallbacks();
        super.finish();
    }


}

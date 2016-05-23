package tsingcloud.android.reallycheap.my.widgets.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.presenter.NicknamePresenter;
import tsingcloud.android.reallycheap.my.view.NicknameView;
import tsingcloud.android.core.utils.LogUtils;

/**
 * Created by admin on 2016/4/17.
 * 修改昵称页面
 */
public class NicknameActivity extends BaseActivity implements View.OnClickListener, NicknameView, TextWatcher {

    private EditText etNikeName;
    private ImageView ivClear;
    private NicknamePresenter nicknamePresenter;
    private String nickname;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_nikename, R.string.update_nickname);
    }

    @Override
    protected void setUpView() {
        nicknamePresenter = new NicknamePresenter(this);
        titleBar.setRightText(R.string.confirm);
        etNikeName = (EditText) findViewById(R.id.nickName);
        ivClear = (ImageView) findViewById(R.id.ivClear);
        ivClear.setOnClickListener(this);
        etNikeName.addTextChangedListener(this);
    }

    @Override
    protected void setUpData() {
        nickname = getIntent().getStringExtra("nickname");
        if (!TextUtils.isEmpty(nickname))
            etNikeName.setText(nickname);

    }

    @Override
    public void clickRight() {
        nicknamePresenter.updateNickname();
        LogUtils.d(TAG, "更改用户信息");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivClear:
                etNikeName.setText("");
                break;
        }
    }

    @Override
    public String getInputNickname() {
        return etNikeName.getText().toString().trim();
    }

    @Override
    public void updateComplete(UserBean userBean) {
        Intent intent = new Intent();
        intent.putExtra("nickname", userBean.getName());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void clearIsShow(boolean isShow) {
        if (isShow)
            ivClear.setVisibility(View.VISIBLE);
        else
            ivClear.setVisibility(View.GONE);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        nicknamePresenter.clearIsShow();
    }
}

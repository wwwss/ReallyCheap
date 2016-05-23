package tsingcloud.android.reallycheap.my.widgets.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.interfaces.UploadPicturesListener;
import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.presenter.PersonalCenterPresenter;
import tsingcloud.android.reallycheap.my.view.PersonalCenterView;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;
import tsingcloud.android.reallycheap.utils.PathUtils;
import tsingcloud.android.reallycheap.utils.QINiuUtils;
import tsingcloud.android.reallycheap.utils.SelectImageUtils;

/**
 * Created by admin on 2016/4/16.
 * 个人中心页面
 */
public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener, PersonalCenterView, UploadPicturesListener {

    private ImageView ivAvatar;
    private TextView tvNikeName;
    private TextView tvPhoneNumber;
    private SelectImageUtils selectImageUtils;
    private UserBean userBean;
    private final int UPDATE_NICKNAME = 2;
    private boolean isUpdate;
    private String imagePath;
    private PersonalCenterPresenter personalCenterPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_personal_center, R.string.personal_center);
    }

    @Override
    protected void setUpView() {
        personalCenterPresenter = new PersonalCenterPresenter(this);
        ivAvatar = (ImageView) findViewById(R.id.avatar);
        tvNikeName = (TextView) findViewById(R.id.nickName);
        tvPhoneNumber = (TextView) findViewById(R.id.phoneNumber);
        findViewById(R.id.updateAvatar).setOnClickListener(this);
        findViewById(R.id.updateNickName).setOnClickListener(this);
        findViewById(R.id.loginOut).setOnClickListener(this);
    }

    @Override
    protected void setUpData() {
        userBean = (UserBean) getIntent().getSerializableExtra("userBean");
        if (userBean == null)
            return;
        if (TextUtils.isEmpty(userBean.getName()))
            tvNikeName.setText("亲点我修改昵称");
        else
            tvNikeName.setText(userBean.getName());
        tvPhoneNumber.setText(userBean.getPhone());
        ImageLoaderUtils.displayRoundedImageView(this, ivAvatar, userBean.getImage(), R.drawable.default_avatar_icon, R.drawable.default_avatar_icon);

    }

    @Override
    public void onClick(View v) {
        if (userBean == null)
            return;
        switch (v.getId()) {
            case R.id.updateAvatar:
                updateAvatar();
                break;
            case R.id.updateNickName:
                Intent intent = new Intent(this, NicknameActivity.class);
                intent.putExtra("nickname", userBean.getName());
                startActivityForResult(intent, UPDATE_NICKNAME);
                break;
            case R.id.loginOut:
                LocalCache.get(this).remove("token");
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    @Override
    public void updateUserInfo(UserBean userBean) {
        if (userBean == null)
            return;
        this.userBean = userBean;
        ImageLoaderUtils.displayRoundedImageView(this, ivAvatar, userBean.getImage(), R.drawable.default_avatar_icon, R.drawable.default_avatar_icon);
        isUpdate = true;
    }

    @Override
    public void updateAvatar() {
        if (selectImageUtils == null)
            selectImageUtils = new SelectImageUtils(this);
        selectImageUtils.showSelectDialog();
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case SelectImageUtils.CAMERA:
                QINiuUtils.getmInstance().UploadFile(selectImageUtils.imagePath, this, TAG);
                break;
            case SelectImageUtils.GALLERY:
                QINiuUtils.getmInstance().UploadFile(PathUtils.getUriPath(data, this), this, TAG);
                break;
            case UPDATE_NICKNAME:
                String nickname = data.getStringExtra("nickname");
                if (!TextUtils.isEmpty(nickname)) {
                    isUpdate = true;
                    tvNikeName.setText(nickname);
                    userBean.setName(nickname);
                }
                break;
        }
    }

    @Override
    public void onUploadSuccess(String imagePath) {
        this.imagePath = imagePath;
        showToast("图片上传成功");
        dismissDialog();
        isUpdate = true;
        personalCenterPresenter.updateAvatar();
    }

    @Override
    public void onUploadStart() {
        showDialog("正在上传图片...");
    }

    @Override
    public void onUploadFailure() {
        showToast("图片上传失败");
        dismissDialog();
    }

    @Override
    public void finish() {
        if (isUpdate) {
            Intent intent = new Intent();
            intent.putExtra("userBean", userBean);
            setResult(RESULT_OK, intent);
        }
        super.finish();
    }
}

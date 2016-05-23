package tsingcloud.android.reallycheap.my.widgets.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.presenter.AddressPresenter;
import tsingcloud.android.reallycheap.my.view.AddressView;

/**
 * Created by admin on 2016/4/17.
 * 地址页面
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener, AddressView {

    private int type;//0是新增1是编辑
    private AddressBean addressBean;
    private EditText etReceivingName;//收货人姓名
    private EditText etPhoneNumber;//手机号码
    private TextView tvReceivingAddress;//收货地址
    private EditText etDetailAddress;//详细地址
    private ImageView ivIsDefault;//是否为默认地址
    protected final int POSITIONING = 0;
    private double lat;//经度
    private double lng;//纬度
    private AddressPresenter addressPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_address, -1);
    }

    @Override
    protected void setUpView() {
        addressPresenter = new AddressPresenter(this);
        etReceivingName = (EditText) findViewById(R.id.name);
        etPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        tvReceivingAddress = (TextView) findViewById(R.id.receivingAddress);
        etDetailAddress = (EditText) findViewById(R.id.detailAddress);
        ivIsDefault = (ImageView) findViewById(R.id.isDefault);
        findViewById(R.id.positioning).setOnClickListener(this);
        ivIsDefault.setOnClickListener(this);
        findViewById(R.id.determine).setOnClickListener(this);
    }

    @Override
    protected void setUpData() {
        type = getIntent().getIntExtra("type", -1);
        if (type == 0)
            titleBar.setTitle("新增地址");
        else if (type == 1) {
            titleBar.setTitle("编辑地址");
            titleBar.setRightDrawable(R.drawable.delete_grey_icon);
            addressBean = (AddressBean) getIntent().getSerializableExtra("addressBean");
            if (addressBean == null)
                return;
            etReceivingName.setText(addressBean.getReceive_name());
            etPhoneNumber.setText(addressBean.getReceive_phone());
            tvReceivingAddress.setText(addressBean.getArea());
            etDetailAddress.setText(addressBean.getDetail());
            if (addressBean.getIs_default() == 0)
                ivIsDefault.setSelected(false);
            else
                ivIsDefault.setSelected(true);
            this.lat = addressBean.getLat();
            this.lng = addressBean.getLng();
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.positioning:
                intent = new Intent(this, PositioningActivity.class);
                startActivityForResult(intent, POSITIONING);
                break;
            case R.id.isDefault:
                ivIsDefault.setSelected(!ivIsDefault.isSelected());
                break;
            case R.id.determine:
                if (type == 0)
                    addressPresenter.addAddress();
                else
                    addressPresenter.updateAddress();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case POSITIONING:
                if (data == null)
                    return;
                String address = data.getStringExtra("address");
                if (!TextUtils.isEmpty(address))
                    tvReceivingAddress.setText(address);
                lat = data.getDoubleExtra("lat", 0);
                lng = data.getDoubleExtra("lng", 0);
                break;
        }
    }


    @Override
    public void clickRight() {
        if (addressBean != null)
            addressPresenter.deleteAddress(addressBean.getId());

    }

    @Override
    public void addAddressCompleted(AddressBean addressBean) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void updateAddressCompleted(AddressBean addressBean) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void deleteAddressCompleted() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        if (TextUtils.isEmpty(getToken()))
            return null;
        map.put("token", getToken());
        if (type == 1 && addressBean != null)
            map.put("id", addressBean.getId());
        if (!TextUtils.isEmpty(etReceivingName.getText().toString().trim()))
            map.put("receive_name", etReceivingName.getText().toString().trim());
        else {
            showToast("收货人姓名不能为空");
            return null;
        }
        if (!TextUtils.isEmpty(etPhoneNumber.getText().toString().trim()))
            map.put("receive_phone", etPhoneNumber.getText().toString().trim());
        else {
            showToast("手机号码不能为空");
            return null;
        }
        if (!TextUtils.isEmpty(tvReceivingAddress.getText().toString().trim()))
            map.put("area", tvReceivingAddress.getText().toString().trim());
        else {
            showToast("收货地址不能为空");
            return null;
        }
        map.put("lat", lat + "");
        map.put("lng", lng + "");
        if (!TextUtils.isEmpty(etDetailAddress.getText().toString().trim()))
            map.put("detail", etDetailAddress.getText().toString().trim());
        else {
            showToast("详细地址不能为空");
            return null;
        }
        if (ivIsDefault.isSelected())
            map.put("default", 1 + "");
        else
            map.put("default", 0 + "");
        return map;
    }
}

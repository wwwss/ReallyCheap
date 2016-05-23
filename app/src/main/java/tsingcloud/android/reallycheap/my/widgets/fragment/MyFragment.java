package tsingcloud.android.reallycheap.my.widgets.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import tsingcloud.android.core.widgets.fragment.BaseFragment;
import tsingcloud.android.model.bean.UserBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.presenter.MyPresenter;
import tsingcloud.android.reallycheap.my.view.MyView;
import tsingcloud.android.reallycheap.my.widgets.activity.AddressManagementActivity;
import tsingcloud.android.reallycheap.my.widgets.activity.CollectBabyActivity;
import tsingcloud.android.reallycheap.my.widgets.activity.LoginActivity;
import tsingcloud.android.reallycheap.my.widgets.activity.MessagePromptActivity;
import tsingcloud.android.reallycheap.my.widgets.activity.MyOrderActivity;
import tsingcloud.android.reallycheap.my.widgets.activity.PersonalCenterActivity;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;

/**
 * Created by admin on 2016/3/21.
 * 我的
 */
public class MyFragment extends BaseFragment implements View.OnClickListener, MyView {

    private ImageView ivMassage;
    private TextView tvName;
    private TextView tvPhoneNumber;
    private ImageView ivAvatar;
    private MyPresenter myPresenter;
    private UserBean userBean;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    /**
     * 初始化控件及变量
     */
    @Override
    protected void setUpView() {
        myPresenter = new MyPresenter(this);
        ivMassage = (ImageView) view.findViewById(R.id.message);
        ivAvatar = (ImageView) view.findViewById(R.id.avatar);
        tvName = (TextView) view.findViewById(R.id.name);
        tvPhoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
        ivMassage.setOnClickListener(this);
        view.findViewById(R.id.all_order_enter).setOnClickListener(this);
        view.findViewById(R.id.all_order_enter_icon).setOnClickListener(this);
        view.findViewById(R.id.stayPay).setOnClickListener(this);
        view.findViewById(R.id.stayReceiving).setOnClickListener(this);
        view.findViewById(R.id.completed).setOnClickListener(this);
        view.findViewById(R.id.refund).setOnClickListener(this);
        view.findViewById(R.id.collection).setOnClickListener(this);
        view.findViewById(R.id.address).setOnClickListener(this);
        view.findViewById(R.id.kefu).setOnClickListener(this);
        view.findViewById(R.id.about_us).setOnClickListener(this);
        view.findViewById(R.id.editUserInfo).setOnClickListener(this);
    }

    @Override
    protected void setUpData() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    //    public static MyFragment newInstance() {
//        Bundle args = new Bundle();
//        MyFragment fragment = new MyFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onResume() {
        super.onResume();
        myPresenter.getUserInfo();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.kefu) {
        } else if (v.getId() == R.id.about_us) {
        } else {
            if (userBean == null) {
                intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                return;
            }
            switch (v.getId()) {
                case R.id.message:
                    intent = new Intent(context, MessagePromptActivity.class);
                    startActivity(intent);
                    break;
                case R.id.editUserInfo:
                    intent = new Intent(context, PersonalCenterActivity.class);
                    intent.putExtra("userBean", userBean);
                    startActivity(intent);
                    break;
                case R.id.address:
                    intent = new Intent(context, AddressManagementActivity.class);
                    startActivity(intent);
                    break;
                case R.id.all_order_enter_icon:
                case R.id.all_order_enter:
                    intent = new Intent(context, MyOrderActivity.class);
                    intent.putExtra("orderStatus", 0);
                    startActivity(intent);
                    break;
                case R.id.stayPay:
                    intent = new Intent(context, MyOrderActivity.class);
                    intent.putExtra("orderStatus", 1);
                    startActivity(intent);
                    break;
                case R.id.stayReceiving:
                    intent = new Intent(context, MyOrderActivity.class);
                    intent.putExtra("orderStatus", 2);
                    startActivity(intent);
                    break;
                case R.id.completed:
                    intent = new Intent(context, MyOrderActivity.class);
                    intent.putExtra("orderStatus", 3);
                    startActivity(intent);
                    break;
                case R.id.refund:
                    intent = new Intent(context, MyOrderActivity.class);
                    intent.putExtra("orderStatus", 4);
                    startActivity(intent);
                    break;
                case R.id.collection:
                    intent = new Intent(context, CollectBabyActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public void setUserInfo(UserBean userBean) {
        this.userBean = userBean;
        if (userBean == null)
            return;
        if (TextUtils.isEmpty(userBean.getName()))
            tvName.setText("亲点我修改昵称");
        else
            tvName.setText(userBean.getName());
        tvPhoneNumber.setText(userBean.getPhone());
        ImageLoaderUtils.displayRoundedImageView(context, ivAvatar, userBean.getImage(), R.drawable.default_avatar_icon, R.drawable.default_avatar_icon);
        if (0 == userBean.getIs_new_msg())
            ivMassage.setSelected(true);
        else
            ivMassage.setSelected(false);
    }

}

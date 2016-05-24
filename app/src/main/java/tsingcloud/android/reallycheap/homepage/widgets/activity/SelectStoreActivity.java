package tsingcloud.android.reallycheap.homepage.widgets.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.widgets.activity.BaseListActivity;
import tsingcloud.android.core.widgets.pull.BaseViewHolder;
import tsingcloud.android.core.widgets.pull.PullRecycler;
import tsingcloud.android.model.bean.ShopBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.homepage.presenter.SelectStorePresenter;
import tsingcloud.android.reallycheap.homepage.view.SelectStoreView;

/**
 * Created by admin on 2016/5/24.
 * 选择店面
 */
public class SelectStoreActivity extends BaseListActivity<ShopBean> implements SelectStoreView {

    private static final int LOCATION = 0;
    private SelectStorePresenter selectStorePresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_select_store, R.string.select_store);
    }

    @Override
    protected void setUpView() {
        selectStorePresenter = new SelectStorePresenter(this);
        recycler = (PullRecycler) findViewById(R.id.pullRecycler);
        findViewById(R.id.linearLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPosition();
            }
        });
    }

    private void getPosition() {
        //版本判断
        if (Build.VERSION.SDK_INT >= 23) {
            //减少是否拥有权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //弹出对话框接收权限
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION);
                return;
            } else {
                showDialog("正在获取位置信息...");
                selectStorePresenter.getLocation(this);
            }
        } else {
            showDialog("正在获取位置信息...");
            selectStorePresenter.getLocation(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showDialog("正在获取位置信息...");
            selectStorePresenter.getLocation(this);
            //TODO:已授权
        } else {
            //TODO:用户拒绝
        }
    }

    @Override
    public void clickLeft() {
        if (!TextUtils.isEmpty(getShopId()))
            super.clickLeft();
    }

    @Override
    protected void setUpData() {
        super.setUpData();
        mDataList = new ArrayList<>();
        recycler.setOnRefreshListener(this);
        recycler.onRefresh();
        recycler.enableLoadMore(false);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shops, parent, false);
        return new SelectStoreViewHolder(view);
    }

    @Override
    public void onRefresh(int action) {
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            selectStorePresenter.getShopsData();
        }

    }

    @Override
    public void setShopsData(List<ShopBean> shopNameBeans) {
        recycler.onRefreshCompleted();
        if (shopNameBeans != null && shopNameBeans.size() > 0) {
            mDataList.clear();
            mDataList.addAll(shopNameBeans);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setLocation(double lat, double lng) {
        dismissDialog();
        selectStorePresenter.getShopsData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        selectStorePresenter.destroyLocation();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (TextUtils.isEmpty(getShopId())) return true;
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    private class SelectStoreViewHolder extends BaseViewHolder {
        public TextView tvName;// 店名
        public TextView tvAddress;// 详细地址

        public SelectStoreViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvAddress = (TextView) itemView.findViewById(R.id.address);
        }

        @Override
        public void onBindViewHolder(int position) {
            ShopBean shopBean = mDataList.get(position);
            tvName.setText(shopBean.getName());
            tvAddress.setText(shopBean.getAddress());
        }

        @Override
        public void onItemClick(View view, int position) {
            ShopBean shopBean = mDataList.get(position);
            Intent intent = new Intent();
            intent.putExtra("shopBean", shopBean);
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}

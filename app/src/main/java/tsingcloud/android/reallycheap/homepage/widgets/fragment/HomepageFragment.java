package tsingcloud.android.reallycheap.homepage.widgets.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.interfaces.OnTabSwitchToListener;
import tsingcloud.android.core.utils.LogUtils;
import tsingcloud.android.core.widgets.fragment.BaseFragment;
import tsingcloud.android.model.bean.BannerBean;
import tsingcloud.android.model.bean.ClassifyBean;
import tsingcloud.android.model.bean.HotClassifyBean;
import tsingcloud.android.model.bean.ShopBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.homepage.presenter.HomepagePresenter;
import tsingcloud.android.reallycheap.homepage.view.HomepageView;
import tsingcloud.android.reallycheap.homepage.widgets.activity.SearchActivity;
import tsingcloud.android.reallycheap.homepage.widgets.adapter.HotClassifyAdapter;
import tsingcloud.android.reallycheap.homepage.widgets.adapter.HotProductListViewAdapter;
import tsingcloud.android.reallycheap.utils.ListViewUitls;
import tsingcloud.android.reallycheap.widgets.view.SAGridView;
import tsingcloud.android.reallycheap.widgets.view.SlideShowView;

/**
 * Created by admin on 2016/3/16.
 * 首页
 */
public class HomepageFragment extends BaseFragment implements HomepageView, View.OnClickListener, AdapterView.OnItemClickListener {

    private HomepagePresenter homepagePresenter;
    private TextView tvShopName;
    private ImageView ivUpward;
    private ScrollView scrollView;
    private SlideShowView slideShowView;
    private SAGridView saGridView;
    private ListView listView;
    private List<ClassifyBean> classifyBeanList;
    private HotClassifyAdapter adapter;
    private List<HotClassifyBean> hotClassifyBeanList;
    private HotProductListViewAdapter listViewAdapter;
    private OnTabSwitchToListener onTabSwitchToListener;
    private LatLng startLatlng = null;
    private int LOCATION = 0;

    public void setOnTabSwitchToListener(OnTabSwitchToListener onTabSwitchToListener) {
        this.onTabSwitchToListener = onTabSwitchToListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    protected void setUpView() {
        homepagePresenter = new HomepagePresenter(this);
        tvShopName = (TextView) view.findViewById(R.id.shopName);
        view.findViewById(R.id.search).setOnClickListener(this);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        slideShowView = (SlideShowView) view.findViewById(R.id.slideshowView);
        saGridView = (SAGridView) view.findViewById(R.id.classifyGridView);
        listView = (ListView) view.findViewById(R.id.classifyListView);
        ivUpward = (ImageView) view.findViewById(R.id.upward);
        ivUpward.setOnClickListener(this);
        classifyBeanList = new ArrayList<>();
        adapter = new HotClassifyAdapter(context, classifyBeanList);
        saGridView.setAdapter(adapter);
        saGridView.setOnItemClickListener(this);
        hotClassifyBeanList = new ArrayList<>();
        listViewAdapter = new HotProductListViewAdapter(context, hotClassifyBeanList, homepagePresenter);
        listView.setAdapter(listViewAdapter);
    }

    @Override
    protected void setUpData() {
        //版本判断
        if (Build.VERSION.SDK_INT >= 23) {
            //减少是否拥有权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context.getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //弹出对话框接收权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION);
                return;
            } else {
                showDialog("正在获取位置信息...");
                homepagePresenter.getLocation(context);
            }
        } else {
            showDialog("正在获取位置信息...");
            homepagePresenter.getLocation(context);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showDialog("正在获取位置信息...");
            homepagePresenter.getLocation(context);
            //TODO:已授权
        } else {
            //TODO:用户拒绝
        }
    }

    @Override
    public void setShopsData(List<ShopBean> shopBeans) {
        if (startLatlng == null && shopBeans == null)
            return;
        int minDistanceIndex = 0;
        double oldDistance = Double.MAX_VALUE;
        for (int i = 0; i < shopBeans.size(); i++) {
            ShopBean shopBean = shopBeans.get(i);
            // 计算量坐标点距离
            LatLng endLatlng = new LatLng(shopBean.getLat(), shopBean.getLng());
            double newDistance = AMapUtils.calculateLineDistance(startLatlng, endLatlng);
            shopBean.setDistance(newDistance);
            if (newDistance < oldDistance) {
                minDistanceIndex = i;
                oldDistance = newDistance;
            }
        }
        tvShopName.setText(shopBeans.get(minDistanceIndex).getName());
        String shopId = shopBeans.get(minDistanceIndex).getId();
        LocalCache.get(context).put("shopId", shopId);
        homepagePresenter.getHomepageData(shopId);
    }

    @Override
    public void setBannerData(List<BannerBean> bannerBeans) {
        slideShowView.setData(bannerBeans);
    }

    @Override
    public void setHotClassifyData(List<ClassifyBean> classifyBeans) {
        classifyBeanList.addAll(classifyBeans);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setHotProductData(List<HotClassifyBean> hotClassifyBeans) {
        if (null != hotClassifyBeans && hotClassifyBeans.size() > 0) {
            hotClassifyBeanList.addAll(hotClassifyBeans);
            listViewAdapter.notifyDataSetChanged();
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.bottom_hint_icon);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            listView.addFooterView(imageView);
            ListViewUitls.setListViewHeightBasedOnChildren(listView);
            ivUpward.setVisibility(View.VISIBLE);
        } else {
            ivUpward.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void setLocation(double lat, double lng) {
        dismissDialog();
        startLatlng = new LatLng(lat, lng);
        homepagePresenter.getShopsData();
        LogUtils.d("定位成功", "经度----" + lat + "纬度----" + lng);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                Intent intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.upward:
                LogUtils.d(TAG, "回到顶部");
                scrollView.scrollTo(0, 20);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ClassifyBean classifyBean = (ClassifyBean) parent.getItemAtPosition(position);
        onTabSwitchToListener.onTabSwitch(1, classifyBean);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homepagePresenter.destroyLocation();
    }
}

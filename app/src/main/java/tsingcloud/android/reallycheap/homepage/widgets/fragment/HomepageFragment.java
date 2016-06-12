package tsingcloud.android.reallycheap.homepage.widgets.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.interfaces.OnTabListener;
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
import tsingcloud.android.reallycheap.homepage.widgets.activity.SelectStoreActivity;
import tsingcloud.android.reallycheap.homepage.widgets.adapter.HotClassifyAdapter;
import tsingcloud.android.reallycheap.homepage.widgets.adapter.HotProductListViewAdapter;
import tsingcloud.android.reallycheap.utils.ListViewUtils;
import tsingcloud.android.reallycheap.utils.NetUtils;
import tsingcloud.android.reallycheap.widgets.view.SAGridView;
import tsingcloud.android.reallycheap.widgets.view.SlideShowView;

/**
 * Created by admin on 2016/3/16.
 * 首页
 */
public class HomepageFragment extends BaseFragment implements HomepageView, View.OnClickListener,
        AdapterView.OnItemClickListener {

    private static final int SELECT_STORE = 0;
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
    private OnTabListener onTabListener;
    private LatLng startLatLng = null;
    private View reloadView;

    public void setOnTabListener(OnTabListener onTabListener) {
        this.onTabListener = onTabListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    protected void setUpView() {
        homepagePresenter = new HomepagePresenter(this);
        tvShopName = (TextView) view.findViewById(R.id.shopName);
        tvShopName.setOnClickListener(this);
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
        listViewAdapter = new HotProductListViewAdapter(context, this, hotClassifyBeanList,
                homepagePresenter);
        listView.setAdapter(listViewAdapter);
    }

    @Override
    protected void setUpData() {
        if (!NetUtils.isConnected(context)) {
            showReloadView();
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionGen.with(HomepageFragment.this)
                    .addRequestCode(100)
                    .permissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    .request();
        } else {
            openLocation();
//            showDialog("正在获取位置信息...");
//            homepagePresenter.getLocation(context);
        }

    }


    @Override
    public void setShopsData(List<ShopBean> shopBeans) {
        if (startLatLng == null && shopBeans == null)
            return;
        int minDistanceIndex = 0;
        double oldDistance = Double.MAX_VALUE;
        for (int i = 0; i < shopBeans.size(); i++) {
            ShopBean shopBean = shopBeans.get(i);
            // 计算量坐标点距离
            LatLng endLatLng = new LatLng(shopBean.getLat(), shopBean.getLng());
            double newDistance = AMapUtils.calculateLineDistance(startLatLng, endLatLng);
            shopBean.setDistance(newDistance);
            if (newDistance < oldDistance) {
                minDistanceIndex = i;
                oldDistance = newDistance;
            }
        }
        tvShopName.setText(shopBeans.get(minDistanceIndex).getName());
        ShopBean shopBean = shopBeans.get(minDistanceIndex);
        LocalCache.get(context).put("shopBean", shopBean);
//        LocalCache.get(context).put("shopId", shopId);
//        LocalCache.get(context).put("range",range);
        homepagePresenter.getHomepageData(shopBean.getId());
    }

    @Override
    public void setBannerData(List<BannerBean> bannerBeans) {
        slideShowView.setData(bannerBeans);
    }

    @Override
    public void setHotClassifyData(List<ClassifyBean> classifyBeans) {
        if (classifyBeans != null && classifyBeans.size() > 0) {
            classifyBeanList.clear();
            classifyBeanList.addAll(classifyBeans);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setHotProductData(List<HotClassifyBean> hotClassifyBeans) {
        if (null != hotClassifyBeans) {
            hotClassifyBeanList.clear();
            hotClassifyBeanList.addAll(hotClassifyBeans);
            listViewAdapter.notifyDataSetChanged();
            if (hotClassifyBeans.size() > 0) {
                if (listView.getFooterViewsCount() == 0) {
                    ImageView imageView = new ImageView(context);
                    imageView.setImageResource(R.drawable.bottom_hint_icon);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    listView.addFooterView(imageView);
                    ivUpward.setVisibility(View.VISIBLE);
                    hideReloadView();
                }
                ListViewUtils.setListViewHeightBasedOnChildren(listView);
            } else {
                showReloadView();
            }
        }
    }

    public void showReloadView() {
        listView.setVisibility(View.GONE);
        ivUpward.setVisibility(View.GONE);
        if (reloadView == null) {
            ViewStub noDataViewStub = (ViewStub) view.findViewById(R.id.reload);
            reloadView = noDataViewStub.inflate();
            reloadView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homepagePresenter.getLocation(context);
                }
            });
        } else
            reloadView.setVisibility(View.VISIBLE);
    }

    public void hideReloadView() {
        ivUpward.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        if (reloadView != null)
            reloadView.setVisibility(View.GONE);
    }

    @Override
    public void setLocation(double lat, double lng) {
        dismissDialog();
        if (lat == 0 && lng == 0) {
            tvShopName.setText("定位失败");
            LocalCache.get(context).remove("shopId");
            startActivityForResult(new Intent(context, SelectStoreActivity.class), SELECT_STORE);
            return;
        }
        startLatLng = new LatLng(lat, lng);
        homepagePresenter.getShopsData();
        LogUtils.d("定位结束", "经度----" + lat + "纬度----" + lng);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.search:
                intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.upward:
                LogUtils.d(TAG, "回到顶部");
                scrollView.scrollTo(0, 20);
                break;
            case R.id.shopName:
                intent = new Intent(context, SelectStoreActivity.class);
                startActivityForResult(intent, SELECT_STORE);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ClassifyBean classifyBean = (ClassifyBean) parent.getItemAtPosition(position);
        onTabListener.onTabSwitch(1, classifyBean);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homepagePresenter.destroyLocation();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case SELECT_STORE:
                ShopBean shopBean = (ShopBean) data.getSerializableExtra("shopBean");
                if (shopBean == null) return;
                //  if (shopBean.getId().equals(getShopId())) return;
                LocalCache.get(context).put("shopBean", shopBean);
                tvShopName.setText(shopBean.getName());
                homepagePresenter.getHomepageData(shopBean.getId());
                onTabListener.onTabRefresh(1, null);
                break;
            case HotProductListViewAdapter.PRODUCT_DETAILS:
                onTabListener.onTabSwitch(2, null);
                break;
        }
    }

    @PermissionSuccess(requestCode = 100)
    public void openLocation() {
        LogUtils.d(TAG, "=============================");
        showDialog("正在获取位置信息...");
        homepagePresenter.getLocation(context);
    }

    @PermissionFail(requestCode = 100)
    public void failLocation() {
        LogUtils.d(TAG, "------------------------------");
        showToast("权限请求被拒绝");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

//        //版本判断
//        if (Build.VERSION.SDK_INT >= 23) {
//            //减少是否拥有权限
//            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context.getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
//            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//                //弹出对话框接收权限
//                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION);
//                return;
//            } else {
//                showDialog("正在获取位置信息...");
//                homepagePresenter.getLocation(context);
//            }
//        } else {
//            showDialog("正在获取位置信息...");
//            homepagePresenter.getLocation(context);
//        }
}

package tsingcloud.android.reallycheap.homepage.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.core.utils.LogUtils;
import tsingcloud.android.model.bean.HomepageDataBean;
import tsingcloud.android.model.bean.ShopBean;
import tsingcloud.android.reallycheap.homepage.model.HomepageModel;
import tsingcloud.android.reallycheap.homepage.model.HomepageModelImpl;
import tsingcloud.android.reallycheap.homepage.view.HomepageView;

/**
 * Created by admin on 2016/3/24.
 * 首页控制器
 */
public class HomepagePresenter extends BasePresenter implements AMapLocationListener {
    private HomepageView homePageView;
    private HomepageModel homepageModel;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption;


    public HomepagePresenter(HomepageView homePageView) {
        super(homePageView);
        this.homePageView = homePageView;
        homepageModel = new HomepageModelImpl();
    }

    public void getHomepageData(String shopId) {
        homepageModel.getHomepageData(shopId, new OnNSURLRequestListener<HomepageDataBean>() {

            @Override
            public void onSuccess(HomepageDataBean homepageDataBean) {
                homePageView.setBannerData(homepageDataBean.getAdvertlist());
                homePageView.setHotClassifyData(homepageDataBean.getCategorylist());
                homePageView.setHotProductData(homepageDataBean.getProductlist());
            }

            @Override
            public void onFailure(String msg) {
                homePageView.showToast(msg);
            }
        },homePageView.getTAG());
    }

    public void getLocation(Context context) {
        //初始化定位
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(false);
        // 设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        // 设置是否强制刷新WIFI，默认为强制刷新
        //mLocationOption.setWifiActiveScan(true);
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        //mLocationOption.setMockEnable(false);
        // 设置定位间隔,单位毫秒,默认为2000ms
        // mLocationOption.setInterval(60 * 1000);
        // 给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        //homePageView.getDialog().show();
    }

    /**
     * 获取店名称
     */
    public void getShopsData() {
        homepageModel.getShopsData(new OnNSURLRequestListener<List<ShopBean>>() {
            @Override
            public void onSuccess(List<ShopBean> shopBeans) {
                homePageView.setShopsData(shopBeans);
            }

            @Override
            public void onFailure(String msg) {
                homePageView.showToast(msg);
            }
        },homePageView.getTAG());
    }

    /**
     * 添加产品到购物车
     */
    public void addShoppingCart(Map<String, String> map) {
        if (TextUtils.isEmpty(homePageView.getToken()))
            return;
        map.put("token", homePageView.getToken());
        if (TextUtils.isEmpty(homePageView.getShopId()))
            return;
        map.put("shop_id", homePageView.getShopId());
        homepageModel.addShoppingCart(map, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                homePageView.showToast(response);
            }

            @Override
            public void onFailure(String msg) {
                homePageView.showToast(msg);
            }
        },homePageView.getTAG());
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                // 定位成功回调
                homePageView.setLocation(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                mLocationClient.stopLocation();
            } else {
                homePageView.setLocation(0, 0);
                homePageView.showToast("定位失败");
                LogUtils.e("定位失败", "ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
        } else {
            homePageView.setLocation(0, 0);
            homePageView.showToast("定位失败");
            LogUtils.e("定位失败", "ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
        }
    }

    /**
     * 销毁定位
     */
    public void destroyLocation() {
        if (mLocationClient != null)
            mLocationClient.onDestroy();
    }
}

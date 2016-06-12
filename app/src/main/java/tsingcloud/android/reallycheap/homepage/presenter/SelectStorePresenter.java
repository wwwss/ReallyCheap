package tsingcloud.android.reallycheap.homepage.presenter;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.List;

import tsingcloud.android.core.utils.LogUtils;
import tsingcloud.android.model.bean.ShopBean;
import tsingcloud.android.reallycheap.homepage.model.SelectStoreModel;
import tsingcloud.android.reallycheap.homepage.model.SelectStoreModelImpl;
import tsingcloud.android.reallycheap.homepage.view.SelectStoreView;
import tsingcloud.android.reallycheap.presenter.ProductBasePresenter;

/**
 * Created by admin on 2016/5/24.
 * 选择店面控制器
 */
public class SelectStorePresenter extends ProductBasePresenter implements AMapLocationListener {
    private SelectStoreView selectStoreView;
    private SelectStoreModel selectStoreModel;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption;

    public SelectStorePresenter(SelectStoreView selectStoreView) {
        super(selectStoreView);
        this.selectStoreView = selectStoreView;
        selectStoreModel=new SelectStoreModelImpl();
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
        selectStoreModel.getShopsData(new AbstractOnNSURLRequestListener<List<ShopBean>>() {
            @Override
            public void onSuccess(List<ShopBean> shopBeans) {
                selectStoreView.setShopsData(shopBeans);
            }
        }, selectStoreView.getTAG());
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                // 定位成功回调
                selectStoreView.setLocation(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                mLocationClient.stopLocation();
            } else {
                selectStoreView.setLocation(0, 0);
                //homePageView.showToast("定位失败");
                LogUtils.e("定位失败", "ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
        } else {
            selectStoreView.setLocation(0, 0);
            //homePageView.showToast("定位失败");
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

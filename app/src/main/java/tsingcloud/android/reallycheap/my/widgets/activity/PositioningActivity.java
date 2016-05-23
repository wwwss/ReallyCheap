package tsingcloud.android.reallycheap.my.widgets.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;

import tsingcloud.android.core.widgets.activity.BaseListActivity;
import tsingcloud.android.core.widgets.pull.BaseViewHolder;
import tsingcloud.android.core.widgets.pull.PullRecycler;
import tsingcloud.android.reallycheap.R;

/**
 * Created by admin on 2016/4/17.
 * 定位页面
 */
public class PositioningActivity extends BaseListActivity<PoiItem> implements AMap.OnCameraChangeListener, LocationSource, AMapLocationListener, PoiSearch.OnPoiSearchListener, GeocodeSearch.OnGeocodeSearchListener, View.OnClickListener {
    private static final int TEXT_SEARCH = 0;
    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient aMapLocationClient;
    protected AMapLocationClientOption aMapLocationClientOption;
    private UiSettings uiSettings;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private PoiResult poiResult;
    // 当前页面，从0开始计数
    private int currentPage = 0;
    private PullRecycler recycler;
    protected String address;
    private Marker locationMarker;
    // 坐标转换
    private GeocodeSearch geocoderSearch;
    private Bundle savedInstanceState;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;


    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_positioning);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);// 必须要写
    }

    @Override
    protected void setUpView() {
        recycler = (PullRecycler) findViewById(R.id.pullRecycler);
        findViewById(R.id.searchInput).setOnClickListener(this);
        initMapView();

    }

    private void initMapView() {
        if (aMap == null) {
            aMap = mapView.getMap();
            uiSettings = aMap.getUiSettings();
        }
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker_icon));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationRotateAngle(180);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        uiSettings.setMyLocationButtonEnabled(false); // 是否显示默认的定位按钮
        uiSettings.setTiltGesturesEnabled(false);// 设置地图是否可以倾斜
        uiSettings.setScaleControlsEnabled(true);// 设置地图默认的比例尺是否显示
        uiSettings.setZoomControlsEnabled(true);// 设置了地图是否允许显示缩放按钮。
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
        initMapListener();
    }

    private void initMapListener() {
        aMap.setOnCameraChangeListener(this);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        // 设置自定义InfoWindow样式
        // aMap.setInfoWindowAdapter(this);
    }

    @Override
    protected void setUpData() {
        adapter = new ListAdapter();
        mDataList = new ArrayList<>();
        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        recycler.setAdapter(adapter);
        recycler.enablePullToRefresh(false);
        recycler.enableLoadMore(true);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_seach_location, parent, false);
        return new PoiViewHolder(view);
    }

    @Override
    public void onRefresh(int action) {
        if (poiResult.getPageCount() - 1 > currentPage) {
            currentPage++;
            // 设置查后一页
            query.setPageNum(currentPage);
            poiSearch.searchPOIAsyn();
            recycler.enableLoadMore(true);
        } else {
            recycler.enableLoadMore(false);
            recycler.onRefreshCompleted();
        }

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (locationMarker != null) {
            LatLng latLng = cameraPosition.target;
            locationMarker.setPosition(latLng);
            if (locationMarker.isInfoWindowShown()) {
                locationMarker.hideInfoWindow();
            }

        }
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LatLonPoint latLonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 0, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
        doSearchQuery("", latLonPoint);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.mListener = onLocationChangedListener;
        if (aMapLocationClient == null) {
            aMapLocationClient = new AMapLocationClient(getApplicationContext());
            aMapLocationClientOption = new AMapLocationClientOption();
            aMapLocationClient.setLocationListener(this); // 设置定位监听
//            aMapLocationClientOption.setOnceLocation(false); // 设置是否只定位一次,默认为false
            aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy); // 设置为高精度定位模式
            aMapLocationClientOption.setInterval(60 * 1000); // 设置定位间隔,单位毫秒,默认为2000ms
            aMapLocationClient.setLocationOption(aMapLocationClientOption);// 设置定位参数
            aMapLocationClient.startLocation();
            showDialog("正在获取您的位置...");
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (aMapLocationClient != null) {
            aMapLocationClient.stopLocation();
            aMapLocationClient.onDestroy();
        }
        aMapLocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        dismissDialog();
        if (mListener != null && aMapLocation != null) if (aMapLocation.getErrorCode() == 0) {
            address = aMapLocation.getAddress();
            mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            aMapLocationClient.stopLocation();
            locationMarker = aMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .draggable(true).snippet(address).position(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
            locationMarker.showInfoWindow();
        }

    }

    /**
     * 开始进行poi搜索
     */
    private void doSearchQuery(String keyWord, LatLonPoint latLonPoint) {
        currentPage = 0;
        mDataList.clear();
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(keyWord, "12", "021");
        query.setPageSize(10);// 设置每页最多返回多少条
        query.setPageNum(currentPage);// 设置查第一页
        PoiSearch.SearchBound searchBound = new PoiSearch.SearchBound(latLonPoint, 500);
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(searchBound);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        this.poiResult = poiResult;
        recycler.onRefreshCompleted();
        if (i == 1000) {
            // 搜索poi的结果
            if (poiResult != null && poiResult.getQuery() != null) {
                // 是否是同一条poiResult = poiResult;
                if (poiResult.getQuery().equals(query)) {
                    mDataList.addAll(poiResult.getPois());
                    if (mDataList.size() > 0)
                        adapter.notifyDataSetChanged();
                }
            }
        } else {
            showToast("搜索失败，请重新拖动地图");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
            RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
            if (address != null && address.getFormatAddress() != null) {
                locationMarker.setSnippet(address.getFormatAddress());
                locationMarker.showInfoWindow();
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchInput:
                Intent intent = new Intent(this, TextSearchActivity.class);
                startActivityForResult(intent, TEXT_SEARCH);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case TEXT_SEARCH:
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }

    private class PoiViewHolder extends BaseViewHolder {
        public TextView tvName;// 地名
        public TextView tvAddress;// 详细地址

        public PoiViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvAddress = (TextView) itemView.findViewById(R.id.address);
        }

        @Override
        public void onBindViewHolder(int position) {
            PoiItem poiItem = mDataList.get(position);
            tvName.setText(poiItem.getTitle());
            tvAddress.setText(poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet());
        }

        @Override
        public void onItemClick(View view, int position) {
            PoiItem poiItem = mDataList.get(position);
            Intent intent = new Intent();
            intent.putExtra("address", poiItem.getTitle());
            intent.putExtra("lat", poiItem.getLatLonPoint().getLatitude());
            intent.putExtra("lng", poiItem.getLatLonPoint().getLongitude());
            setResult(RESULT_OK, intent);
            finish();
        }

    }

//    @Override
//    public View getInfoWindow(Marker marker) {
//        View infoWindow = getLayoutInflater().inflate(
//                R.layout.custom_info_window, null);
//        return null;
//    }
//
//    @Override
//    public View getInfoContents(Marker marker) {
//        return null;
//    }
}

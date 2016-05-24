package tsingcloud.android.reallycheap.homepage.view;

import java.util.List;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.ShopBean;

/**
 * Created by admin on 2016/5/24.
 * 选择店面页面接口
 */

public  interface  SelectStoreView extends BaseView {
    /**
     * 设置店铺数据
     *
     * @param shopNameBeans 店铺实体对象列表
     */
    void setShopsData(List<ShopBean> shopNameBeans);
    /**
     * 设置定位
     *
     * @param lat 经度
     * @param lng 纬度
     */
    void setLocation(double lat, double lng);

}

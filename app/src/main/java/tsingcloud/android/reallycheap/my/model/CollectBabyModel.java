package tsingcloud.android.reallycheap.my.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.model.bean.CollectBabyBean;

/**
 * Created by admin on 2016/4/29.
 * 收藏宝贝数据接口类
 */
public interface CollectBabyModel {
    /**
     * 获取收藏列表
     * @param map                 请求参数集合
     * @param listener            请求返回监听
     * @param totalPagesListener  请求返回列表长度监听
     */
    void getCollectBabyList(Map<String, String> map, OnNSURLRequestListener<List<CollectBabyBean>> listener, OnSetListTotalPagesListener totalPagesListener,String tag);

    /**
     * 删除收藏
     * @param map
     * @param listener
     */
    void deleteCollectBaby(Map<String, String> map, OnNSURLRequestListener<String> listener,String tag);

    /**
     * 清空收藏
     * @param map
     * @param listener
     */
    void clearCollectBaby(Map<String, String> map, OnNSURLRequestListener<String> listener,String tag);
}

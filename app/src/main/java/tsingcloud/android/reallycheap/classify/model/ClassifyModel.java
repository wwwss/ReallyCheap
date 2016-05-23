package tsingcloud.android.reallycheap.classify.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.ClassifyDataBean;
import tsingcloud.android.model.bean.SmallClassifyBean;

/**
 * Created by admin on 2016/3/24.
 */
public interface ClassifyModel {
    void getClassifyList(String shopId, String categoryId, OnNSURLRequestListener<ClassifyDataBean> listener,String tag);

    void getSmallClassifyList(Map<String, String> map, OnNSURLRequestListener<List<SmallClassifyBean>> listener,String tag);
}

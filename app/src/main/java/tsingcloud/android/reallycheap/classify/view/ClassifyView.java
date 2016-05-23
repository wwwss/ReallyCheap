package tsingcloud.android.reallycheap.classify.view;

import java.util.List;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.ClassifyBean;
import tsingcloud.android.model.bean.SmallClassifyBean;

/**
 * Created by admin on 2016/3/24.
 * 分类View
 */
public interface ClassifyView extends BaseView {
    /**
     * 设置分类列表
     *
     * @param classifyBeans
     */
    void setClassifyListData(List<ClassifyBean> classifyBeans);

    /**
     * 设置小分类列表
     *
     * @param smallClassifyBeans
     */
    void setSmallClassifyListData(List<SmallClassifyBean> smallClassifyBeans);

    String getCategoryId();

    void setCategoryId(String categoryId);

}



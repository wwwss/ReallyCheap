package tsingcloud.android.reallycheap.classify.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.model.bean.ClassifyDataBean;
import tsingcloud.android.model.bean.SmallClassifyBean;
import tsingcloud.android.reallycheap.classify.model.ClassifyModel;
import tsingcloud.android.reallycheap.classify.model.ClassifyModelImpl;
import tsingcloud.android.reallycheap.classify.view.ClassifyView;

/**
 * Created by admin on 2016/3/24.
 * 分类Presenter
 */
public class ClassifyPresenter {
    private ClassifyView classifyView;
    private ClassifyModel classifyModel;

    public ClassifyPresenter(ClassifyView classifyView) {
        this.classifyView = classifyView;
        classifyModel = new ClassifyModelImpl();
    }

    /**
     * 获取分类数据
     */
    public void getClassifyData() {
        classifyModel.getClassifyList(classifyView.getShopId(), classifyView.getCategoryId(), new OnNSURLRequestListener<ClassifyDataBean>() {
            @Override
            public void onSuccess(ClassifyDataBean classifyDataBean) {
                classifyView.setClassifyListData(classifyDataBean.getCategorylist());
                classifyView.setSmallClassifyListData(classifyDataBean.getObjlist());
            }

            @Override
            public void onFailure(String msg) {
                classifyView.showToast(msg);
            }
        },classifyView.getTAG());
    }

    /**
     * 获取小分类数据
     */
    public void getSmallClassifyData(String parameter) {
        Map<String, String> map = new HashMap<>();
        map.put("shop_id", classifyView.getShopId());
        map.put("category_id", parameter);
        classifyModel.getSmallClassifyList(map, new OnNSURLRequestListener<List<SmallClassifyBean>>() {
            @Override
            public void onSuccess(List<SmallClassifyBean> smallClassifyBeans) {
                classifyView.setSmallClassifyListData(smallClassifyBeans);
            }

            @Override
            public void onFailure(String msg) {
                classifyView.showToast(msg);
            }
        },classifyView.getTAG());
    }


}


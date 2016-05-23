package tsingcloud.android.reallycheap.my.view;

import java.util.List;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.CollectBabyBean;

/**
 * Created by admin on 2016/4/29.
 */
public interface CollectBabyView extends BaseView{
    void setCollectBabyList(List<CollectBabyBean> collectBabyBeans);

    void clearCollectComplete();

    void deleteCollectComplete(int position);

    void setTotalPages(int totalPages);
}

package tsingcloud.android.reallycheap.my.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.core.presenter.BasePresenter;
import tsingcloud.android.model.bean.CollectBabyBean;
import tsingcloud.android.reallycheap.my.model.CollectBabyModel;
import tsingcloud.android.reallycheap.my.model.CollectBabyModelImpl;
import tsingcloud.android.reallycheap.my.view.CollectBabyView;

/**
 * Created by admin on 2016/4/29.
 * 收藏宝贝控制器
 */
public class CollectBabyPresenter extends BasePresenter{
    private CollectBabyView collectBabyView;
    private CollectBabyModel collectBabyModel;

    public CollectBabyPresenter(CollectBabyView collectBabyView) {
        super(collectBabyView);
        this.collectBabyView = collectBabyView;
        collectBabyModel = new CollectBabyModelImpl();
    }


    public void getCollectBabyList(int pageNum) {
        if (TextUtils.isEmpty(collectBabyView.getToken()) || TextUtils.isEmpty(collectBabyView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", collectBabyView.getToken());
        map.put("shop_id", collectBabyView.getShopId());
        map.put("page_num", pageNum + "");
        collectBabyModel.getCollectBabyList(map, new AbstractOnNSURLRequestListener<List<CollectBabyBean>>() {
            @Override
            public void onSuccess(List<CollectBabyBean> response) {
                collectBabyView.setCollectBabyList(response);
            }
        }, new OnSetListTotalPagesListener() {
            @Override
            public void setTotalPages(int totalPages) {
                collectBabyView.setTotalPages(totalPages);
            }
        },collectBabyView.getTAG());
    }

    public void deleteCollectBaby(String id, final int position) {
        if (TextUtils.isEmpty(collectBabyView.getToken()) || TextUtils.isEmpty(collectBabyView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", collectBabyView.getToken());
        map.put("id", id);
        collectBabyModel.deleteCollectBaby(map, new AbstractOnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                collectBabyView.showToast(response);
                collectBabyView.deleteCollectComplete(position);
            }

        },collectBabyView.getTAG());
    }

    public void clearCollectBaby() {
        if (TextUtils.isEmpty(collectBabyView.getToken()) || TextUtils.isEmpty(collectBabyView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", collectBabyView.getToken());
        map.put("shop_id", collectBabyView.getShopId());
        collectBabyModel.clearCollectBaby(map, new AbstractOnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                collectBabyView.showToast(response);
                collectBabyView.clearCollectComplete();
            }
        },collectBabyView.getTAG());
    }


}

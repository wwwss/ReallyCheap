package tsingcloud.android.reallycheap.my.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.model.bean.CollectBabyBean;
import tsingcloud.android.reallycheap.my.model.CollectBabyModel;
import tsingcloud.android.reallycheap.my.model.CollectBabyModelImpl;
import tsingcloud.android.reallycheap.my.view.CollectBabyView;

/**
 * Created by admin on 2016/4/29.
 * 收藏宝贝控制器
 */
public class CollectBabyPresenter {
    private CollectBabyView collectBabyView;
    private CollectBabyModel collectBabyModel;

    public CollectBabyPresenter(CollectBabyView collectBabyView) {
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
        collectBabyModel.getCollectBabyList(map, new OnNSURLRequestListener<List<CollectBabyBean>>() {
            @Override
            public void onSuccess(List<CollectBabyBean> response) {
                collectBabyView.setCollectBabyList(response);
            }

            @Override
            public void onFailure(String msg) {
                collectBabyView.showToast(msg);
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
        collectBabyModel.deleteCollectBaby(map, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                collectBabyView.showToast(response);
                collectBabyView.deleteCollectComplete(position);
            }

            @Override
            public void onFailure(String msg) {
                collectBabyView.showToast(msg);
            }
        },collectBabyView.getTAG());
    }

    public void clearCollectBaby() {
        if (TextUtils.isEmpty(collectBabyView.getToken()) || TextUtils.isEmpty(collectBabyView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", collectBabyView.getToken());
        map.put("shop_id", collectBabyView.getShopId());
        collectBabyModel.clearCollectBaby(map, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                collectBabyView.showToast(response);
                collectBabyView.clearCollectComplete();
            }

            @Override
            public void onFailure(String msg) {
                collectBabyView.showToast(msg);
            }
        },collectBabyView.getTAG());
    }


}

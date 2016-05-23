package tsingcloud.android.reallycheap.my.widgets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tsingcloud.android.model.bean.CollectBabyBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.presenter.CollectBabyPresenter;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;
import tsingcloud.android.reallycheap.widgets.view.SliderView;

public class CollectBabyAdapter extends BaseAdapter<CollectBabyBean> {

    private CollectBabyPresenter collectBabyPresenter;

    public CollectBabyAdapter(Context context, List<CollectBabyBean> list, CollectBabyPresenter collectBabyPresenter) {
        super(context, list);
        this.collectBabyPresenter = collectBabyPresenter;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SliderView slideView = (SliderView) convertView;
        if (slideView == null) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_collect_baby, null);
            slideView = new SliderView(context);
            slideView.setContentView(itemView);
            ItemCache itemCache = new ItemCache();
            itemCache.tvName = (TextView) slideView.findViewById(R.id.productName);
            itemCache.ivImage = (ImageView) slideView.findViewById(R.id.productImage);
            itemCache.tvSpec = (TextView) slideView.findViewById(R.id.productSpecification);
            itemCache.tvCurrentPrice = (TextView) slideView.findViewById(R.id.productCurrentPrice);
            itemCache.deleteHolder = (ViewGroup) slideView.findViewById(R.id.holder);
            slideView.setTag(itemCache);
        }
        ItemCache itemCache = (ItemCache) slideView.getTag();
        final CollectBabyBean collectBabyBean = list.get(position);
        slideView.shrink();
        ImageLoaderUtils.display(context, itemCache.ivImage, collectBabyBean.getProduct_image(), R.drawable.product_default_icon, R.drawable.product_default_icon);
        itemCache.tvName.setText(collectBabyBean.getProduct_name());
        itemCache.tvSpec.setText(collectBabyBean.getProduct_spec());
        itemCache.tvCurrentPrice.setText("¥" + collectBabyBean.getProduct_price());
        itemCache.deleteHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectBabyPresenter.deleteCollectBaby(collectBabyBean.getId(), position);
            }
        });
        return slideView;
    }

    private class ItemCache {
        private TextView tvName;
        private ImageView ivImage;
        private TextView tvCurrentPrice;
        private TextView tvSpec;
        private ViewGroup deleteHolder;
    }
}

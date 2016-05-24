package tsingcloud.android.reallycheap.homepage.widgets.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.homepage.presenter.HomepagePresenter;
import tsingcloud.android.reallycheap.my.widgets.activity.LoginActivity;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;

/**
 * Created by admin on 2016/3/21.
 * 产品适配器
 */
public class HotProductGridViewAdapter extends BaseAdapter<ProductBean> {

    private int oldPosition;
    private HomepagePresenter homePagePresenter;

    public HotProductGridViewAdapter(Context context, List<ProductBean> list, HomepagePresenter homePagePresenter) {
        super(context, list);
        this.homePagePresenter = homePagePresenter;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_girdview_hot_product, null);
            ItemCache itemCache = new ItemCache();
            itemCache.tvName = (TextView) convertView.findViewById(R.id.productName);
            itemCache.ivImage = (ImageView) convertView.findViewById(R.id.productImage);
            itemCache.tvNumber = (TextView) convertView.findViewById(R.id.productNum);
            itemCache.tvOriginalPrice = (TextView) convertView.findViewById(R.id.productOriginalPrice);
            itemCache.tvCurrentPrice = (TextView) convertView.findViewById(R.id.productCurrentPrice);
            itemCache.ivAdd = (ImageView) convertView.findViewById(R.id.add);
            convertView.setTag(itemCache);
        }
        ItemCache itemCache = (ItemCache) convertView.getTag();
        ProductBean productBean = list.get(position);
        ImageLoaderUtils.display(context, itemCache.ivImage, productBean.getImage(), R.drawable.product_default_icon, R.drawable.product_default_icon);
        itemCache.tvName.setText(productBean.getName());
        itemCache.tvNumber.setText("已售" + productBean.getSales_volume() + "件");
        itemCache.tvCurrentPrice.setText("¥" + productBean.getPrice());
        itemCache.tvOriginalPrice.setText("¥" + productBean.getOld_price());
        itemCache.tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        itemCache.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(LocalCache.get(context).getAsString("token"))) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    return;
                }
                homePagePresenter.addShoppingCart(list.get(position).getId());
            }
        });
        if (productBean.getStock_volume() > 0)
            itemCache.ivAdd.setClickable(true);
        else
            itemCache.ivAdd.setClickable(false);
        oldPosition = position;
        return convertView;
    }


    private class ItemCache {
        private TextView tvName;//名称
        private ImageView ivImage;// 图片
        private TextView tvNumber;//产品数量
        private TextView tvCurrentPrice;//产品现价
        private TextView tvOriginalPrice;//产品原价
        private ImageView ivAdd;//添加到购物车
    }

}

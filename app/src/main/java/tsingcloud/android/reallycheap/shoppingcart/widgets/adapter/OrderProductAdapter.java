package tsingcloud.android.reallycheap.shoppingcart.widgets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;

/**
 * Created by admin on 2016/3/21.
 * 订单产品适配器
 */
public class OrderProductAdapter extends BaseAdapter<ProductBean> {

    public OrderProductAdapter(Context context, List<ProductBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_order_product, null);
            ItemCache itemCache = new ItemCache();
            itemCache.tvName = (TextView) convertView.findViewById(R.id.productName);
            itemCache.ivImage = (ImageView) convertView.findViewById(R.id.productImage);
            itemCache.tvSpec = (TextView) convertView.findViewById(R.id.productSpecification);
            itemCache.tvNumber = (TextView) convertView.findViewById(R.id.productNum);
            itemCache.tvCurrentPrice = (TextView) convertView.findViewById(R.id.productCurrentPrice);
            convertView.setTag(itemCache);
        }
        ItemCache itemCache = (ItemCache) convertView.getTag();
        ProductBean productBean = list.get(position);
        ImageLoaderUtils.display(context, itemCache.ivImage, productBean.getImage(), R.drawable.product_default_icon, R.drawable.product_default_icon);
        itemCache.tvName.setText(productBean.getName());
        itemCache.tvSpec.setText(productBean.getSpec());
        itemCache.tvCurrentPrice.setText("¥" + productBean.getPrice());
        itemCache.tvNumber.setText("x" + productBean.getNumber());
        return convertView;
    }


    private class ItemCache {
        private TextView tvName;//名称
        private ImageView ivImage;// 图片
        private TextView tvSpec;//产品规格
        private TextView tvCurrentPrice;//产品现价
        private TextView tvNumber;//产品原价
    }

}

package tsingcloud.android.reallycheap.classify.widgets.adapter;

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
import tsingcloud.android.reallycheap.classify.presenter.ProductDetailsPresenter;
import tsingcloud.android.reallycheap.homepage.presenter.SearchResultsPresenter;
import tsingcloud.android.reallycheap.my.widgets.activity.LoginActivity;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;

/**
 * Created by admin on 2016/3/21.
 * 产品适配器
 */
public class ProductAdapter extends BaseAdapter<ProductBean> implements View.OnClickListener {


    private ProductDetailsPresenter productDetailsPresenter;
    private SearchResultsPresenter searchResultsPresenter;
    private ProductBean productBean;
    private PresenterEnum presenterEnum;

    public ProductAdapter(Context context, List<ProductBean> list, SearchResultsPresenter searchResultsPresenter, PresenterEnum presenterEnum) {
        super(context, list);
        this.searchResultsPresenter = searchResultsPresenter;
        this.presenterEnum = presenterEnum;
    }

    public ProductAdapter(Context context, List<ProductBean> list, ProductDetailsPresenter productDetailsPresenter, PresenterEnum presenterEnum) {
        super(context, list);
        this.productDetailsPresenter = productDetailsPresenter;
        this.presenterEnum = presenterEnum;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_product, null);
            ItemCache itemCache = new ItemCache();
            itemCache.tvName = (TextView) convertView.findViewById(R.id.productName);
            itemCache.ivImage = (ImageView) convertView.findViewById(R.id.productImage);
            itemCache.tvSpec = (TextView) convertView.findViewById(R.id.productSpecification);
            itemCache.tvOriginalPrice = (TextView) convertView.findViewById(R.id.productOriginalPrice);
            itemCache.tvCurrentPrice = (TextView) convertView.findViewById(R.id.productCurrentPrice);
            itemCache.ivAdd = (ImageView) convertView.findViewById(R.id.add);
            convertView.setTag(itemCache);
        }
        ItemCache itemCache = (ItemCache) convertView.getTag();
        productBean = list.get(position);
        ImageLoaderUtils.display(context, itemCache.ivImage, productBean.getImage(), R.drawable.product_default_icon, R.drawable.product_default_icon);
        itemCache.tvName.setText(productBean.getName());
        itemCache.tvSpec.setText(productBean.getSpec());
        itemCache.tvCurrentPrice.setText("¥" + productBean.getPrice());
        itemCache.tvOriginalPrice.setText("¥" + productBean.getOld_price());
        itemCache.tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        itemCache.ivAdd.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(LocalCache.get(context).getAsString("token"))) {
            context.startActivity(new Intent(context, LoginActivity.class));
            return;
        }
        switch (presenterEnum) {
            case SEARCH:
                if (searchResultsPresenter != null)
                    searchResultsPresenter.addShoppingCart(productBean.getId());
                break;
            case PRODUCT_DETAILS:
                if (productDetailsPresenter != null)
                    productDetailsPresenter.addShoppingCart(productBean.getId());
                break;
        }
    }

    private class ItemCache {
        private TextView tvName;//名称
        private ImageView ivImage;// 图片
        private TextView tvSpec;//产品规格
        private TextView tvCurrentPrice;//产品现价
        private TextView tvOriginalPrice;//产品原价
        private ImageView ivAdd;//添加到购物车
    }

    public enum PresenterEnum {
         SEARCH, PRODUCT_DETAILS,
    }
}

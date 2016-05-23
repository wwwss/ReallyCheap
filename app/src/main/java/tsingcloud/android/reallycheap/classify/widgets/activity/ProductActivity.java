package tsingcloud.android.reallycheap.classify.widgets.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.widgets.activity.BaseListActivity;
import tsingcloud.android.core.widgets.pull.BaseViewHolder;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.model.bean.SmallClassifyBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.classify.presenter.ProductPresenter;
import tsingcloud.android.reallycheap.classify.view.ProductView;
import tsingcloud.android.reallycheap.my.widgets.activity.LoginActivity;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;

/**
 * Created by admin on 2016/3/24.
 * 产品页面
 */
public class ProductActivity extends BaseListActivity<ProductBean> implements ProductView {

    private ProductPresenter productPresenter;
    private SmallClassifyBean smallClassifyBean;
    private ProductBean productBean;
    private int type;
    private int pageNum = 1;
    private int totalPages;

    @Override
    protected void setUpData() {
        super.setUpData();
        type = getIntent().getIntExtra("type", -1);
        productPresenter = new ProductPresenter(this);
        if (type == 0) {
            smallClassifyBean = (SmallClassifyBean) getIntent().getSerializableExtra("smallClassifyBean");
            setUpTitle(smallClassifyBean.getName());
            productPresenter.getProductsData(Api.GET_SMALL_CLASSIFY_PRODUCT + smallClassifyBean.getId(), pageNum);
        } else if (type == 1) {
            productBean = (ProductBean) getIntent().getSerializableExtra("productBean");
            setUpTitle(productBean.getName());
            productPresenter.getProductsData(Api.GET_DETAIL_CATEGORY_PRODUCT + productBean.getId(), pageNum);
        }
        mDataList = new ArrayList<>();
        recycler.enablePullToRefresh(false);
        recycler.enableLoadMore(true);
        recycler.setRefreshing();
    }

    @Override
    public void onRefresh(int action) {
        pageNum++;
        if (pageNum <= totalPages) {
            if (type == 0)
                productPresenter.getProductsData(Api.GET_SMALL_CLASSIFY_PRODUCT + smallClassifyBean.getId(), pageNum);
            else if (type == 1)
                productPresenter.getProductsData(Api.GET_DETAIL_CATEGORY_PRODUCT + productBean.getId(), pageNum);
        } else {
            recycler.onRefreshCompleted();
            recycler.enableLoadMore(false);
        }

    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_product, parent, false);
        return new ProductViewHolder(view, this, mDataList);
    }

    @Override
    public void setProductsData(List<ProductBean> products) {
        mDataList.addAll(products);
        adapter.notifyDataSetChanged();
        recycler.onRefreshCompleted();
    }

    @Override
    public void toProductDetailsActivity(int position) {
        Intent intent = new Intent();
        ProductBean productBean = mDataList.get(position);
        intent.putExtra("productBean", productBean);
        startActivity(intent);
    }

    @Override
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    private class ProductViewHolder extends BaseViewHolder {
        private TextView tvName;//名称
        private ImageView ivImage;// 图片
        private TextView tvSpec;//产品规格
        private TextView tvCurrentPrice;//产品现价
        private TextView tvOriginalPrice;//产品原价
        private ImageView ivAdd;//添加到购物车

        public ProductViewHolder(View itemView, Context context, List<ProductBean> list) {
            super(itemView);
            this.context = context;
            this.list = list;
            tvName = (TextView) itemView.findViewById(R.id.productName);
            ivImage = (ImageView) itemView.findViewById(R.id.productImage);
            tvSpec = (TextView) itemView.findViewById(R.id.productSpecification);
            tvOriginalPrice = (TextView) itemView.findViewById(R.id.productOriginalPrice);
            tvCurrentPrice = (TextView) itemView.findViewById(R.id.productCurrentPrice);
            ivAdd = (ImageView) itemView.findViewById(R.id.add);
        }

        @Override
        public void onBindViewHolder(int position) {
            final ProductBean productBean = mDataList.get(position);
            ImageLoaderUtils.display(context, ivImage, productBean.getImage(), R.drawable.product_default_icon, R.drawable.product_default_icon);
            tvName.setText(productBean.getName());
            tvSpec.setText(productBean.getSpec());
            tvCurrentPrice.setText("¥" + productBean.getPrice());
            tvOriginalPrice.setText("¥" + productBean.getOld_price());
            tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(getToken()))
                        startActivity(new Intent(context, LoginActivity.class));
                    else
                        productPresenter.addShoppingCart(productBean.getId());
                }
            });
        }

        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("id", mDataList.get(position).getId());
            startActivity(intent);

        }


    }
}

package tsingcloud.android.reallycheap.classify.widgets.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.classify.presenter.ProductDetailsPresenter;
import tsingcloud.android.reallycheap.classify.view.ProductDetailsView;
import tsingcloud.android.reallycheap.classify.widgets.adapter.ProductAdapter;
import tsingcloud.android.reallycheap.my.widgets.activity.LoginActivity;
import tsingcloud.android.reallycheap.widgets.activity.MainActivity;
import tsingcloud.android.reallycheap.widgets.view.SlideShowView;

/**
 * Created by admin on 2016/4/29.
 * 商品详情页面
 */
public class ProductDetailsActivity extends BaseActivity implements ProductDetailsView, View.OnClickListener {

    private ImageView ivCollection;
    private SlideShowView slideShowView;
    private TextView tvName;//名称
    private TextView tvSpec;//产品规格
    private TextView tvPrice;//产品现价
    private ListView listView;
    private ProductAdapter adapter;
    private ProductDetailsPresenter productDetailsPresenter;
    private String productId;
    private ProductBean productBean;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_product_details);
    }

    @Override
    protected void setUpView() {
        productDetailsPresenter = new ProductDetailsPresenter(this);
        slideShowView = (SlideShowView) findViewById(R.id.slideshowView);
        ivCollection = (ImageView) findViewById(R.id.collection);
        tvName = (TextView) findViewById(R.id.productName);
        tvSpec = (TextView) findViewById(R.id.productSpecification);
        tvPrice = (TextView) findViewById(R.id.productPrice);
        listView = (ListView) findViewById(R.id.listView);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.goShoppingCart).setOnClickListener(this);
        ivCollection.setOnClickListener(this);
        findViewById(R.id.addShoppingCart).setOnClickListener(this);
    }

    @Override
    protected void setUpData() {
        productId = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(productId))
            return;
        productDetailsPresenter.getProductDetailsData(productId);
    }

    @Override
    public void setProductDetailsData(final ProductBean productBean) {
        if (productBean == null)
            return;
        this.productBean = productBean;
        if (productBean.getIs_favorite() == 0)
            ivCollection.setSelected(true);
        else
            ivCollection.setSelected(false);
        tvName.setText(productBean.getName());
        tvSpec.setText(productBean.getSpec());
        tvPrice.setText("¥" + productBean.getPrice());
        if (null != productBean.getImagelist() && productBean.getImagelist().size() > 0)
            slideShowView.setData(productBean.getImagelist());
        if (null != productBean.getHotlists() && productBean.getHotlists().size() > 0) {
            adapter = new ProductAdapter(this, productBean.getHotlists(), productDetailsPresenter, ProductAdapter.PresenterEnum.PRODUCT_DETAILS);
            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent=new Intent(ProductDetailsActivity.this,ProductDetailsActivity.class);
//                    intent.putExtra("id",productBean.getHotlists().get(position).getId());
//                    startActivity(intent);
//                }
//            });
        }


    }

    @Override
    public void collectionSuccess(String favoriteId) {
        productBean.setFavorite_id(favoriteId);
        ivCollection.setSelected(true);
    }

    @Override
    public void cancelCollectionSuccess() {
        ivCollection.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.goShoppingCart:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type", 2);
                startActivity(intent);
                finish();
                break;
            case R.id.collection:
                if (TextUtils.isEmpty(getToken()))
                    startActivity(new Intent(this, LoginActivity.class));
                else {
                    if (productBean == null)
                        return;
                    if (ivCollection.isSelected())
                        productDetailsPresenter.cancelCollectionProduct(productBean.getFavorite_id());
                    else
                        productDetailsPresenter.collectionProduct(productId);
                }
                break;
            case R.id.addShoppingCart:
                if (TextUtils.isEmpty(getToken()))
                    startActivity(new Intent(this, LoginActivity.class));
                else
                    productDetailsPresenter.addShoppingCart(productId);
                break;
        }

    }
}

package tsingcloud.android.reallycheap.homepage.widgets.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.List;

import tsingcloud.android.model.bean.HotClassifyBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.classify.widgets.activity.ProductDetailsActivity;
import tsingcloud.android.reallycheap.homepage.presenter.HomepagePresenter;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;
import tsingcloud.android.reallycheap.widgets.view.SAGridView;

/**
 * Created by admin on 2016/3/21.
 * 产品适配器
 */
public class HotProductListViewAdapter extends BaseAdapter<HotClassifyBean> {

    private HotProductGridViewAdapter adapter;
    private HomepagePresenter homePagePresenter;

    public HotProductListViewAdapter(Context context, List<HotClassifyBean> list, HomepagePresenter homePagePresenter) {
        super(context, list);
        this.homePagePresenter = homePagePresenter;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_listview_hot_product, null);
            ItemCache itemCache = new ItemCache();
            itemCache.ivImage = (ImageView) convertView.findViewById(R.id.classifyImage);
            itemCache.saGridView = (SAGridView) convertView.findViewById(R.id.productGridView);
            convertView.setTag(itemCache);
        }
        ItemCache itemCache = (ItemCache) convertView.getTag();
        final
        HotClassifyBean hotClassifyBean = list.get(position);
        ImageLoaderUtils.display(context, itemCache.ivImage, hotClassifyBean.getImage(), R.drawable.product_default_icon, R.drawable.product_default_icon);
        adapter = new HotProductGridViewAdapter(context, hotClassifyBean.getList(), homePagePresenter);
        itemCache.saGridView.setAdapter(adapter);
        itemCache.saGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id", hotClassifyBean.getList().get(position).getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    private class ItemCache {
        private ImageView ivImage;// 图片
        private SAGridView saGridView;
    }


}

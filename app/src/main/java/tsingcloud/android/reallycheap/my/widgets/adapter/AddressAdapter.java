package tsingcloud.android.reallycheap.my.widgets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.view.AddManageView;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;

/**
 * Created by admin on 2016/4/17.
 * 地址适配器
 */
public class AddressAdapter extends BaseAdapter<AddressBean> {
    private AddManageView addManageView;

    public AddressAdapter(Context context, List<AddressBean> list, AddManageView addManageView) {
        super(context, list);
        this.addManageView = addManageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_address, null);
            ItemCache itemCache = new ItemCache();
            itemCache.tvName = (TextView) convertView.findViewById(R.id.name);
            itemCache.tvPhoneNumber = (TextView) convertView.findViewById(R.id.phoneNumber);
            itemCache.tvAddress = (TextView) convertView.findViewById(R.id.address);
            itemCache.ivIsDefault = (ImageView) convertView.findViewById(R.id.defaultAddress);
            itemCache.ivEdit = (ImageView) convertView.findViewById(R.id.editAddress);
            convertView.setTag(itemCache);
        }
        ItemCache itemCache = (ItemCache) convertView.getTag();
        AddressBean addressBean = list.get(position);
        itemCache.tvName.setText("收货人：" + addressBean.getReceive_name());
        itemCache.tvPhoneNumber.setText("联系电话：" + addressBean.getReceive_phone());
        itemCache.tvAddress.setText("收货地址：" + addressBean.getArea());
        if (addressBean.getIs_default() == 0)
            itemCache.ivIsDefault.setVisibility(View.INVISIBLE);
        else
            itemCache.ivIsDefault.setVisibility(View.VISIBLE);
        itemCache.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addManageView.toAddressActivity(1, position);
            }
        });
        return convertView;
    }

    private class ItemCache {
        public TextView tvName;// 收货人姓名
        public TextView tvPhoneNumber;//电话
        public TextView tvAddress;//地址
        public ImageView ivIsDefault;//是否默认
        public ImageView ivEdit;//编辑
    }
}

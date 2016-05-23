package tsingcloud.android.reallycheap.shoppingcart.widgets.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.my.presenter.AddManagePresenter;
import tsingcloud.android.reallycheap.my.view.AddManageView;
import tsingcloud.android.reallycheap.my.widgets.activity.AddressManagementActivity;
import tsingcloud.android.reallycheap.my.widgets.adapter.AddressAdapter;

/**
 * Created by admin on 2016/4/28.
 * 选择地址页面
 */
public class SelectAddressActivity extends BaseActivity implements AddManageView, AdapterView.OnItemClickListener, View.OnClickListener {
    private static final int ADDRESS_MANAGEMENT = 0;
    private ListView listView;
    private List<AddressBean> addressBeanList;
    private AddressAdapter adapter;
    private final int ADDRESS = 0;
    private AddManagePresenter addManagePresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_select_address, R.string.select_address);
    }

    @Override
    protected void setUpView() {
        addManagePresenter = new AddManagePresenter(this);
        listView = (ListView) findViewById(R.id.listView);
        addressBeanList = new ArrayList<>();
        adapter = new AddressAdapter(this, addressBeanList, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(findViewById(R.id.empty));
        findViewById(R.id.addressManagement).setOnClickListener(this);
    }

    @Override
    protected void setUpData() {
        addManagePresenter.getAddressList();
    }

    @Override
    public void setAddressList(List<AddressBean> addressBeans) {
        if (addressBeans == null)
            return;
        addressBeanList.clear();
        addressBeanList.addAll(addressBeans);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void toAddressActivity(int type, int position) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        AddressBean addressBean = (AddressBean) parent.getItemAtPosition(position);
        intent.putExtra("addressBean", addressBean);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addressManagement:
                startActivityForResult(new Intent(this, AddressManagementActivity.class), ADDRESS_MANAGEMENT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case ADDRESS_MANAGEMENT:
                addManagePresenter.getAddressList();
                break;
        }
    }
}

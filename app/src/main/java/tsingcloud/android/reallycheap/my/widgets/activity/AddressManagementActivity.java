package tsingcloud.android.reallycheap.my.widgets.activity;

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
import tsingcloud.android.reallycheap.my.widgets.adapter.AddressAdapter;

/**
 * Created by admin on 2016/4/17.
 * 地址管理页面
 */
public class AddressManagementActivity extends BaseActivity implements View.OnClickListener, AddManageView, AdapterView.OnItemClickListener {

    private ListView listView;
    private List<AddressBean> addressBeanList;
    private AddressAdapter adapter;
    private final int ADDRESS = 0;
    private AddManagePresenter addManagePresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_address_management, R.string.receiving_address_management);
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
        findViewById(R.id.add).setOnClickListener(this);
    }

    @Override
    protected void setUpData() {
        addManagePresenter.getAddressList();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                toAddressActivity(0, -1);
                break;
        }
    }

    @Override
    public void setAddressList(List<AddressBean> addressBeans) {
        if (null == addressBeans)
            return;
        addressBeanList.clear();
        addressBeanList.addAll(addressBeans);
        adapter.notifyDataSetChanged();
    }


    /**
     * @param type 0是添加，1是修改
     */
    @Override
    public void toAddressActivity(int type, int position) {
        Intent intent = new Intent(this, AddressActivity.class);
        intent.putExtra("type", type);
        if (type == 1)
            intent.putExtra("addressBean", addressBeanList.get(position));
        startActivityForResult(intent, ADDRESS);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        toAddressActivity(1, position);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case ADDRESS:
                addManagePresenter.getAddressList();
                break;
        }
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }
}

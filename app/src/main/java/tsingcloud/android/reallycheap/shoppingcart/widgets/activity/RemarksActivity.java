package tsingcloud.android.reallycheap.shoppingcart.widgets.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.reallycheap.R;

/**
 * Created by admin on 2016/5/31.
 * 备注页面
 */
public class RemarksActivity extends BaseActivity {

    private EditText etRemarks;
    private String remarks;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_remarks, R.string.remarks);
    }

    @Override
    protected void setUpView() {
        titleBar.setRightText(R.string.confirm);
        etRemarks = (EditText) findViewById(R.id.remarks);
    }

    @Override
    protected void setUpData() {
        remarks = getIntent().getStringExtra("remarks");
        if (!TextUtils.isEmpty(remarks))
            etRemarks.setText(remarks);
    }

    @Override
    public void clickRight() {
        remarks = etRemarks.getText().toString();
        if (!TextUtils.isEmpty(remarks)) {
            Intent intent = new Intent();
            intent.putExtra("remarks", remarks);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}

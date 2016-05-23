package tsingcloud.android.reallycheap.shoppingcart.widgets.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.model.bean.ShoppingCartBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.shoppingcart.presenter.ShoppingCartPresenter;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;

/**
 * Created by admin on 2016/3/28.
 * 购物车适配器
 */
public class ShoppingCartAdapter extends BaseAdapter<ShoppingCartBean> {
    private ShoppingCartPresenter shoppingCartPresenter;
    private int oldPosition;

    public ShoppingCartAdapter(Context context, List<ShoppingCartBean> list, ShoppingCartPresenter shoppingCartPresenter) {
        super(context, list);
        this.shoppingCartPresenter = shoppingCartPresenter;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_shopping_cart, null);
            ItemCache itemCache = new ItemCache();
            itemCache.tvCategoryName = (TextView) convertView.findViewById(R.id.categoryName);
            itemCache.tvName = (TextView) convertView.findViewById(R.id.productName);
            itemCache.ivImage = (ImageView) convertView.findViewById(R.id.productImage);
            itemCache.tvSpec = (TextView) convertView.findViewById(R.id.productSpecification);
            itemCache.tvCurrentPrice = (TextView) convertView.findViewById(R.id.productCurrentPrice);
            itemCache.ivIsSelect = (ImageView) convertView.findViewById(R.id.isSelect);
            itemCache.ivSubtract = (ImageView) convertView.findViewById(R.id.subtract);
            itemCache.ivPlus = (ImageView) convertView.findViewById(R.id.plus);
            itemCache.llEditProductNum = (LinearLayout) convertView.findViewById(R.id.editProductNum);
            itemCache.tvProductNumber = (TextView) convertView.findViewById(R.id.productNum);
            convertView.setTag(itemCache);
        }
        ItemCache itemCache = (ItemCache) convertView.getTag();
        final ShoppingCartBean shoppingCartBean = list.get(position);
        ShoppingCartBean oldShoppingCartBean = new ShoppingCartBean();
        if (oldPosition <= list.size() - 1) {
            oldShoppingCartBean = list.get(oldPosition);
        }
        final ProductBean productBean = shoppingCartBean.getProductBean();
        if (position == 0) {
            itemCache.tvCategoryName.setVisibility(View.VISIBLE);
        } else {
            if (shoppingCartBean.getCategory_name().equals(oldShoppingCartBean.getCategory_name())) {
                itemCache.tvCategoryName.setVisibility(View.GONE);
            } else {
                itemCache.tvCategoryName.setVisibility(View.VISIBLE);
            }
        }
        if (shoppingCartBean.isSelected()) {
            itemCache.ivIsSelect.setSelected(true);
        } else {
            itemCache.ivIsSelect.setSelected(false);
        }
        itemCache.tvCategoryName.setText(shoppingCartBean.getCategory_name());
        itemCache.tvName.setText(productBean.getName());
        ImageLoaderUtils.display(context, itemCache.ivImage, productBean.getImage(), R.drawable.product_default_icon, R.drawable.product_default_icon);
        itemCache.tvSpec.setText(productBean.getSpec());
        itemCache.tvCurrentPrice.setText("¥" + productBean.getPrice());
        itemCache.tvProductNumber.setText(productBean.getNumber() + "");
        itemCache.ivSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = productBean.getNumber();
                if (number > 1) {
                    number--;
                    shoppingCartPresenter.updateShopCartItemNumber(shoppingCartBean, number, position);
                }
            }
        });
        itemCache.ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = productBean.getNumber();
                if (number < productBean.getStock_volume()) {
                    number++;
                    shoppingCartPresenter.updateShopCartItemNumber(shoppingCartBean, number, position);
                }

            }
        });
        itemCache.llEditProductNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCartItemNumberShow(position, productBean.getNumber(), productBean.getStock_volume());
            }
        });
        itemCache.ivIsSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shoppingCartBean.isSelected()) {
                    shoppingCartBean.setSelected(false);
                } else {
                    shoppingCartBean.setSelected(true);
                }
                notifyDataSetChanged();
            }
        });
        oldPosition = position;
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        double total = 0;
        for (int i = 0; i < list.size(); i++) {
            ShoppingCartBean shoppingCartBean = list.get(i);
            final ProductBean productBean = shoppingCartBean.getProductBean();
            if (shoppingCartBean.isSelected()) {
                if (TextUtils.isEmpty(productBean.getPrice()))
                    continue;
                total += productBean.getNumber() * Double.parseDouble(productBean.getPrice());
            }
        }
        if (total > 0)
            shoppingCartPresenter.updateTotalPrice(total);
        super.notifyDataSetChanged();
    }

    private class ItemCache {
        private TextView tvCategoryName;
        private TextView tvName;//名称
        private ImageView ivImage;// 图片
        private TextView tvSpec;//产品规格
        private TextView tvCurrentPrice;//产品现价
        private ImageView ivIsSelect;//是否选中
        private ImageView ivSubtract;//减
        private ImageView ivPlus;//加
        private LinearLayout llEditProductNum;//编辑产品数量
        private TextView tvProductNumber;//产品数量
    }


    /**
     * 修改数量
     */
    private void updateCartItemNumberShow(final int position, int number, final int stock_num) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        final Window window = alertDialog.getWindow();
        window.clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        window.setContentView(R.layout.dialog_update_item_num);
        TextView tv_jian = (TextView) window.findViewById(R.id.jian);
        TextView tv_jia = (TextView) window.findViewById(R.id.jia);
        final EditText et_number = (EditText) window.findViewById(R.id.number);
        et_number.setText(number + "");
        tv_jian.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_number.getText().toString()))
                    return;
                int number = Integer.valueOf(et_number.getText().toString());
                if (number > 1) {
                    number--;
                    et_number.setText(number + "");
                }

            }
        });
        tv_jia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_number.getText().toString()))
                    return;
                int number = Integer.valueOf(et_number.getText().toString());
                if (number < stock_num) {
                    number++;
                    et_number.setText(number + "");
                }

            }
        });
        window.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        window.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_number.getText().toString()))
                    return;
                int number = Integer.valueOf(et_number.getText().toString());
                if (number > 0 && number <= stock_num) {
                    shoppingCartPresenter.updateShopCartItemNumber(list.get(position), number, position);
                    alertDialog.dismiss();
                }
            }
        });
        et_number.setFocusable(true);
        et_number.setFocusableInTouchMode(true);
        et_number.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() { // 让软键盘延时弹出，以更好的加载Activity

            public void run() {
                InputMethodManager inputManager = (InputMethodManager) et_number.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(et_number, 0);
            }

        }, 300);

    }
}

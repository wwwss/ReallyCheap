package tsingcloud.android.reallycheap.shoppingcart.model;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.callback.ResultCallback;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.core.utils.LogUtils;
import tsingcloud.android.model.bean.AddressBean;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.model.bean.ShoppingCartBean;

/**
 * Created by admin on 2016/3/28.
 * 购物车数据
 */
public class ShoppingCartModelImpl implements ShoppingCartModel {
    private final String TAG = getClass().getName();

    @Override
    public void getShippingAddress(String token, final OnNSURLRequestListener<AddressBean> listener, String tag) {
        OkHttpUtils.get(Api.GET_DEFAULT_ADDRESSES + token, new ResultCallback<ApiResponseBean<AddressBean>>() {
            @Override
            public void onSuccess(ApiResponseBean<AddressBean> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getObj());
                else if (response.isTokenFailure())
                    listener.onTokenFailure();
                else
                    listener.onSuccess(null);
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.e(TAG, "获取默认地址失败", e);
                //listener.onFailure("获取默认地址失败");
            }
        }, tag);
    }

    @Override
    public void getShoppingCartList(String token, String shopId, final OnNSURLRequestListener<List<ShoppingCartBean>> listener, String tag) {
        OkHttpUtils.get(Api.GET_CARTS + token + "?shop_id=" + shopId, new ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                if (TextUtils.isEmpty(response))
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errCode = jsonObject.optInt("errcode", -1);
                    String errMsg = jsonObject.optString("errmsg");
                    if (errCode == 0) {
                        List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
                        JSONArray jsonArray = jsonObject.optJSONArray("objlist");
                        if (jsonArray == null) {
                            return;
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                            if (jsonObject2 == null) {
                                continue;
                            }
                            String categoryName = jsonObject2.optString("category_name", "");
                            JSONArray jsonArray2 = jsonObject2.optJSONArray("list");
                            if (jsonArray2 == null) {
                                continue;
                            }
                            for (int j = 0; j < jsonArray2.length(); j++) {
                                JSONObject jsonObject3 = jsonArray2.optJSONObject(j);
                                if (jsonObject3 == null) {
                                    continue;
                                }
                                ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
                                shoppingCartBean.setCategory_name(categoryName);
                                shoppingCartBean.setCart_id(jsonObject3.optString("cart_id", ""));
                                ProductBean productBean = new ProductBean();
                                productBean.setId(jsonObject3.optString("product_id", ""));
                                int product_num = jsonObject3.optInt("product_num", 1);
                                productBean.setNumber(product_num);
                                productBean.setName(jsonObject3.optString("product_name", ""));
                                productBean.setImage(jsonObject3.optString("product_image", ""));
                                productBean.setPrice(jsonObject3.optString("product_price", ""));
                                productBean.setSpec(jsonObject3.optString("product_spec", ""));
                                int stock_num = jsonObject3.optInt("product_stock_volume", 0);
                                productBean.setStock_volume(stock_num);
                                if (product_num > stock_num) {
                                    shoppingCartBean.setSelected(false);
                                } else {
                                    shoppingCartBean.setSelected(true);
                                }
                                shoppingCartBean.setProductBean(productBean);
                                shoppingCartBeanList.add(shoppingCartBean);
                            }
                        }
                        if (shoppingCartBeanList.size() > 0)
                            listener.onSuccess(shoppingCartBeanList);
                        else
                            listener.onSuccess(null);
                    } else if (errCode == 2)
                        listener.onTokenFailure();
                    else
                        listener.onFailure(errMsg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onFailure(response);
                }
            }

            @Override
            public void onFailure(Exception e) {
                //listener.onFailure("获取购物车列表失败");
            }
        }, tag);
    }

    @Override
    public void delete(Map<String, String> map, final OnNSURLRequestListener<String> listener, String tag) {
        OkHttpUtils.delete(Api.DELETE_CART_ITEMS, new ResultCallback<ApiResponseBean<String>>() {
            @Override
            public void onSuccess(ApiResponseBean<String> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getErrmsg());
                else if (response.isTokenFailure())
                    listener.onTokenFailure();
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("删除购物车数据失败");
            }
        }, map, tag);
    }

    @Override
    public void updateShopCartItemNumber(Map<String, String> map, final OnNSURLRequestListener<String> listener, String tag) {
        OkHttpUtils.post(Api.EDIT_CARTS_PRODUCT_NUM, new ResultCallback<ApiResponseBean<String>>() {
            @Override
            public void onSuccess(ApiResponseBean<String> response) {
                if (response.isSuccess())
                    listener.onSuccess(response.getErrmsg());
                else if (response.isTokenFailure())
                    listener.onTokenFailure();
                else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("修改购物车产品数量失败");
            }
        }, map, tag);
    }
}

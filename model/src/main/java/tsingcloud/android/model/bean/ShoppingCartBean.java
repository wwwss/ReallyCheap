package tsingcloud.android.model.bean;

/**
 * Created by admin on 2016/3/28.
 * 购物车
 */
public class ShoppingCartBean extends BaseBean {

    /**
     * 大分类名称
     */
    private String category_name;

    /**
     * 购物车id
     */
    private String cart_id;

    private  boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ProductBean getProductBean() {
        return productBean;
    }

    public void setProductBean(ProductBean productBean) {
        this.productBean = productBean;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    private ProductBean productBean;


    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}

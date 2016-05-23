package tsingcloud.android.model.bean;

/**
 * Created by admin on 2016/4/29.
 * 收藏宝贝实体类
 */
public class CollectBabyBean extends BaseBean {

    private String id;
    private String product_id;
    private String product_name;
    private String product_image;
    private String product_unit;
    private String product_price;
    private String product_old_price;
    private String product_stock_volume;
    private String product_sales_volume;
    private String product_spec;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_old_price() {
        return product_old_price;
    }

    public void setProduct_old_price(String product_old_price) {
        this.product_old_price = product_old_price;
    }

    public String getProduct_stock_volume() {
        return product_stock_volume;
    }

    public void setProduct_stock_volume(String product_stock_volume) {
        this.product_stock_volume = product_stock_volume;
    }

    public String getProduct_sales_volume() {
        return product_sales_volume;
    }

    public void setProduct_sales_volume(String product_sales_volume) {
        this.product_sales_volume = product_sales_volume;
    }

    public String getProduct_spec() {
        return product_spec;
    }

    public void setProduct_spec(String product_spec) {
        this.product_spec = product_spec;
    }
}

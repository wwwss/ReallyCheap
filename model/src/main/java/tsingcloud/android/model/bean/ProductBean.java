package tsingcloud.android.model.bean;

import java.util.List;

/**
 * Created by admin on 2016/3/21.
 */
public class ProductBean extends BaseBean {
    private static final long serialVersionUID = 1L;

    /**
     * 产品ID
     */
    private String id;

    /**
     * 产品图片地址
     */
    private String image;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品规格
     */
    private String spec;

    /**
     * 产品原价
     */
    private String old_price;

    /**
     * 产品现价
     */
    private String price;

    private int stock_volume;

    private int sales_volume;

    private String unit;

    private  int number;

    private int is_favorite;

    private String favorite_id;

    private List<BannerBean> imagelist;

    private List<ProductBean> hotlists;

    public String getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public String getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(String favorite_id) {
        this.favorite_id = favorite_id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getOld_price() {
        return old_price;
    }

    public void setOld_price(String old_price) {
        this.old_price = old_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStock_volume() {
        return stock_volume;
    }

    public void setStock_volume(int stock_volume) {
        this.stock_volume = stock_volume;
    }

    public int getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(int sales_volume) {
        this.sales_volume = sales_volume;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<BannerBean> getImagelist() {
        return imagelist;
    }

    public void setImagelist(List<BannerBean> imagelist) {
        this.imagelist = imagelist;
    }

    public List<ProductBean> getHotlists() {
        return hotlists;
    }

    public void setHotlists(List<ProductBean> hotlists) {
        this.hotlists = hotlists;
    }
    /**--------------------------购物车产品---------------------------------------------*/
//    /**
//     * 购物车ID
//     */
//    private String cart_id;
//    private String product_id;
//    private String product_name;
//    private String product_image;
//    private String product_unit;
//    private String product_price;
//    private String product_old_price;
//    private int product_stock_volume;
//    private int product_sales_volume;
//    private String product_spec;
//
//    public String getProduct_spec() {
//        return product_spec;
//    }
//
//    public void setProduct_spec(String product_spec) {
//        this.product_spec = product_spec;
//    }
//
//    public int getProduct_sales_volume() {
//        return product_sales_volume;
//    }
//
//    public void setProduct_sales_volume(int product_sales_volume) {
//        this.product_sales_volume = product_sales_volume;
//    }
//
//    public int getProduct_stock_volume() {
//        return product_stock_volume;
//    }
//
//    public void setProduct_stock_volume(int product_stock_volume) {
//        this.product_stock_volume = product_stock_volume;
//    }
//
//    public String getProduct_price() {
//        return product_price;
//    }
//
//    public void setProduct_price(String product_price) {
//        this.product_price = product_price;
//    }
//
//    public String getProduct_old_price() {
//        return product_old_price;
//    }
//
//    public void setProduct_old_price(String product_old_price) {
//        this.product_old_price = product_old_price;
//    }
//
//    public String getProduct_unit() {
//        return product_unit;
//    }
//
//    public void setProduct_unit(String product_unit) {
//        this.product_unit = product_unit;
//    }
//
//    public String getProduct_image() {
//        return product_image;
//    }
//
//    public void setProduct_image(String product_image) {
//        this.product_image = product_image;
//    }
//
//    public String getProduct_name() {
//        return product_name;
//    }
//
//    public void setProduct_name(String product_name) {
//        this.product_name = product_name;
//    }
//
//    public String getProduct_id() {
//        return product_id;
//    }
//
//    public void setProduct_id(String product_id) {
//        this.product_id = product_id;
//    }
//
//    public String getCart_id() {
//        return cart_id;
//    }
//
//    public void setCart_id(String cart_id) {
//        this.cart_id = cart_id;
//    }
}

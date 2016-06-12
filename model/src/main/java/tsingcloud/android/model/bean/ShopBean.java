package tsingcloud.android.model.bean;

/**
 * Created by admin on 2016/4/14.
 * 店铺实体对象
 */
public class ShopBean extends BaseBean{

    /**
     * 店铺ID
     */
    private String id;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺地址
     */
    private String address;

    /**
     * 经度
     */
    private double lat;

    /**
     * 纬度
     */
    private double lng;

    /**
     * 配送范围
     */
    private double distance;

    /**
     * 营业时间
     */
    private String business_hours;

    /**
     *免费起送价格
     */
    private double send_price;

    /**
     * 运费
     */
    private double freight;

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getSend_price() {
        return send_price;
    }

    public void setSend_price(double send_price) {
        this.send_price = send_price;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    private int range;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getBusiness_hours() {
        return business_hours;
    }

    public void setBusiness_hours(String business_hours) {
        this.business_hours = business_hours;
    }
}

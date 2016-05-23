package tsingcloud.android.model.bean;

/**
 * Created by admin on 2016/4/16.
 * 程序入口
 */
public class ApplicationBean extends BaseBean {


    /**
     * 版本标识
     */
    private String version;

    /**
     * 起送价格
     */
    private String send_price;

    /**
     * 客服电话
     */
    private String phone_num;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSend_price() {
        return send_price;
    }

    public void setSend_price(String send_price) {
        this.send_price = send_price;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }
}

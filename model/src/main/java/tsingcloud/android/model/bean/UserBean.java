package tsingcloud.android.model.bean;

/**
 * Created by admin on 2016/4/16.
 */
public class UserBean extends BaseBean{
    /**
     * 登录token
     */
    private  String token;

    private String id;

    private String name;

    private String image;

    private String phone;

    /**
     * 是否有新的消息
     * 0是，1否
     */
    private int is_new_msg;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIs_new_msg() {
        return is_new_msg;
    }

    public void setIs_new_msg(int is_new_msg) {
        this.is_new_msg = is_new_msg;
    }
}

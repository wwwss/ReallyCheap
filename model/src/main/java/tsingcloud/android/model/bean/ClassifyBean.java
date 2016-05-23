package tsingcloud.android.model.bean;

import java.util.List;

/**
 * Created by admin on 2016/3/21.
 */
public class ClassifyBean extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String name;
    private String id;
    private String image;

    private List<ProductBean> list;

    public String getId() {
        return id;
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

    public List<ProductBean> getList() {
        return list;
    }

    public void setList(List<ProductBean> list) {
        this.list = list;
    }
}

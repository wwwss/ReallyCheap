package tsingcloud.android.model.bean;

import java.util.List;

/**
 * Created by admin on 2016/4/18.
 * 分类页面数据实体对象
 */
public class ClassifyDataBean extends BaseBean {

    private List<ClassifyBean> categorylist;

    private List<SmallClassifyBean> objlist;


    public List<ClassifyBean> getCategorylist() {
        return categorylist;
    }

    public void setCategorylist(List<ClassifyBean> categorylist) {
        this.categorylist = categorylist;
    }

    public List<SmallClassifyBean> getObjlist() {
        return objlist;
    }

    public void setObjlist(List<SmallClassifyBean> objlist) {
        this.objlist = objlist;
    }
}

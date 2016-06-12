package tsingcloud.android.model.bean;

import android.text.TextUtils;

/**
 * Created by admin on 2016/4/5.
 * API请求响应实体类
 */
public class ApiResponseBean<T> extends BaseBean{

    private String errcode;    // 返回码，0为成功
    private String errmsg;      // 返回信息
    private T obj;           // 单个对象
    private T objlist;       // 数组对象
    private int currentPage; // 当前页数
    private int pageSize;    // 每页显示数量
    private int maxCount;    // 总条数
    private int total_pages;     // 总页数

    // 构造函数，初始化code和msg
    public ApiResponseBean(String errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        if (TextUtils.isEmpty(errmsg))
            errmsg = "服务器异常，稍后再试";
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    // 判断结果是否成功
    public boolean isSuccess() {
        return "0".equals(errcode);
    }


    // 判断结果是否成功
    public boolean isTokenFailure() {
        return "2".equals(errcode);
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public T getObjList() {
        return objlist;
    }

    public void setObjList(T objlist) {
        this.objlist = objlist;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
}

package tsingcloud.android.model.bean;

import java.util.Map;

/**
 * Created by admin on 2016/5/25.
 */
public class RequestBean extends BaseBean {
    private String url;
    private Map<String, String> map;
    private String tag;
    private RequestEnum requestEnum;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public RequestEnum getRequestEnum() {
        return requestEnum;
    }

    public void setRequestEnum(RequestEnum requestEnum) {
        this.requestEnum = requestEnum;
    }
}

package tsingcloud.android.model.bean;

import java.util.List;

/**
 * Created by admin on 2016/3/24.
 */
public class SearchBean {
    List<String> hotSearchList;
    List<String> historySearchList;

    public List<String> getHistorySearchList() {
        return historySearchList;
    }

    public void setHistorySearchList(List<String> historySearchList) {
        this.historySearchList = historySearchList;
    }

    public List<String> getHotSearchList() {
        return hotSearchList;
    }

    public void setHotSearchList(List<String> hotSearchList) {
        this.hotSearchList = hotSearchList;
    }
}

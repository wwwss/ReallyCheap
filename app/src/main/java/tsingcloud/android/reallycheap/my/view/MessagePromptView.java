package tsingcloud.android.reallycheap.my.view;

import java.util.List;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.MessagePromptBean;

/**
 * Created by admin on 2016/5/9.
 * 消息提示页面接口类
 */
public interface MessagePromptView extends BaseView{

    void setMessagePromptList(List<MessagePromptBean> messagePromptList);

    void deleteComplete(int position);

    void clearComplete();

    void setTotalPages(int totalPages);

}

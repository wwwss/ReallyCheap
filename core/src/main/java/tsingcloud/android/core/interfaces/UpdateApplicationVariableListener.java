package tsingcloud.android.core.interfaces;

import tsingcloud.android.core.view.BaseView;
import tsingcloud.android.model.bean.ApplicationBean;

/**
 * Created by admin on 2016/4/16.
 * 更新程序变量
 */
public interface UpdateApplicationVariableListener extends BaseView{
    void updateApplicationVariable(ApplicationBean applicationBean);

    void updateToken(String token);
}


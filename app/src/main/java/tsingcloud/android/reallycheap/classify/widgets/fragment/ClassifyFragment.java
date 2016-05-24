package tsingcloud.android.reallycheap.classify.widgets.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.widgets.fragment.BaseFragment;
import tsingcloud.android.model.bean.ClassifyBean;
import tsingcloud.android.model.bean.SmallClassifyBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.classify.presenter.ClassifyPresenter;
import tsingcloud.android.reallycheap.classify.view.ClassifyView;
import tsingcloud.android.reallycheap.classify.widgets.adapter.ClassifyAdapter;
import tsingcloud.android.reallycheap.classify.widgets.adapter.SmallClassifyAdapter;
import tsingcloud.android.reallycheap.homepage.widgets.activity.SearchActivity;
import tsingcloud.android.reallycheap.utils.NetUtils;

/**
 * Created by admin on 2016/3/16.
 * 产品分类
 */
public class ClassifyFragment extends BaseFragment implements AdapterView.OnItemClickListener, ClassifyView {

    private ListView classifyListView;
    private ListView smallClassifyListView;
    private ClassifyAdapter classifyAdapter;
    private List<ClassifyBean> classifyBeanList;
    private SmallClassifyAdapter smallClassifyAdapter;
    private List<SmallClassifyBean> smallClassifyBeanList;
    private ClassifyPresenter classifyPresenter;
    private int newPosition;
    private String categoryId;//分类ID
    private View reloadView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classify, container, false);
    }

    /**
     * 初始化布局文件
     */
    @Override
    protected void setUpView() {
        classifyPresenter = new ClassifyPresenter(this);
        classifyListView = (ListView) view.findViewById(R.id.classifyListView);
        smallClassifyListView = (ListView) view.findViewById(R.id.smallClassifyListView);
        classifyBeanList = new ArrayList<>();
        classifyAdapter = new ClassifyAdapter(context, classifyBeanList);
        classifyListView.setAdapter(classifyAdapter);
        classifyListView.setOnItemClickListener(this);
        smallClassifyBeanList = new ArrayList<>();
        smallClassifyAdapter = new SmallClassifyAdapter(context, smallClassifyBeanList);
        smallClassifyListView.setAdapter(smallClassifyAdapter);
        view.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpData() {
        if (!NetUtils.isConnected(context)){
            showReloadView();
            return;
        }
        classifyPresenter.getClassifyData();
    }

    public void showReloadView() {
        if (reloadView == null) {
            ViewStub noDataViewStub = (ViewStub) view.findViewById(R.id.reload);
            reloadView = noDataViewStub.inflate();
            reloadView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    classifyPresenter.getClassifyData();
                }
            });
        } else
            reloadView.setVisibility(View.VISIBLE);
    }

    public  void hideReloadView(){
        if (reloadView!=null)
            reloadView.setVisibility(View.GONE);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.classifyListView:
                this.newPosition = position;
                ClassifyBean classifyBean = (ClassifyBean) parent.getItemAtPosition(position);
                classifyPresenter.getSmallClassifyData(classifyBean.getId());
                break;
        }
    }


    @Override
    public void setClassifyListData(List<ClassifyBean> classifyBeans) {
        if (classifyBeans != null && classifyBeans.size() > 0) {
            classifyBeanList.clear();
            classifyBeanList.addAll(classifyBeans);
            classifyAdapter.notifyDataSetChanged();
            for (int i = 0; i < classifyBeanList.size(); i++) {
                if (classifyBeanList.get(i).getId().equals(categoryId)) {
                    newPosition = i;
                }
            }
            classifyAdapter.setSelectItem(newPosition);
            classifyListView.setSelection(newPosition);
            classifyAdapter.notifyDataSetInvalidated();
            hideReloadView();
        }else
            showReloadView();

    }

    @Override
    public void setSmallClassifyListData(List<SmallClassifyBean> smallClassifyBeans) {
        if (null != smallClassifyBeans && smallClassifyBeans.size() > 0) {
            smallClassifyBeanList.clear();
            classifyAdapter.setSelectItem(newPosition);
            classifyAdapter.notifyDataSetInvalidated();
            smallClassifyBeanList.addAll(smallClassifyBeans);
            smallClassifyAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        if (classifyPresenter != null)
            classifyPresenter.getClassifyData();
    }


    public void refresh() {
        if (classifyPresenter != null)
            classifyPresenter.getClassifyData();
    }
}

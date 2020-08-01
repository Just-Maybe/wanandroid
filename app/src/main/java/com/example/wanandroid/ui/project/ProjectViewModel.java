package com.example.wanandroid.ui.project;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.base.RxObserver;
import com.example.wanandroid.bean.ProjectBean;
import com.example.wanandroid.bean.ProjectCategoryBean;
import com.example.wanandroid.bean.ProjectListBean;
import com.example.wanandroid.network.Http;
import com.example.wanandroid.utils.RxUtils;

import java.util.List;

/**
 * Created by Miracle on 2020/8/1
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class ProjectViewModel extends ViewModel {
    private static final int START_PAGE = 0;
    public MutableLiveData<List<ProjectBean>> projectLiveData;
    public MutableLiveData<List<ProjectCategoryBean>> projectCategoryLiveData;
    public MutableLiveData<Boolean> isLoadData;
    public int page;

    public ProjectViewModel() {
        projectLiveData = new MutableLiveData<>();
        projectCategoryLiveData = new MutableLiveData<>();
        isLoadData = new MutableLiveData<>();
        isLoadData.setValue(false);
    }

    /**
     * 从网络获取热门项目列表
     */
    public void getHotProjectListFromNetwork() {
        isLoadData.setValue(page == START_PAGE);
        Http.getApi().getHotProjectList(page)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new RxObserver<ProjectListBean>() {
                    @Override
                    public void onSuccess(ProjectListBean response) {
                        if (response.getDataList() != null && response.getDataList().size() > 0) {
                            if (page == START_PAGE) {
                                projectLiveData.setValue(response.getDataList());
                            } else {
                                projectLiveData.postValue(response.getDataList());
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {

                    }

                    @Override
                    public void onComplete() {
                        page++;
                        isLoadData.setValue(false);
                    }
                });
    }

    /**
     * 从网络获取项目分类列表
     */
    public void getProjectCategoryListFromNetwork() {
        Http.getApi().getProjectCategoryList().compose(RxUtils.rxSchedulerHelper())
                .subscribe(new RxObserver<List<ProjectCategoryBean>>() {
                    @Override
                    public void onSuccess(List<ProjectCategoryBean> response) {
                        if (response != null && response.size() > 0) {
                            projectCategoryLiveData.setValue(response);
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {

                    }
                });
    }

}

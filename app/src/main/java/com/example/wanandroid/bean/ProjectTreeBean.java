package com.example.wanandroid.bean;

import androidx.room.Entity;

import java.util.List;

/**
 * Created by Miracle on 2020/7/17
 * Email: zhaoqirong96@gmail.com
 * Describe:  知识体系树
 */

public class ProjectTreeBean {

    /**
     * visible : 0
     * children : []
     * name : 完整项目
     * userControlSetTop : false
     * id : 294
     * courseId : 13
     * parentChapterId : 293
     * order : 145000
     */
    private int visible;
    private List<ProjectTreeBean> children;
    private String name;
    private boolean userControlSetTop;
    private int id;
    private int courseId;
    private int parentChapterId;
    private int order;

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public void setChildren(List<ProjectTreeBean> children) {
        this.children = children;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getVisible() {
        return visible;
    }

    public List<ProjectTreeBean> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public int getOrder() {
        return order;
    }
}

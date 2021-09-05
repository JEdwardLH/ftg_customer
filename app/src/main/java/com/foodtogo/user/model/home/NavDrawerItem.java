package com.foodtogo.user.model.home;


public class NavDrawerItem {
    private String title;
    private int imgDefault;
    private int imgSelected;

    public NavDrawerItem() {

    }

    public int getImgSelected() {
        return imgSelected;
    }

    public void setImgSelected(int imgSelected) {
        this.imgSelected = imgSelected;
    }

    public int getDefalutImg() {
        return imgDefault;
    }

    public void setImgDefault(int imgDefault) {
        this.imgDefault = imgDefault;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

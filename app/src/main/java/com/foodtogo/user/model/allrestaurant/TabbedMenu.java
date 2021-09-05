package com.foodtogo.user.model.allrestaurant;

public class TabbedMenu {

    private int tabId;
    private String tabActive;
    private String tabMenu;
    private boolean isActive;


    public TabbedMenu(int tabId, String tabActive, String tabMenu, boolean isActive) {
        this.tabId = tabId;
        this.tabActive = tabActive;
        this.tabMenu = tabMenu;
        this.isActive = isActive;
    }


    public String getTabActive() {
        return tabActive;
    }

    public void setTabActive(String tabActive) {
        this.tabActive = tabActive;
    }

    public String getTabMenu() {
        return tabMenu;
    }

    public void setTabMenu(String tabMenu) {
        this.tabMenu = tabMenu;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getTabId() {
        return tabId;
    }

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }


}

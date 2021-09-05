package com.foodtogo.user.model.settings;

public class MenuSettings {

    private int menuIcon;
    private String menuName;

    public MenuSettings() {
    }

    public MenuSettings(int menuIcon, String menuName) {
        this.menuIcon = menuIcon;
        this.menuName = menuName;
    }

    public int getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(int menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}

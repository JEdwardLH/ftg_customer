
package com.foodtogo.user.model.login;

public class LogoutRequest {

    private String token;
    private String lang;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAndr_device_id() {
        return andr_device_id;
    }

    public void setAndr_device_id(String andr_device_id) {
        this.andr_device_id = andr_device_id;
    }

    private String andr_device_id;

    private String andr_fcm_id;;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getAndr_fcm_id() {
        return andr_fcm_id;
    }

    public void setAndr_fcm_id(String andr_fcm_id) {
        this.andr_fcm_id = andr_fcm_id;
    }







}

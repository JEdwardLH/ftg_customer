
package com.foodtogo.user.model.login;

public class LoginRequest {

    private String login_id;
    private String cus_password;
    private String lang;
    private String andr_fcm_id;
    private String type;

    public String getAndr_device_id() {
        return andr_device_id;
    }

    public void setAndr_device_id(String andr_device_id) {
        this.andr_device_id = andr_device_id;
    }

    private String andr_device_id;

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getCus_password() {
        return cus_password;
    }

    public void setCus_password(String cus_password) {
        this.cus_password = cus_password;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}

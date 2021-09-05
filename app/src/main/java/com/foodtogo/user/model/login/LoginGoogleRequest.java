
package com.foodtogo.user.model.login;

public class LoginGoogleRequest {

    private String name;
    private String email;
    private String google_id;
    private String lang;
    private String andr_fcm_id;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
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


    public String getAndr_device_id() {
        return andr_device_id;
    }

    public void setAndr_device_id(String andr_device_id) {
        this.andr_device_id = andr_device_id;
    }

    private String andr_device_id;



}

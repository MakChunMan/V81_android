package com.imagsky.v81_android.core;

/**
 * Created by jasonmak on 29/1/2015.
 */
import com.google.gson.annotations.SerializedName;

public class JsonResponse {

    @SerializedName("_module")
    private String module;

    @SerializedName("_status")
    private String status;

    @SerializedName("_msg")
    private String message;

    @SerializedName("_email")
    private String email;

    @SerializedName("_passwd")
    private String password;

    @SerializedName("_token")
    private String token;

    @SerializedName("_timestamp")
    private long timestamp;

    @SerializedName("_64appobj")
    private String appobjjson_64;

    @SerializedName("_savetime")
    private long savetime;

    @SerializedName("_newsboard")
    private String newsboard_64;

    /****************** BASIC setter / getter *************/

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public java.util.Date getTimestamp() {
        return new java.util.Date(this.timestamp);
    }

    public void setTimestamp(java.util.Date timestamp) {
        this.timestamp = timestamp.getTime();
    }

    public String getAppobjjson_64() {
        return appobjjson_64;
    }

    public void setAppobjjson_64(String appobjjson_64) {
        this.appobjjson_64 = appobjjson_64;
    }

    public long getSavetime() {
        return savetime;
    }

    public void setSavetime(long savetime) {
        this.savetime = savetime;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getNewsboard_64() {
        return newsboard_64;
    }

    public void setNewsboard_64(String newsboard_64) {
        this.newsboard_64 = newsboard_64;
    }


}

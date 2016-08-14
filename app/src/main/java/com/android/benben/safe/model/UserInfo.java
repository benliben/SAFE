package com.android.benben.safe.model;

/**
 * Created by Administrator on 2016/8/8.
 */
public class UserInfo {
    private String username;
    private String phonenumber;

    public UserInfo(String username, String phonenumber) {
        this.username = username;
        this.phonenumber = phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}

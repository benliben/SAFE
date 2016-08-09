package com.android.benben.safe.model;

/**
 * Created by LiYuanxiong on 2016/8/9 14:08.
 * Desribe:
 */
public class PhoneNumberModel {
    String name;
    String number;

    public PhoneNumberModel(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

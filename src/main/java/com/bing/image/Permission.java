package com.bing.image;

/**
 * Created by Administrator on 2015/6/24.
 */
public enum Permission {
    PRIVATE(3), FRIEND(2), ALL(1);

    private int code;

    private Permission(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

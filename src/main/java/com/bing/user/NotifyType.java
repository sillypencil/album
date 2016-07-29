package com.bing.user;

/**
 * Created by binglau on 15-6-30.
 */
public enum NotifyType {
    COMMENT(1), FAVOR(2), COLLECT(3);

    private int code;

    private NotifyType(int code){

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

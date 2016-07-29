package com.bing.image;

public enum FilterType {
    DEFAULT(1), AUTO(2), PERSON(3), FOOD(4), STATIC(5), VIEW(6);

    private int code;
    private FilterType(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

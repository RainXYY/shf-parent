package com.atguigu.en;

/**
 * 包名:com.atguigu.en
 *
 * @author Leevi
 * 日期2022-03-26  22:51
 */
public enum HouseStatus {
    PUBLISHED(1,"已发布"), UNPUBLISHED(0,"未发布");
    public int code;
    public String message;

    HouseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

package com.atguigu.en;

/**
 * Date:2022/5/22
 * Author:夏宇
 * Description:
 */
public enum DictCode {
    HOUSETYPE("houseType"),FLOOR("floor"),BUILDSTRUCTURE("buildStructure"),
    DECORATION("decoration"),DIRECTION("direction"),HOUSEUSE("houseUse");
    private String message;

    DictCode(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}

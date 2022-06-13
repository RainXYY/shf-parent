package com.atguigu.entity.bo;


import lombok.Data;

/**
 * 登录对象
 */
@Data
public class LoginBo {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;
}

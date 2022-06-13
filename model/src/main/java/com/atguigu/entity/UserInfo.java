package com.atguigu.entity;

import lombok.Data;

@Data
public class UserInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//手机号
	private String phone;
	//用户密码
	private String password;
	//用户昵称
	private String nickName;
	//状态（0：锁定 1：正常）
	private Integer status;
}


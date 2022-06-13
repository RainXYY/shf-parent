package com.atguigu.entity;

import lombok.Data;

@Data
public class HouseUser extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	//房源id
	private Long houseId;
	//房东姓名
	private String name;
	//手机
	private String phone;
	//性别 1:男 2：女
	private Integer sex;
	//身份证号
	private String idNo;
}


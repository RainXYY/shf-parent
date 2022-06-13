package com.atguigu.entity;

import lombok.Data;

@Data
public class UserFollow extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	//用户id
	private Long userId;
	//房源id
	private Long houseId;
}


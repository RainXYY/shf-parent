package com.atguigu.entity;

import lombok.Data;

@Data
public class HouseImage extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//房源id
	private Long houseId;
	//图片名称
	private String imageName;
	//图片地址
	private String imageUrl;
	//1：房源图片 2：房产图片
	private Integer type;
}


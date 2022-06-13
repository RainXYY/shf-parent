package com.atguigu.entity;

import lombok.Data;

@Data
public class HouseBroker extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//房源id
	private Long houseId;
	//经纪人id
	private Long brokerId;
	//经纪人名称
	private String brokerName;
	//经纪人头像
	private String brokerHeadUrl;
}

